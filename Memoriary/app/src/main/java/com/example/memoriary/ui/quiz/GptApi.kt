package com.example.memoriary.ui.quiz

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class GptRequest(val text: String)

data class GptResponse(val summary: String)

interface GptApi {
    @POST("your_gpt_api_endpoint")
    fun summarizeDiary(@Body request: GptRequest): Call<GptResponse>
}