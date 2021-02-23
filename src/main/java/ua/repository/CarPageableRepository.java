package ua.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.model.Car;


@Repository
public interface CarPageableRepository extends PagingAndSortingRepository<Car, Long> {

    Page<Car> findAllByBrand (Pageable pageable, String brand);

    Page<Car> findAllByQualityClass(Pageable p, String qClass);
}
