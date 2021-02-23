package ua.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.REMOVE)
    private Car car;
    @OneToOne
    private User user;
    private int totalPrice;
    @OneToOne(cascade = CascadeType.ALL)
    private DateRange dateRange;
    private boolean payed = false;
    private LocalDateTime orderDate;
    private boolean driverNeeded = false;
    private String approvalState = "UNDEFINED";
    private String denyMessage;
    private boolean returned = false;
    private int refundSum = 0;

    public int getRefundSum() {
        return refundSum;
    }

    public void setRefundSum(int refundSum) {
        this.refundSum = refundSum;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    public void setDenyMessage(String denyMessage) {
        this.denyMessage = denyMessage;
    }

    public String getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(String approvalState) {
        this.approvalState = approvalState;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public boolean isDriverNeeded() {
        return driverNeeded;
    }

    public void setDriverNeeded(boolean driverNeeded) {
        this.driverNeeded = driverNeeded;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order(Car car, User user, int totalPrice, DateRange dateRange, LocalDateTime orderDate, boolean driverNeeded) {
        this.car = car;
        this.user = user;
        this.totalPrice = totalPrice;
        this.dateRange = dateRange;
        this.orderDate = orderDate;
        this.driverNeeded = driverNeeded;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "Order{" +
                "car=" + car +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                ", dateRange=" + dateRange +
                ", payed=" + payed +
                ", orderDate=" + orderDate +
                ", driverNeeded=" + driverNeeded +
                '}';
    }
}
