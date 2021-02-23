package ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import org.springframework.ui.Model;
import ua.model.User;
import ua.model.UserPrincipal;
import ua.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsManager userDetailsManager;

    public void manageUser(long id) {
        System.out.println("OKAY");
//        User u = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Unable to find user by ID"));
//        u.setAccountNonLocked(false);
        UserPrincipal user = new UserPrincipal(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Unable to find user by ID")));
        user.setAccountNonLocked(false);
        userDetailsManager.updateUser(user);
    }

    @Transactional
    public User updateUserIdCardAndDriversLicense(User user, String idCard, String driversLicense) {
        User userToUpdate = userRepository.getUserById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Unable to find user by ID"));
        userToUpdate.setIdCard(idCard);
        userToUpdate.setDriversLicense(driversLicense);
        return userRepository.save(userToUpdate);

    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUsersByEmail(email);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public User addUser(User user) {
        if (isEmailExists(user.getEmail())) {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Unable to save user in Database");
            }
        }
        return new User();
    }

    /**
     * Checks the given string as email in db
     *
     * @param userEmail string-email to check in db
     * @return true if there is no such email in db, false if found such email
     */
    public boolean isEmailExists(String userEmail) throws IllegalArgumentException {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email to check is NULL or EMPTY!!!");
        }
        return userRepository.getUsersByEmail(userEmail).isEmpty();
    }

    public boolean confirmationPasswordMatch(User user, String confirmPass) {
        return user.getPassword().equals(confirmPass);
    }

    public boolean checkConfirmPassAndIfEmailExists(User user, String confirmPass, Model model) {
        boolean result = !isEmailExists(user.getEmail());
        if (result) {
            model.addAttribute("registrationStatus", "errorEmail");
            return false;
        }
        result = confirmationPasswordMatch(user, confirmPass);
        if (!result) {
            model.addAttribute("registrationStatus", "errorConfirmation");
            return false;
        }
        model.addAttribute("registrationStatus", "registrationSuccessful");
        return result;
    }

    public List<User> findAllUsersByRole(String role) {
        return userRepository.findAllByRole(role);
    }
}