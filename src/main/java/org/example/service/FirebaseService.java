package org.example.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import jakarta.annotation.PostConstruct;
import org.example.dto.PostureData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    private DatabaseReference database;

    @PostConstruct
    public void initFirebase() {
        try {
            File file = new File("src/main/resources/firebase-service-key.json");

            if (!file.exists()) {
                System.out.println("⚠️ Firebase 설정 파일이 없어 초기화를 건너뜁니다.");
                return;
            }

            FileInputStream serviceAccount = new FileInputStream(file);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chair-firebase-default-rtdb.asia-southeast1.firebasedatabase.app/")  // 🔁 수정 필요 파이어베이스 경로 반드시 수정할것 수정 수정 수정
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            database = FirebaseDatabase.getInstance().getReference();
            System.out.println("✅ Firebase 초기화 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadPosture(PostureData data) {
        if (database == null) {
            System.out.println("⚠️ Firebase가 초기화되지 않아 데이터를 전송할 수 없습니다.");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", data.getTimestamp());
        map.put("posture", data.getPosture());
        map.put("confidence", data.getConfidence());

        database.child("postures").push().setValueAsync(map);
    }

}
