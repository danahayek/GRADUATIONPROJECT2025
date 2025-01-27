package com.example.GraduationProject.chatbot;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OPenAi {

    @POST("v1/chat/completions")
    Call<ChatResponse> getChatResponse(
            @Header("Authorization") String authHeader,
            @Body ChatRequest request
    );

}
