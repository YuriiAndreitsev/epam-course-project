package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ua.model.Car;
import ua.model.DateRange;
import ua.model.Order;
import ua.model.User;
import ua.service.CarService;
import ua.service.OrderService;
import ua.service.UserService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequestMapping("/authenticated")
public class ActionsController {
    @Autowired
    CarService carService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/user-page")
    public String showUserPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        session.setAttribute("usersOrders", orderService.findAllByUserId(user.getId()));
        return "userPage";
    }

    @GetMapping("/users/orders/return")
    public String returnCar(@RequestParam(name = "orderId") long orderId) {
        orderService.returnCar(orderId);
        return "userPage";
    }

    @PostMapping("/users/orders/refund")
    public RedirectView refundCarDamage(@RequestParam(name = "orderId") long orderId) {
        orderService.setRefundSum(orderId, 0);
        return new RedirectView("/authenticated/user-page");
    }


    @PostMapping("/rent")
    public String rentCarPost(HttpSession session, @RequestParam(name = "carId") long carId, Model model) {
        model.addAttribute("dateRange", LocalDate.now());
        session.setAttribute("currentCar", carService.getCarById(carId));
        return "rent";
    }

    @PostMapping("/order")
    public String orderCarPost(HttpSession session, Locale loc,
                               @RequestParam(name = "dateFrom") String dateFrom,
                               @RequestParam(name = "dateTo") String dateTo,
                               @RequestParam(name = "idCard") String idCard,
                               @RequestParam(name = "driversLicense") String driversLicense,
                               @Nullable @RequestParam(name = "driver") String driverNeeded) {
        Car car = (Car) session.getAttribute("currentCar");
        User user = (User) session.getAttribute("user");
        if (user.getIdCard() == null && user.getDriversLicense() == null) {
            userService.updateUserIdCardAndDriversLicense(user, idCard, driversLicense);
        }
        DateRange dateRange = new DateRange(LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
        session.setAttribute("dateRange", dateRange);
        int priceForDateRange = orderService.calculatePrice(dateRange, car);
        session.setAttribute("order", new Order(car, user, priceForDateRange,
                dateRange, LocalDateTime.now(), Boolean.parseBoolean(driverNeeded)));
        session.setAttribute("locale", loc);
        session.setAttribute("totalPrice", priceForDateRange);
        return "pay";
    }

    @PostMapping("/pay")
    public String payForOrderPost(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        orderService.saveOrder(order);
        return "successfulOrder";
    }
}
