package ua.service;

import ua.model.Car;
import ua.model.DateRange;
import ua.model.User;

import javax.servlet.http.HttpSession;

public class RentService {
    public DateRange getDateRangeFromSessionAndSetParams(HttpSession session ){
        DateRange dateRange = (DateRange) session.getAttribute("dateRange");

        dateRange.setDateFrom(dateRange.getDateFrom());
        dateRange.setDateTo(dateRange.getDateTo());
        return  dateRange;
    }
}
