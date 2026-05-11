// src/main/java/org/example/dto/PostureData.java
package org.example.dto;

import java.util.Map;

/**
 * 주간(통계) 수집용 DTO
 * Flask(라즈베리파이)에서
 * {
 *   "latest_fsr": {"fsr1": 0, "fsr2": 1, ...},
 *   "confidence": 0.92,
 *   "posture": "정 자세",
 *   "timestamp": "2025-05-24 12:00:00",
 *   "sitting_time": 85,
 *   "camera_conf": 0.75
 * }
 * 형태로 보내면 된다.
 */
public class PostureData {
    private Map<String, Integer> latest_fsr;
    private double confidence;
    private String posture;
    private String timestamp;
    private int sitting_time;
    private double camera_conf;

    // Getters
    public Map<String, Integer> getLatest_fsr() {
        return latest_fsr;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getPosture() {
        return posture;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getSitting_time() {
        return sitting_time;
    }

    public double getCamera_conf() {
        return camera_conf;
    }

    // Setters
    public void setLatest_fsr(Map<String, Integer> latest_fsr) {
        this.latest_fsr = latest_fsr;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public void setPosture(String posture) {
        this.posture = posture;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSitting_time(int sitting_time) {
        this.sitting_time = sitting_time;
    }

    public void setCamera_conf(double camera_conf) {
        this.camera_conf = camera_conf;
    }
}
