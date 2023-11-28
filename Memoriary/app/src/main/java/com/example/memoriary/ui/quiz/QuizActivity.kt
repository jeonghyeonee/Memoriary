package com.example.memoriary.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.memoriary.BuildConfig
import com.example.memoriary.R
import com.example.memoriary.databinding.ActivityQuizBinding
import com.example.memoriary.ui.diary.Post
import com.example.memoriary.ui.diary.ThumbnailsFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import kotlin.time.Duration.Companion.seconds

class QuizActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuizBinding

    private lateinit var database: DatabaseReference

    interface FirebaseCallBack {
        fun onDataLoaded(posts: MutableList<Post>)
        fun onError(error: Exception)
    }

    private fun readFromFirebase(callback: FirebaseCallBack) {
        val userId = "rainday0828"
        database = Firebase.database.reference
        val posts = mutableListOf<Post>()

        database.child(userId).child("posts").get().addOnSuccessListener {
            for (post in it.children) {
                val key = post.key.toString()
                val title = post.child("title").value.toString()
                val content = post.child("content").value.toString()
                val image = "asdfafsd"
                val post = Post(key, title, content, image)
                Log.d("Thumb", "$post")
                posts.add(post)
            }
            Log.d("Thumb", "$posts")
            callback.onDataLoaded(posts)
        }.addOnFailureListener{error ->
            callback.onError(error)
        }
        Log.d("Thumb", "$posts")

    }

    lateinit var loadDiary: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readFromFirebase(object: FirebaseCallBack {
            override fun onDataLoaded(posts: MutableList<Post>) {
                loadDiary = posts[0].content
                Log.d("ITMpost", "post: $loadDiary")

                binding.originalDiaryTextView.text = "$loadDiary"


                val apiKey = BuildConfig.OPENAI_API_KEY
                Log.d("ITM", "$apiKey")


                // Use lifecycleScope.launch for coroutine
                lifecycleScope.launch {
                    val openAI = OpenAI(
                        token = apiKey,
                        timeout = Timeout(socket = 60.seconds),
                        // additional configurations...
                    )

                    val quizInstruction = "Generate a quiz with three types of questions: " +
                            "Q1. Multiple-choice with 5 options, four correct, one incorrect.\n" +
                            "Q2. Multiple-choice with 5 options, one correct, four incorrect.\n" +
                            "Q3. True/false question based on diary events."

                    val chatCompletionRequest = ChatCompletionRequest(
                        model = ModelId("gpt-3.5-turbo"),
                        messages = listOf(
                            ChatMessage(
                                role = ChatRole.System,
                                content = quizInstruction
                            ),
                            ChatMessage(
                                role = ChatRole.User,
                                content = loadDiary
                            )
                        ),
                        temperature = 0.7,
                        maxTokens = 200,
                        topP = 0.7,
                        frequencyPenalty = 0.0,
                        presencePenalty = 0.0
                    )
                    // Assistant 항목을 3개로 늘려서 Q1,Q2,Q3를 각각 도출하는 방안도 생각

                    val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
//            Log.d("ITM", "completion")
//            Log.d("ITM", "${completion}")
//
//            Log.d("ITM", "completion.choices")
//            Log.d("ITM", "${completion.choices}")
//
//            Log.d("ITM", "completion.choices.toString()")
//            Log.d("ITM", "${completion.choices.toString()}")
//
//            Log.d("ITM", "completion.choices.first()")
//            Log.d("ITM", "${completion.choices.first()}")
//
//            Log.d("ITM", "completion.choices.first().toString()")
//            Log.d("ITM", "${completion.choices.first().toString()}")
//
//            Log.d("ITM", "completion.choices.first().message.content")
                    Log.d("ITM", "${completion.choices.first().message.content}")


//            // 퀴즈 코드
//            val generatedQuiz: String? = completion.choices.firstOrNull()?.message?.content
//
//            // generatedQuiz가 null이 아닐 때만 parseGeneratedQuiz 호출
//            if (generatedQuiz != null) {
//                val quizzes = parseGeneratedQuiz(generatedQuiz)
//
//                // 각 퀴즈를 화면에 표시
//                displayQuizzes(quizzes)
//            }


                    // Handle the completion result here
                    binding.summaryTextView.text = completion.choices.first().message.content
                }

            }

            override fun onError(error: Exception) {
                Log.e("Thumb", "Error getting data", error)
            }
        })




//        // Assume that diaryText contains the original diary string
//        val diaryText = "Today is November 23, 2023. Weather is so cold that it is 3'C. " +
//                "After the Management Science class, I did some Mobile Programming Team project works. " +
//                "I went to 'Donghak' to eat dinner and I ordered turkey with makgeolli. It was so delicious. " +
//                "After that I went to Coin Karaoke with friends and spend 3000 won. " +
//                "I went back to my house and ate some snacks. " +
//                "It was a fantastic day."
//
//        binding.originalDiaryTextView.text = "<Original Diary> \n$diaryText"

