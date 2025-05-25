package org.example.dto;

// PostrueData JSON필드 1:1대응 수정
// PostureController 출력 로직 바꿀시 수정
// PostureService Csv 로그 포맷시 수정
// FirebaseService 전송구조 손볼때

public class PostureData {
    private String timestamp;
    private String posture;
    private double confidence;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosture() {
        return posture;
    }

    public void setPosture(String posture) {
        this.posture = posture;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
