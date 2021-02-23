package ua.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.model.Car;
import ua.model.CarPage;
import ua.service.CarService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CarsController {
    @Autowired
    CarService carService;

    @GetMapping("/cars")
    @PreAuthorize("permitAll()")
    public String getCarsList(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @Nullable @RequestParam("sort") String sort,
            @Nullable @RequestParam("brand") String brand) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        model.addAttribute("carsByBrand", carService.getUniqueBrands(brand));
        CarPage carPage = new CarPage(currentPage - 1, pageSize, sort);
//        Page<Car> carsPaged = carService.getAllSorted(carPage);
        Page<Car> carsPaged;
        if (brand!=null&&!brand.isEmpty()) {
            carsPaged = carService.getUniqueBrands(carPage, brand);
        }else{
            carsPaged = carService.getAllSorted(carPage);
        }
        int totalPages = carsPaged.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("carPage", carsPaged);
        model.addAttribute("sortType", sort);
        model.addAttribute("brand", brand);
        return "cars";
    }
}
