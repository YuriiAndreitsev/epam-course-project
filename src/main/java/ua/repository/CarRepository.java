package ua.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.model.Car;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
Set<Car> findAllByBrand(String brand);
}
