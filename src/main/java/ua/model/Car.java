package ua.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brand;
    @Column(name = "class")
    private String qualityClass;
    private int price;

    public Car() {
    }

    public Car(String name, String brand, String qualityClass, int price) {
        this.name = name;
        this.brand = brand;
        this.qualityClass = qualityClass;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", qualityClass='" + qualityClass + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                price == car.price &&
                Objects.equals(name, car.name) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(qualityClass, car.qualityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, qualityClass, price);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQualityClass() {
        return qualityClass;
    }

    public void setQualityClass(String qualityClass) {
        this.qualityClass = qualityClass;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
