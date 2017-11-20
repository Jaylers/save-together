package me.srichomthong.savetogether.center.model;

/**
 * Created by sapthawee_s on 20-Nov-17.
 */

public class Review {
    public String reviewId;
    public String reviewRating;
    public String reviewDate;
    public String reviewDetail;
    public String reviewPhotoUrl;

    public Review(String reviewId, String reviewRating, String reviewDate, String reviewDetail, String reviewPhotoUrl) {
        this.reviewId = reviewId;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.reviewDetail = reviewDetail;
        this.reviewPhotoUrl = reviewPhotoUrl;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewDetail() {
        return reviewDetail;
    }

    public void setReviewDetail(String reviewDetail) {
        this.reviewDetail = reviewDetail;
    }

    public String getReviewPhotoUrl() {
        return reviewPhotoUrl;
    }

    public void setReviewPhotoUrl(String reviewPhotoUrl) {
        this.reviewPhotoUrl = reviewPhotoUrl;
    }
}
