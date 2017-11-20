package me.srichomthong.savetogether.center.model;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class Comment {
    public String commentId;
    public String commentDetail;
    public String commentDate;
    public String userId;
    public String shopId;

    public Comment(String commentId, String commentDetail, String commentDate, String userId, String shopId) {
        this.commentId = commentId;
        this.commentDetail = commentDetail;
        this.commentDate = commentDate;
        this.userId = userId;
        this.shopId = shopId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
