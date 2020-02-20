package com.wirecard.userapp.response.paging;

public class Paging {

    private long totalData;
    private int sizeOfPage;
    private int currPage;
    private int totalPages;

    public Paging(long totalData, int sizeOfPage, int currPage, int totalPages) {
        this.totalData = totalData;
        this.sizeOfPage = sizeOfPage;
        this.currPage = currPage;
        this.totalPages = totalPages;
    }

    public long getTotalData() {
        return totalData;
    }

    public void setTotalData(long totalData) {
        this.totalData = totalData;
    }

    public int getSizeOfPage() {
        return sizeOfPage;
    }

    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPage(int totalPages) {
        this.totalPages = totalPages;
    }
}
