package ua.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;
import ua.component.AuthenticationSuccessHandlerImpl;
import ua.service.ApplicationUserDetailService;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String AUTHENTICATION_BY_EMAIL = "SELECT email as username, password, true from users where email=?";
    private static final String AUTHORITIES_BY_USERNAME = "SELECT email as username, role from users where email=?";

    @Bean
    public AuthenticationSuccessHandlerImpl authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

    @Autowired
    DataSource dataSource;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager mgr = new JdbcUserDetailsManager();
        mgr.setDataSource(dataSource); // (1)
        mgr.setUpdateUserSql("update users set account_non_locked = ? where email = ?");
        mgr.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
        mgr.setUsersByUsernameQuery(AUTHENTICATION_BY_EMAIL);
        return mgr;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsManager(dataSource))
        auth.userDetailsService(userDetailsManager(dataSource)).and().jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder())
                .usersByUsernameQuery(AUTHENTICATION_BY_EMAIL)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/fragments/*", "/registration", "/cars", "/order", "/css/*", "/js/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .passwordParameter("pass")
                .usernameParameter("email")
                .permitAll()
                .defaultSuccessUrl("/cars", true)
                .successHandler(authenticationSuccessHandler())
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingverysecured")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logoutPage")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutPage", "GET")) // https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/");
    }
}
