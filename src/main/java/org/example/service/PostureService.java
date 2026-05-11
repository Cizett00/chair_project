package org.example.service;

import org.example.dto.PostureData;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap; //통계 패키지

@Service
public class PostureService {

    private int goodCount = 0;
    private int badCount = 0;
    private int total = 0;
    private double confidenceSum = 0.0;

    private final String CSV_FILE = "posture_log.csv";
    private final String TEXT_LOG = "posture_log.txt";

    public synchronized void processPosture(PostureData data) {
        // 통계 계산
        total++;
        confidenceSum += data.getConfidence();

        if ("good".equalsIgnoreCase(data.getPosture())) {
            goodCount++;
        } else if ("bad".equalsIgnoreCase(data.getPosture())) {
            badCount++;
        }

        // CSV 기록
        writeToCSV(data);

        // 텍스트 로그 기록
        writeToLog(data);
    }

    private void writeToCSV(PostureData data) {
        try {
            File file = new File(CSV_FILE);
            boolean isNew = !file.exists();

            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            if (isNew) {
                pw.println("timestamp,posture,confidence");
            }

            pw.printf("%s,%s,%.2f%n", data.getTimestamp(), data.getPosture(), data.getConfidence());
            pw.close();

        } catch (IOException e) {
            System.err.println("CSV 저장 오류: " + e.getMessage());
        }
    }

    private void writeToLog(PostureData data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_LOG, true))) {
            writer.write(String.format("[%s] 자세: %s | 신뢰도: %.2f%n",
                    data.getTimestamp(), data.getPosture(), data.getConfidence()));
        } catch (IOException e) {
            System.err.println("로그 저장 오류: " + e.getMessage());
        }
    }

    public synchronized String getSummary() {
        return String.format("📊 총 입력: %d회, 좋은 자세: %d회, 나쁜 자세: %d회, 평균 신뢰도: %.2f",
                total, goodCount, badCount, total > 0 ? confidenceSum / total : 0.0);
    }

    public synchronized Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>(); //데이터 통계내기
        stats.put("total", total);
        stats.put("goodCount", goodCount);
        stats.put("badCount", badCount);
        stats.put("averageConfidence", total > 0 ? confidenceSum / total : 0.0);
        return stats;
    }

}
