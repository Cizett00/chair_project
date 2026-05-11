// src/main/java/org/example/service/WeeklyConfidenceScheduler.java
package org.example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class WeeklyConfidenceScheduler {

    private final WeeklyConfidenceStats stats = new WeeklyConfidenceStats();
    private final FirebaseService firebaseService;

    public WeeklyConfidenceScheduler(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    // 컨트롤러에서 호출됨 → 점수 누적
    public void addConfidence(double confidence) {
        stats.addConfidence(confidence);
    }
    @Scheduled(cron = "0 * * * * *")
    public void reportHourlyAverage() {
        double avg = stats.getWeeklyAverage();
        // 1) 원본 포맷
        String now = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 2) 키용으로 공백→'_' , ':'→'-' 치환
        String key = now.replace(" ", "_")
            .replace(":", "-");  // ex) "2025-06-12_17-00-00"

    firebaseService.uploadWeeklyConfidence(avg, key);
    System.out.println("📤 주간(1시간 단위) 평균 전송 완료: " + avg);
    }

    // 매일 자정(00:00:00) 하루 평균 전송 후 초기화 임시코드
    @Scheduled(cron = "*/10 * * * * *")
    public void resetDailyData() {
        double avg = stats.getWeeklyAverage();
        String today = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String key = today.replace(" ", "_")
                .replace(":", "-");  // ex) "2025-06-13_00-00-00"

        firebaseService.uploadWeeklyConfidence(avg, key);
        stats.clear();
        System.out.println("🔄 일일 데이터 초기화 및 평균 전송 완료: " + avg);
    }
    @Scheduled(cron = "*/10 * * * * *")
    public void reportDailyAverage() {
        // 1) 오늘의 평균 점수 계산
        double avg = stats.getWeeklyAverage();

        // 2) 오늘 날짜를 키로 (문서ID)
        String todayKey = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        //    예: "2025-06-12"

        // 3) Firestore에 덮어쓰기
        firebaseService.uploadWeeklyConfidence(avg, todayKey);
        // 4) 로그 찍고, 다음날을 위해 초기화
        System.out.println("📅 일일 평균 전송 완료: " + todayKey + " = " + avg);
        stats.clear();
    }
}
