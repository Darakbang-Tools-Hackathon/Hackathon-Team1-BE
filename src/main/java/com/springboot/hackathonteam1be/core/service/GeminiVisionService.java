package com.springboot.hackathonteam1be.core.service;

import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.Part;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeminiVisionService {

    // 1. Config에서 생성한 Bean을 주입받습니다.
    //    (API 키나 ExecutorService에 대해 알 필요가 없어졌습니다)
    private final GenerativeModelFutures model;

    @Autowired
    public GeminiVisionService(GenerativeModelFutures model) {
        this.model = model;
    }

    public String analyzeImage(byte[] imageBytes, String mimeType, String deviceType) throws Exception {

        // 2. 이미지 Part 생성
        Part imagePart = Part.newBuilder()
                .setMimeType(mimeType)
                .setData(ByteString.copyFrom(imageBytes))
                .build();

        // 3. 텍스트 프롬프트 생성
        String promptText = String.format(
                "당신은 주거 공간 분석 전문가입니다. " +
                        "이 주거 도면 이미지를 보고, '%s' 장치를 설치하기에 적합한 공간을 모두 찾아 목록으로 알려주세요. " +
                        "도면에 '거실', '안방', '주방' 등의 텍스트가 있습니다. " +
                        "응답 형식은 '설치 추천 공간: [공간1], [공간2]'로 통일하세요.",
                deviceType
        );
        Part textPart = Part.newBuilder().setText(promptText).build();

        // 4. Content 객체 생성
        Content content = Content.newBuilder()
                .addParts(imagePart)
                .addParts(textPart)
                .build();

        // 5. API 호출 (주입받은 model 객체 사용)
        ListenableFuture<GenerateContentResponse> futureResponse = model.generateContent(content);

        // 6. 동기식으로 결과 대기 (.get())
        GenerateContentResponse response = futureResponse.get();

        return response.getText();
    }
}
