// src/main/java/org/example/service/FirebaseService.java
package org.example.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.example.dto.PostureData; // 필요 없을 수 있음
import org.example.dto.PostureStatsDTO;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    // Realtime Database 참조
    private DatabaseReference realtimeDbRef;

    // Firestore 참조
    private Firestore firestore;

    @PostConstruct
    public void init() {
        try {
            // 서비스 계정 키 파일 경로
            File file = new File("src/main/resources/firebase-service-key.json");
            if (!file.exists()) {
                System.out.println("⚠️ Firebase 서비스 키 파일을 찾을 수 없어 초기화를 건너뜁니다.");
                return;
            }
            FileInputStream serviceAccount = new FileInputStream(file);

            // FirebaseOptions 설정 (RealtimeDB URL 지정)
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://userdata-ecc36-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();

            // FirebaseApp 초기화
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            // 1) Realtime Database 참조 얻기
            realtimeDbRef = FirebaseDatabase.getInstance().getReference();
            System.out.println("✅ Firebase Realtime Database 초기화 완료");

            // 2) Firestore 참조 얻기
            firestore = FirestoreClient.getFirestore();
            System.out.println("✅ Firestore 초기화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Realtime Database에 실시간 데이터 저장 (PostureStatsDTO)
     */
    public void uploadPostureToRealtime(PostureStatsDTO data) {
        if (realtimeDbRef == null) {
            System.out.println("⚠️ Realtime Database가 초기화되지 않아 데이터를 전송할 수 없습니다.");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("자세", data.get자세());
        map.put("좋은자세시간", data.get좋은자세시간());
        map.put("안좋은자세시간", data.get안좋은자세시간());
        map.put("총앉은시간", data.get총앉은시간());
        map.put("점수", data.get점수());
        map.put("timestamp", System.currentTimeMillis());

        realtimeDbRef.child("postures").push().setValueAsync(map);
    }

    public void uploadWeeklyConfidence(double averageConfidence, String timestamp) {
        if (firestore == null) {
            System.out.println("⚠️ Firestore가 초기화되지 않았습니다.");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("weekly_confidence_avg", averageConfidence);

        // 문서 ID를 timestamp로 지정
        firestore
                .collection("weekly_stats")
                .document(timestamp)
                .set(map);

        System.out.println("✅ Firestore 업로드 완료 (문서명: " + timestamp + ")");
    }
    public void updateWeeklyStats(String weekKey, String dayField, double average) {
        Map<String, Object> map = new HashMap<>();
        map.put(dayField, average);

        // merge 옵션으로 기존 문서에 필드만 덮어쓰기
        firestore
                .collection("weekly_stats")
                .document(weekKey)
                .set(map, SetOptions.merge());

        System.out.println("✅ Firestore 주간 통계 업데이트: "
                + weekKey + " → " + dayField + " = " + average);
    }


    /**
     * Firestore에 주간 confidence 평균 저장
     */
//    public void uploadWeeklyConfidence(double averageConfidence, String timestamp) {
//        if (firestore == null) {
//            System.out.println("⚠️ Firestore가 초기화되지 않았습니다.");
//            return;
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("timestamp", timestamp);
//        map.put("weekly_confidence_avg", averageConfidence);
//
//        firestore.collection("weekly_stats").add(map);
//        System.out.println("✅ Firestore 업로드 완료: " + averageConfidence);
//    }
}
