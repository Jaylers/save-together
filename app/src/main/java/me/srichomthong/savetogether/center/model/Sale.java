package me.srichomthong.savetogether.center.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class Sale {
    public static String saleId;
    public static String saleName;
    public static String saleDetail;
    public static int saleFullPrice;
    public static int salePrice;
    public static String saleShop;
    public static String saleShopName;
    public static String shop_url;
    public static String timeRange;
    public static int count;
    public int starCount;
    public static Map<String, Boolean> stars = new HashMap<>();

    public Sale() {
    }

    public Sale(String saleId, String saleName, String saleDetail, int saleFullPrice, int salePrice, String saleShop, String timeRange, int starCount) {
        this.saleId = saleId;
        this.saleName = saleName;
        this.saleDetail = saleDetail;
        this.saleFullPrice = saleFullPrice;
        this.salePrice = salePrice;
        this.saleShop = saleShop;
        this.timeRange = timeRange;
        this.starCount = starCount;
    }

    public Sale(String saleId, String saleName, String saleDetail, int saleFullPrice, int salePrice,
                String saleShop, String timeRange, String saleShopName, int count, String shop_url) {
        this.saleId = saleId;
        this.saleName = saleName;
        this.saleDetail = saleDetail;
        this.saleFullPrice = saleFullPrice;
        this.salePrice = salePrice;
        this.saleShop = saleShop;
        this.timeRange = timeRange;
        this.saleShopName = saleShopName;
        this.count = count;
        this.shop_url = shop_url;

    }

    // [START post_to_map]
    @Exclude
    public static Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("saleId", saleId);
        result.put("saleName", saleName);
        result.put("saleDetail", saleDetail);
        result.put("saleFullPrice", saleFullPrice);
        result.put("salePrice", salePrice);
        result.put("saleShop", saleShop);
        result.put("time", timeRange);
        result.put("saleShopName", saleShopName);
        result.put("count", count);
        result.put("shop_url", shop_url);
        result.put("star", stars);
        return result;
    }
    // [END post_to_map]

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

    public int getSaleFullPrice() {
        return saleFullPrice;
    }

    public void setSaleFullPrice(int saleFullPrice) {
        this.saleFullPrice = saleFullPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getSaleShop() {
        return saleShop;
    }

    public void setSaleShop(String saleShop) {
        this.saleShop = saleShop;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
}
