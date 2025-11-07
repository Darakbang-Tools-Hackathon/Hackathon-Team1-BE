package com.springboot.hackathonteam1be.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class GeminiConfig {
    @Value("${google.gemini.api-key}")
    private String apiKey;

    private static final String MODEL_NAME = "gemini-pro-vision";

    // 3. 비동기 SDK를 위한 ExecutorService Bean을 등록합니다.
    //    애플리케이션 종료 시 Spring이 자동으로 shutdown을 관리해줍니다.
    @Bean
    public ExecutorService generativeModelExecutor() {
        return Executors.newCachedThreadPool();
    }

    // 4. GenerativeModel Bean을 등록합니다.
    //    이 Bean은 API 키를 사용하여 초기화됩니다.
    @Bean
    public GenerativeModel generativeModel() {
        // GenerativeModel 객체는 스레드 안전(thread-safe)합니다.
        return new GenerativeModel(MODEL_NAME, apiKey);
    }

    // 5. GenerativeModelFutures (비동기 래퍼) Bean을 등록합니다.
    //    위에서 만든 'generativeModel'과 'generativeModelExecutor' Bean을 주입받습니다.
    @Bean
    public GenerativeModelFutures generativeModelFutures(
            GenerativeModel generativeModel,
            ExecutorService generativeModelExecutor
    ) {
        return GenerativeModelFutures.from(generativeModel, generativeModelExecutor);
    }


}
