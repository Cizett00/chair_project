//// src/main/java/org/example/dto/PostureStatsDTO.java
//package org.example.dto;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
///**
// * Flask에서 보내는 JSON을 1:1로 매핑하기 위한 DTO
// * 예시 JSON:
// * {
// *   "자세": "...",
// *   "좋은자세시간": 123,
// *   "안좋은자세시간": 456,
// *   "총앉은시간": 789,
// *   "점수": 0.92
// * }
// */
//public class PostureStatsDTO {
//
//    @JsonProperty("자세")
//    private String 자세;
//
//    @JsonProperty("좋은자세시간")
//    private int 좋은자세시간;
//
//    @JsonProperty("안좋은자세시간")
//    private int 안좋은자세시간;
//
//    @JsonProperty("총앉은시간")
//    private int 총앉은시간;
//
//    @JsonProperty("점수")
//    private double 점수;
//
//    // Getters
//    public String get자세() {
//        return 자세;
//    }
//
//    public int get좋은자세시간() {
//        return 좋은자세시간;
//    }
//
//    public int get안좋은자세시간() {
//        return 안좋은자세시간;
//    }
//
//    public int get총앉은시간() {
//        return 총앉은시간;
//    }
//
//    public double get점수() {
//        return 점수;
//    }
//
//    // Setters
//    public void set자세(String 자세) {
//        this.자세 = 자세;
//    }
//
//    public void set좋은자세시간(int 좋은자세시간) {
//        this.좋은자세시간 = 좋은자세시간;
//    }
//
//    public void set안좋은자세시간(int 안좋은자세시간) {
//        this.안좋은자세시간 = 안좋은자세시간;
//    }
//
//    public void set총앉은시간(int 총앉은시간) {
//        this.총앉은시간 = 총앉은시간;
//    }
//
//    public void set점수(double 점수) {
//        this.점수 = 점수;
//    }
//}

// src/main/java/org/example/dto/PostureStatsDTO.java
package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostureStatsDTO {

    @JsonProperty("자세")
    private String 자세;

    @JsonProperty("좋은자세시간")
    private int 좋은자세시간;

    @JsonProperty("안좋은자세시간")
    private int 안좋은자세시간;

    @JsonProperty("총앉은시간")
    private int 총앉은시간;

    @JsonProperty("점수")
    private double 점수;

    @JsonProperty("feedback")
    private String feedback;   // ← 새로 추가된 필드

    // Getters
    public String get자세() { return 자세; }
    public int get좋은자세시간() { return 좋은자세시간; }
    public int get안좋은자세시간() { return 안좋은자세시간; }
    public int get총앉은시간() { return 총앉은시간; }
    public double get점수() { return 점수; }
    public String getFeedback() { return feedback; }  // ← 추가

    // Setters
    public void set자세(String 자세) { this.자세 = 자세; }
    public void set좋은자세시간(int 좋은자세시간) { this.좋은자세시간 = 좋은자세시간; }
    public void set안좋은자세시간(int 안좋은자세시간) { this.안좋은자세시간 = 안좋은자세시간; }
    public void set총앉은시간(int 총앉은시간) { this.총앉은시간 = 총앉은시간; }
    public void set점수(double 점수) { this.점수 = 점수; }
    public void setFeedback(String feedback) { this.feedback = feedback; }  // ← 추가
}
