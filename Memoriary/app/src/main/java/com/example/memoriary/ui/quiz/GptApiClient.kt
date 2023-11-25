package com.example.memoriary.ui.quiz

import com.example.memoriary.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GptApiClient {
    private const val BASE_URL = "https://api.openai.com"

    // GPT_API_KEY는 build.gradle에서 설정한 필드 이름과 일치해야 합니다.
    private const val API_KEY = BuildConfig.GPT_API_KEY

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $API_KEY")
                        .build()
                    chain.proceed(request)
                }
                .build()
        )
        .build()

    val gptApi: GptApi = retrofit.create(GptApi::class.java)
}
