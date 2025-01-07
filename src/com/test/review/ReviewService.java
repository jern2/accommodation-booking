package com.test.review;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReviewService {
    private List<Review> reviews = new ArrayList<>();
    private static final String FILE_PATH = "./data/reviews.txt";

    public ReviewService() {
        loadReviews();
    }

    // 리뷰 데이터 로드
    private void loadReviews() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                reviews.add(Review.fromFile(line));
            }
        } catch (Exception e) {
            System.err.println("Failed to load reviews: " + e.getMessage());
        }
    }

    // 리뷰 데이터 저장
    private void saveReviews() {
        List<String> lines = new ArrayList<>();
        for (Review review : reviews) {
            lines.add(review.toFileFormat());
        }
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (Exception e) {
            System.err.println("Failed to save reviews: " + e.getMessage());
        }
    }

    // 유효성 검사: 평점 범위 확인
    private boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    // 중복 리뷰 확인
    public boolean isDuplicateReview(int userId, int accommodationId) {
        for (Review review : reviews) {
            if (review.getUserId() == userId && review.getAccommodationId() == accommodationId) {
                return true;
            }
        }
        return false;
    }

    // 리뷰 추가
    public boolean addReview(int userId, String userName, int accommodationId, String content, int rating) {
        // 중복 리뷰 방지
        if (isDuplicateReview(userId, accommodationId)) {
            System.out.println("이미 이 숙소에 리뷰를 작성하셨습니다.");
            return false;
        }

        // 평점 유효성 검사
        if (!isValidRating(rating)) {
            System.out.println("평점은 1~5 사이의 값만 입력 가능합니다.");
            return false;
        }

        int newReviewId = reviews.size() + 1; // 간단히 리뷰 ID 생성
        Review newReview = new Review(newReviewId, userId, accommodationId, content, rating);
        reviews.add(newReview);
        saveReviews();
        System.out.println("리뷰가 성공적으로 등록되었습니다.");
        return true;
    }

    // 숙소 ID로 리뷰 조회
    public List<Review> getReviewsByAccommodationId(int accommodationId) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getAccommodationId() == accommodationId) {
                result.add(review);
            }
        }
        return result;
    }
}
