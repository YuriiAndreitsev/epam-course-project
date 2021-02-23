package ua.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class DateRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

    public DateRange(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public DateRange() {
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "id=" + id +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
