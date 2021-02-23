package ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.model.User;
import ua.model.UserPrincipal;
import ua.repository.UserRepository;

@Service
public class ApplicationUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUsersByEmail(username).orElseThrow( ()-> new UsernameNotFoundException("Unable to load user by email : "+username));
        return new UserPrincipal(user);
    }
}
