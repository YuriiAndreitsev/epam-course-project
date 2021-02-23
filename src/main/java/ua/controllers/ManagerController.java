package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ua.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping(value = "/authenticated/managers")
public class ManagerController {
    private static final String APPROVED = "APPROVED";
    private static final String UNDEFINED = "UNDEFINED";
    private static final String DENIED = "DENIED";
    @Autowired
    OrderService orderService;

    @GetMapping(value = "/orders/returned")
    public String getReturnedCars(HttpSession session, @RequestParam(name = "returned") String returnedState) {
        session.setAttribute("returnedCars", orderService.getReturnedCarList(Boolean.parseBoolean(returnedState)));
        return "managers-returned";
    }

    @PostMapping(value = "/orders/returned")
    public RedirectView receivedDamagedCar(@RequestParam(name = "orderId") long id, @RequestParam(name = "refundSum") int sum) {
        orderService.setRefundSum(id, sum);
        return new RedirectView("/authenticated/managers/orders/returned?returned=true");
    }

    @GetMapping(value = "orders")
    public String getAllUnapprovedOrders(HttpSession session, @RequestParam(name = "approvalState") Optional<String> state) {
        if (state.isPresent()) {
            switch (state.get()) {
                case APPROVED:
                    session.setAttribute("currentOrders", orderService.getAllOrdersByState(APPROVED));
                    break;
                case DENIED:
                    session.setAttribute("currentOrders", orderService.getAllOrdersByState(DENIED));
                    break;
            }
        } else {
            session.setAttribute("currentOrders", orderService.getAllOrdersByState(UNDEFINED));
        }
        return "managers";
    }

    @GetMapping(value = "/orders/approve")
    public String approveOrder(@RequestParam("orderId") long id) {
        orderService.changeApprovalState(id, APPROVED);
        return "managers";
    }

    @GetMapping(value = "/orders/deny")
    public String denyOrder(HttpSession session, @RequestParam("orderId") long id) {
        session.setAttribute("currentOrders", orderService.getOrderById(id));
        return "managers-deny";
    }

    @PostMapping(value = "/orders/deny")
    public String denyOrderPOST(@RequestParam("orderId") long id, @RequestParam("message") String denyMessage, Model model) {
        if (denyMessage.isEmpty()) {
            model.addAttribute("denyMessageError", "error");
            return "managers-deny";
        }
        orderService.changeApprovalState(id, DENIED, denyMessage);
        return "managers-deny";
    }
}
