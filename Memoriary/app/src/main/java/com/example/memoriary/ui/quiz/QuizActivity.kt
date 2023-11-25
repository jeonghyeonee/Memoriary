package com.example.memoriary.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.memoriary.R
import com.example.memoriary.databinding.ActivityQuizBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assume that diaryText contains the original diary string
        val diaryText = "Today is November 23, 2023. Weather is so cold that it is 3'C. " +
                "After the Management Science class, I did some Mobile Programming Team project works. " +
                "I went to 'Donghak' to eat dinner and I ordered turkey with makgeolli. It was so delicious. " +
                "After that I went to Coin Karaoke with friends and spend 3000 won. " +
                "I went back to my house and ate some snacks." +
                "It was a fantastic day."

        // Coroutine scope for asynchronous tasks
        lifecycleScope.launch {
            // Use withContext to switch to the IO dispatcher for networking operations
            val summary = withContext(Dispatchers.IO) {
                // Make the API call to GPT API to summarize the diary
                val response = GptApiClient.gptApi.summarizeDiary(GptRequest(diaryText)).execute()

                // Extract the summary from the response
                response.body()?.summary ?: "Summary not available"
            }

            // Update the UI with the summary
            binding.summaryTextView.text = summary
        }

    }
}