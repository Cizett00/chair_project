// src/main/java/org/example/controller/PostureController.java
package org.example.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.example.dto.PostureStatsDTO;
import org.example.service.WeeklyConfidenceScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RestController
//@RequestMapping("/api/posture")
//public class PostureController {
//
//    @Autowired
//    private WeeklyConfidenceScheduler weeklyScheduler;
//
//    @PostMapping
//    public ResponseEntity<String> receivePosture(@RequestBody PostureStatsDTO dto) {
//        // 1) 로그 출력 (디버깅용)
//        System.out.println("📡 실시간 데이터 수신");
//        System.out.println("자세: "       + dto.get자세());
//        System.out.println("좋은자세시간: " + dto.get좋은자세시간());
//        System.out.println("안좋은자세시간: " + dto.get안좋은자세시간());
//        System.out.println("총앉은시간: "   + dto.get총앉은시간());
//        System.out.println("점수: "        + dto.get점수());
//
//        try {
//            // 2) Realtime Database에 저장
//            DatabaseReference ref = FirebaseDatabase.getInstance()
//                    .getReference("postures")
//                    .push();  // 자동 키 생성
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("자세", dto.get자세());
//            map.put("좋은자세시간", dto.get좋은자세시간());
//            map.put("안좋은자세시간", dto.get안좋은자세시간());
//            map.put("총앉은시간", dto.get총앉은시간());
//            map.put("점수", dto.get점수());
//            map.put("timestamp", System.currentTimeMillis());
//
//            ref.setValueAsync(map);
//
//            // 3) 주간 평균용으로 score(점수)만 스케줄러에 전달
//            weeklyScheduler.addConfidence(dto.get점수());
//
//            return ResponseEntity.ok("✅ Realtime DB 저장 및 주간 스케줄러 호출 완료");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("❌ 저장 중 오류 발생");
//        }
//    }
//}

// src/main/java/org/example/controller/PostureController.java
@RestController
@RequestMapping("/api/posture")
public class PostureController {

    @Autowired
    private WeeklyConfidenceScheduler weeklyScheduler;

    @PostMapping
    public ResponseEntity<String> receivePosture(@RequestBody PostureStatsDTO dto) {
        // 1) 로그 출력
        System.out.println("📡 실시간 데이터 수신");
        System.out.println("자세: "           + dto.get자세());
        System.out.println("좋은자세시간: "   + dto.get좋은자세시간());
        System.out.println("안좋은자세시간: " + dto.get안좋은자세시간());
        System.out.println("총앉은시간: "     + dto.get총앉은시간());
        System.out.println("점수: "           + dto.get점수());
        System.out.println("피드백: "         + dto.getFeedback());  // ← 추가

        try {
            // 2) Realtime Database에 저장
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("postures")
                    .push();

            Map<String, Object> map = new HashMap<>();
            map.put("자세", dto.get자세());
            map.put("좋은자세시간", dto.get좋은자세시간());
            map.put("안좋은자세시간", dto.get안좋은자세시간());
            map.put("총앉은시간", dto.get총앉은시간());
            map.put("점수", dto.get점수());
            map.put("feedback", dto.getFeedback());  // ← 추가
            map.put("timestamp", System.currentTimeMillis());

            ref.setValueAsync(map);

            // 3) 주간 평균 스케줄러 호출
            weeklyScheduler.addConfidence(dto.get점수());

            return ResponseEntity.ok("✅ 저장 및 스케줄러 호출 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ 저장 중 오류 발생");
        }
    }
}
