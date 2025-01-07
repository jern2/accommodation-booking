package com.test.accommodation;

public class Review {
    private int reviewId;
    private String userId;
    private int accommodationId;
    private String content;
    private int rating;

    public Review(int reviewId, String userId, int accommodationId, String content, int rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.content = content;
        this.rating = rating;
    }

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(int accommodationId) {
		this.accommodationId = accommodationId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
    
    
}