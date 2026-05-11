// src/main/java/org/example/service/WeeklyConfidenceStats.java
package org.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeeklyConfidenceStats {
    // 하루 동안 누적된 confidence 점수 리스트
    private final List<Double> week_confidenceList = new ArrayList<>();
    // 현재 날짜 기록 (날짜가 바뀌면 리스트 초기화)
    private LocalDate currentDate = LocalDate.now();

    // 새로운 confidence 값이 들어올 때 호출
    public synchronized void addConfidence(double confidence) {
        LocalDate today = LocalDate.now();
        // 날짜가 바뀌었다면 내부 리스트 초기화
        if (!today.equals(currentDate)) {
            currentDate = today;
            week_confidenceList.clear();
        }
        week_confidenceList.add(confidence);
    }

    // 현재까지의 평균 계산
    public synchronized double getWeeklyAverage() {
        if (week_confidenceList.isEmpty()) {
            return 0.0;
        }
        return week_confidenceList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // 리스트 완전 초기화(필요 시)
    public synchronized void clear() {
        week_confidenceList.clear();
        currentDate = LocalDate.now();
    }
}
