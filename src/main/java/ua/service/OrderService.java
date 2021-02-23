package ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.model.Car;
import ua.model.DateRange;
import ua.model.Order;
import ua.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public Order setRefundSum(long id, int sum) {
        Order orderToUpdate = orderRepository.getOrderById(id).orElseThrow();
        orderToUpdate.setRefundSum(sum);
        return orderRepository.save(orderToUpdate);
    }

    public List<Order> getReturnedCarList() {
        return orderRepository.findAllByReturnedTrue();
    }

    public List<Order> getReturnedCarList(boolean returned) {
        return orderRepository.findAllByReturned(returned);
    }

    public List<Order> getAllOrdersByState(String state) {
        return orderRepository.findAllByApprovalState(state);
    }

    public Order getOrderById(long id) {
        return orderRepository.getOrderById(id).orElseThrow();
    }

    @Transactional
    public Order returnCar(long id) {
        Order orderToUpdate = orderRepository.getOrderById(id).orElseThrow();
        orderToUpdate.setReturned(true);
        return orderRepository.save(orderToUpdate);
    }

    @Transactional
    public Order changeApprovalState(long id, String state) {
        Order orderToUpdate = orderRepository.getOrderById(id).orElseThrow();
        orderToUpdate.setApprovalState(state);
        return orderRepository.save(orderToUpdate);
    }

    @Transactional
    public Order changeApprovalState(long id, String state, String denyMessage) {
        Order orderToUpdate = orderRepository.getOrderById(id).orElseThrow();
        orderToUpdate.setDenyMessage(denyMessage);
        orderToUpdate.setApprovalState(state);
        return orderRepository.save(orderToUpdate);
    }


    public Order saveOrder(Order order) {
        order.setPayed(true);
        return orderRepository.save(order);
    }

    public int calculatePrice(DateRange dateRange, Car car) {
        if (dateRange.getDateTo().compareTo(dateRange.getDateFrom()) <= 0) {
            throw new IllegalArgumentException("You've entered the wrong date range:" + System.lineSeparator() + dateRange);
        }
        return dateRange.getDateTo().compareTo(dateRange.getDateFrom()) * car.getPrice();
    }

    public List<Order> findAllByUserId(long id) {
        return orderRepository.findAllByUserId(id);
    }
}
