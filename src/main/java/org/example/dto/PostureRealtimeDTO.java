// src/main/java/org/example/dto/PostureRealtimeDTO.java
package org.example.dto;

/**
 * 실시간 데이터 수신용 DTO
 * - JSON 필드 1:1 매핑
 * - Flask(라즈베리파이)에서
 *   {"자세": "...", "사용시간": 123, "점수": 95} 형태로 보내면 된다.
 */
public class PostureRealtimeDTO {
    private String 자세;
    private int 사용시간;
    private int 점수;

    public String get자세() {
        return 자세;
    }

    public void set자세(String 자세) {
        this.자세 = 자세;
    }

    public int get사용시간() {
        return 사용시간;
    }

    public void set사용시간(int 사용시간) {
        this.사용시간 = 사용시간;
    }

    public int get점수() {
        return 점수;
    }

    public void set점수(int 점수) {
        this.점수 = 점수;
    }
}
