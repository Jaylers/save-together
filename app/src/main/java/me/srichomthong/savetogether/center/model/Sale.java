package me.srichomthong.savetogether.center.model;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class Sale {
    public String saleId;
    public  String saleName;
    public String saleDetail;
    public String saleFullPrice;
    public String salePrice;
    public String saleShop;
    public TimeRange timeRange;

    public Sale(String saleId, String saleName, String saleDetail, String saleFullPrice, String salePrice, String saleShop, TimeRange timeRange) {
        this.saleId = saleId;
        this.saleName = saleName;
        this.saleDetail = saleDetail;
        this.saleFullPrice = saleFullPrice;
        this.salePrice = salePrice;
        this.saleShop = saleShop;
        this.timeRange = timeRange;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(String saleDetail) {
        this.saleDetail = saleDetail;
    }

    public String getSaleFullPrice() {
        return saleFullPrice;
    }

    public void setSaleFullPrice(String saleFullPrice) {
        this.saleFullPrice = saleFullPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getSaleShop() {
        return saleShop;
    }

    public void setSaleShop(String saleShop) {
        this.saleShop = saleShop;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