//        // Coroutine scope for asynchronous tasks
//        lifecycleScope.launch {
//            // Use withContext to switch to the IO dispatcher for networking operations
//            val summary = withContext(Dispatchers.IO) {
//                // Make the API call to GPT API to summarize the diary
//                val response = GptApiClient.gptApi.summarizeDiary(GptRequest(diaryText)).execute()
//
//                // Extract the summary from the response
//                response.body()?.summary ?: "Summary not available"
//            }
//
//            // Update the UI with the summary
//            binding.summaryTextView.text = summary
//        }


//        val apiKey = BuildConfig.OPENAI_API_KEY
//        Log.d("ITM", "$apiKey")
//
//
//        // Use lifecycleScope.launch for coroutine
//        lifecycleScope.launch {
//            val openAI = OpenAI(
//                token = apiKey,
//                timeout = Timeout(socket = 60.seconds),
//                // additional configurations...
//            )
//
//            val quizInstruction = "Generate a quiz with three types of questions: " +
//                    "Q1. Multiple-choice with 5 options, four correct, one incorrect.\n" +
//                    "Q2. Multiple-choice with 5 options, one correct, four incorrect.\n" +
//                    "Q3. True/false question based on diary events."
//
//            val chatCompletionRequest = ChatCompletionRequest(
//                model = ModelId("gpt-3.5-turbo"),
//                messages = listOf(
//                    ChatMessage(
//                        role = ChatRole.System,
//                        content = quizInstruction
//                    ),
//                    ChatMessage(
//                        role = ChatRole.User,
//                        content = diaryText
//                    )
//                ),
//                temperature = 0.7,
//                maxTokens = 200,
//                topP = 0.7,
//                frequencyPenalty = 0.0,
//                presencePenalty = 0.0
//            )
//            // Assistant 항목을 3개로 늘려서 Q1,Q2,Q3를 각각 도출하는 방안도 생각
//
//            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)
////            Log.d("ITM", "completion")
////            Log.d("ITM", "${completion}")
////
////            Log.d("ITM", "completion.choices")
////            Log.d("ITM", "${completion.choices}")
////
////            Log.d("ITM", "completion.choices.toString()")
////            Log.d("ITM", "${completion.choices.toString()}")
////
////            Log.d("ITM", "completion.choices.first()")
////            Log.d("ITM", "${completion.choices.first()}")
////
////            Log.d("ITM", "completion.choices.first().toString()")
////            Log.d("ITM", "${completion.choices.first().toString()}")
////
////            Log.d("ITM", "completion.choices.first().message.content")
//            Log.d("ITM", "${completion.choices.first().message.content}")
//
//
////            // 퀴즈 코드
////            val generatedQuiz: String? = completion.choices.firstOrNull()?.message?.content
////
////            // generatedQuiz가 null이 아닐 때만 parseGeneratedQuiz 호출
////            if (generatedQuiz != null) {
////                val quizzes = parseGeneratedQuiz(generatedQuiz)
////
////                // 각 퀴즈를 화면에 표시
////                displayQuizzes(quizzes)
////            }
//
//
//            // Handle the completion result here
//            binding.summaryTextView.text = completion.choices.first().message.content
//        }

    }

    private fun parseGeneratedQuiz(generatedQuiz: String): List<Quiz> {
        // 여기서 generatedQuiz를 파싱하여 실제 Quiz 객체들로 변환하는 로직을 구현
        // 실제로는 정규표현식이나 특정 규칙을 통해 파싱해야 할 것입니다.
        return emptyList() // 여기서는 빈 리스트를 반환하였으나 실제로는 파싱 로직을 구현해야 합니다.
    }

    private fun displayQuizzes(quizzes: List<Quiz>) {
        // 여기서는 각 퀴즈에 따라 화면에 표시하는 로직을 구현
        for (i in quizzes.indices) {
            val quiz = quizzes[i]
            when (quiz.getType()) {
                QuizType.MULTIPLE_CHOICE -> {
                    // 여기에 객관식 퀴즈 표시 로직 추가 (RecyclerView 등 활용)
                    // 예시: binding.multipleChoiceQuestionTextView.text = quiz.question
                }

                QuizType.SHORT_ANSWER -> {
                    // 여기에 주관식 퀴즈 표시 로직 추가 (EditText 등 활용)
                    // 예시: binding.shortAnswerQuestionEditText.hint = quiz.question
                }

                QuizType.TRUE_FALSE -> {
                    // 여기에 O/X 퀴즈 표시 로직 추가 (RadioButton 등 활용)
                    // 예시: binding.trueFalseQuestionRadioGroup.hint = quiz.question
                }
            }
        }
    }
}