package ua.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "login";
    }
}

