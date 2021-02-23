package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ua.model.Car;
import ua.model.UserPrincipal;
import ua.service.CarService;
import ua.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/authenticated/admins")
public class AdminController {
    @Autowired
    CarService carService;
    @Autowired
    UserService userService;

    @GetMapping("/administer-managers")
    public String registerManagers(){
        //TODO
        return "administerManagers";
    }

    @GetMapping("/administer-users")
    public String administerUsers(HttpSession session){
        //TODO
        session.setAttribute("administrateUsers", userService.findAllUsersByRole("USER"));
        return "administerUsers";
    }
    @PostMapping("/administer-users/block")
    public RedirectView administerUsers(@RequestParam("userId") long id){
        userService.manageUser(id);
        System.out.println("BLOCKING USER by id = "+id);
        return new RedirectView("/authenticated/admins/administer-users");
    }

    @GetMapping("/administer-cars")
    public String getAllCars(HttpSession session, Model model, @Nullable @RequestParam("new-car") String newCar) {
        model.addAttribute("newCar", newCar);
        model.addAttribute("car", new Car());
        session.setAttribute("administerCars", carService.getAllCars());
        return "admins";
    }

    @PostMapping("/administer-cars/new-car")
    public String getAllCars(@ModelAttribute Car car) {
        carService.saveCar(car);
        return "admins";
    }

    @GetMapping("/administer-cars/edit")
    public String updateCar(@RequestParam("carId") long id, Model model) {
        model.addAttribute("edit", "edit");
        model.addAttribute("carToEdit", carService.getCarById(id));
//        carService.updateCar(id);
        return "admins";
    }

    @PostMapping("/administer-cars/edit")
    public String updateCarPost(@ModelAttribute Car car, Model model) {
        carService.updateCar(car);
        model.addAttribute("status", "updated");
        return "admins";
    }

    @PostMapping("/administer-cars/delete")
    public RedirectView removerCar(@RequestParam("carId") long carId, Model model) {
        carService.removeCarById(carId);
        model.addAttribute("status", "removed");
        return new RedirectView("/authenticated/admins/administer-cars");
    }
}
