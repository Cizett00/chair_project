package org.example.controller;

import org.example.dto.PostureData;
import org.example.service.FirebaseService;
import org.example.service.PostureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map; //통계 패키지

@RestController
@RequestMapping("/api")
public class PostureController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private PostureService postureService;

    @PostMapping("/posture")
    public String receivePosture(@RequestBody PostureData data) {
        System.out.println("Received Posture Data:");
        System.out.println(" - time: " + data.getTimestamp());
        System.out.println(" - posture: " + data.getPosture());
        System.out.println(" - confidience: " + data.getConfidence());

        //연동 후에 쓸것
        firebaseService.uploadPosture(data);
        return "✅ 수신 완료";
    }

    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return postureService.getStatistics();  // 통계 데이터 JSON 형태로 반환
    }


}
