package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.model.User;
import ua.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute User user, Model model,
                                   @Nullable @RequestParam(name = "confirmPass") String confirmPass) {
        //TODO double check pattern
        if (userService.checkConfirmPassAndIfEmailExists(user, confirmPass, model)) {
            userService.addUser(user);
        }
        return "registration";
    }
}
