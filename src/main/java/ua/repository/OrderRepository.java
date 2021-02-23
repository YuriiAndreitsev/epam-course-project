package ua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> getOrderById(long id);

    List<Order> findAllByUserId(long id);

    List<Order> findAllByApprovalState(String approvalState);
    List<Order> findAllByReturnedTrue ();
    List<Order> findAllByReturned (boolean returned);
    List<Order> findAllByCar_Id(long id);
}
