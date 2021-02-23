package ua.model;

import org.springframework.data.domain.Sort;

public class CarPage {
    private int pageNumber = 0;
    private int pageSize = 4;
    private Sort.Direction sortDirectionAcs = Sort.Direction.ASC;
    private Sort.Direction sortDirectionDesc = Sort.Direction.DESC;
    private String sortBy;

    public CarPage(int pageNumber, int pageSize, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }

    public CarPage(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public CarPage() {
    }

    public Sort.Direction getSortDirectionAcs() {
        return sortDirectionAcs;
    }

    public void setSortDirectionAcs(Sort.Direction sortDirectionAcs) {
        this.sortDirectionAcs = sortDirectionAcs;
    }

    public Sort.Direction getSortDirectionDesc() {
        return sortDirectionDesc;
    }

    public void setSortDirectionDesc(Sort.Direction sortDirectionDesc) {
        this.sortDirectionDesc = sortDirectionDesc;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
