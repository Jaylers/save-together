package me.srichomthong.savetogether.center.model;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class Shop {
    public String shopId;
    public String shopName;
    public String shopAddress;
    public String shopLocation;
    public String shopDetail;
    public TimeRange timeRange;

    public Shop(String shopId, String shopName, String shopAddress, String shopLocation, String shopDetail, TimeRange timeRange) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopLocation = shopLocation;
        this.shopDetail = shopDetail;
        this.timeRange = timeRange;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(String shopDetail) {
        this.shopDetail = shopDetail;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
