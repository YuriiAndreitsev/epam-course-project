package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.model.User;
import ua.service.CarService;
import ua.service.OrderService;
import ua.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
public class NavigationController {

    @Autowired
    UserService userService;
    @Autowired
    CarService carService;
    @Autowired
    OrderService orderService;


    @GetMapping(path = "/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/authenticated")
    public String forAuthenticatedUsers() {
        return "authenticated";
    }

    @GetMapping("/authenticated/admins")
    public String forAdmins() {
        return "admins";
    }


}
