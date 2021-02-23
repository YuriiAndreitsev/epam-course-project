package ua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.model.Car;
import ua.model.CarPage;
import ua.model.Order;
import ua.repository.CarPageableRepository;
import ua.repository.CarRepository;
import ua.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CarService {
    @Autowired
    CarPageableRepository carPageRep;
    @Autowired
    CarRepository carRep;
    @Autowired
    OrderRepository orderRepository;

    public Car getCarById(long id) {
        return carPageRep.findById(id).orElseThrow();
    }

    public Page<Car> getAllSorted(CarPage carPage) {
        Pageable pageable;
        if (carPage.getSortBy() != null && !carPage.getSortBy().isEmpty()) {
            Sort sort = Sort.by(carPage.getSortDirectionAcs(), carPage.getSortBy());
            pageable = PageRequest.of(carPage.getPageNumber(), carPage.getPageSize(), sort);
        } else {
            pageable = PageRequest.of(carPage.getPageNumber(), carPage.getPageSize());
        }
        return carPageRep.findAll(pageable);
    }

    public Page<Car> getUniqueBrands(CarPage carPage, String brand) {
        Pageable pageable;
        if (carPage.getSortBy() != null && !carPage.getSortBy().isEmpty()) {
            Sort sort = Sort.by(carPage.getSortDirectionAcs(), carPage.getSortBy());
            pageable = PageRequest.of(carPage.getPageNumber(), carPage.getPageSize(), sort);
        } else {
            pageable = PageRequest.of(carPage.getPageNumber(), carPage.getPageSize());
        }
        return carPageRep.findAllByBrand(pageable, brand);
    }

    public Set<Car> getUniqueBrands(String brand) {
        Comparator<Car> carComparator
                = Comparator.comparing(Car::getBrand);
        Set<Car> carSet = new TreeSet<>(carComparator);
        carSet.addAll(carRep.findAll());
        return carSet;
    }

    public List<Car> getAllCars() {
        return carRep.findAll();
    }

    @Transactional
    public Car updateCar(Car car) {
        Car carToUpdate = carRep.findById(car.getId()).orElseThrow();
        carToUpdate.setBrand(car.getBrand());
        carToUpdate.setName(car.getName());
        carToUpdate.setPrice(car.getPrice());
        carToUpdate.setQualityClass(car.getQualityClass());
        return carRep.save(carToUpdate);
    }

    public Car saveCar(Car car) {
        return carRep.save(car);
    }

    @Transactional
    public void removeCarById(long id) {
        List<Order> orderList = orderRepository.findAllByCar_Id(id);
        orderList.forEach(o -> o.setCar(null));
        orderList.forEach(o -> orderRepository.save(o));
        carRep.deleteById(id);
    }
}
