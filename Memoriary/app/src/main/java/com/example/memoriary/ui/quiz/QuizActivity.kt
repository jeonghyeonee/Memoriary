package com.example.memoriary.ui.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aallam.openai.api.chat.ChatChoice
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
                //Log.d("ITM", "$apiKey")


                // Use lifecycleScope.launch for coroutine
                lifecycleScope.launch {
                    val openAI = OpenAI(
                        token = apiKey,
                        timeout = Timeout(socket = 60.seconds),
                        // additional configurations...
                    )

                    val modelId = ModelId("gpt-4")

                    val quizInstruction = "You are a Quiz Generator. Your mission is to generate three quizzes based on the input diary. Three quizzes are generated " +
                    "with three types of questions: \n" +
                            "Q1. Multiple-choice with 5 options, with four incorrect options, and one correct option. Labeling with number.\n" +
                            "Q2. Another multiple-choice with 5 options, but with four correct options, and one incorrect option. Question should involve 'not true'.\n" +
                            "Q3. True/false question based on diary events.\n" +
                            "You have to provide Q1 between string of <Q1> and <E1>, Q2 between string of <Q2> and <E2>, Q3 between string of <Q3> and <E3>.\n" +
                            "After providing quizzes, you have to provide answer of each quizzes. Just provide answer number." +
                            "You have to provide answer of Q1 between string of <A1> and <AE1>, answer of Q2 between string of <A2> and <AE2>, " +
                            "answer of Q3 between string of <A3> and <AE3>."


                    val generateQ1 = ChatCompletionRequest(
                        model = modelId,
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
                        maxTokens = 300,
                        topP = 0.7,
                        frequencyPenalty = 0.0,
                        presencePenalty = 0.0
                    )

                    val completion = openAI.chatCompletion(generateQ1)

                    Log.d("ITM", "completion.choices.first().message.content")
                    Log.d("ITM", "${completion.choices.first().message.content}")

                    val inputString = completion.choices.first().message.content.toString()

                    // 퀴즈 추출
                    val quiz1 = extractContent(inputString, "<Q1>", "<E1>")
                    val quiz2 = extractContent(inputString, "<Q2>", "<E2>")
                    val quiz3 = extractContent(inputString, "<Q3>", "<E3>")

                    // 답안 추출
                    val ans1 = extractContent(inputString, "<A1>", "<AE1>")
                    val ans2 = extractContent(inputString, "<A2>", "<AE2>")
                    val ans3 = extractContent(inputString, "<A3>", "<AE3>")

                    // 출력
                    Log.d("ITM", "Quiz 1: $quiz1")
                    Log.d("ITM", "Quiz 2: $quiz2")
                    Log.d("ITM", "Quiz 3: $quiz3")
                    Log.d("ITM", "Answer 1: $ans1")
                    Log.d("ITM", "Answer 2: $ans2")
                    Log.d("ITM", "Answer 3: $ans3")


                    // Handle the completion result here
//                    binding.tvQ1.text = completion.choices.first().message.content
//                    binding.tvQ2.text = "$quiz1\n" + "$quiz2\n" +"$quiz3\n" + "$ans1\n" + "$ans2\n" + "$ans3\n"
                    binding.tvQ1.text = quiz1
                    binding.tvQ2.text = quiz2
                    binding.tvQ3.text = quiz3

                    var userAnsQ1 = ""
                    var userAnsQ2 = ""
                    var userAnsQ3 = ""

                    binding.rg1.setOnCheckedChangeListener{ radioGroup, i ->
                        when(i) {
                            binding.rb11.id -> userAnsQ1 = binding.rb11.text.toString()
                            binding.rb12.id -> userAnsQ1 = binding.rb12.text.toString()
                            binding.rb13.id -> userAnsQ1 = binding.rb13.text.toString()
                            binding.rb14.id -> userAnsQ1 = binding.rb14.text.toString()
                            binding.rb15.id -> userAnsQ1 = binding.rb15.text.toString()
                        }
                    }

                    binding.rg2.setOnCheckedChangeListener{ radioGroup, i ->
                        when(i) {
                            binding.rb21.id -> userAnsQ2 = binding.rb21.text.toString()
                            binding.rb22.id -> userAnsQ2 = binding.rb22.text.toString()
                            binding.rb23.id -> userAnsQ2 = binding.rb23.text.toString()
                            binding.rb24.id -> userAnsQ2 = binding.rb24.text.toString()
                            binding.rb25.id -> userAnsQ2 = binding.rb25.text.toString()
                        }
                    }

                    binding.rg3.setOnCheckedChangeListener{ radioGroup, i ->
                        when(i) {
                            binding.rb31.id -> userAnsQ3 = binding.rb31.text.toString()
                            binding.rb32.id -> userAnsQ3 = binding.rb32.text.toString()
                        }
                    }


                    binding.btnSubmit.setOnClickListener {
                        var check1 = "Q1 incorrect"
                        var check2 = "Q2 incorrect"
                        var check3 = "Q3 incorrect"

                        Log.d("ITM", "($ans1) ($userAnsQ1)")
                        Log.d("ITM", "($ans2) ($userAnsQ2)")
                        Log.d("ITM", "($ans3) ($userAnsQ3)")

                        if (ans1 == userAnsQ1) {
                            check1 = "Q1 correct"
                            Log.d("ITM", "($ans1) ($userAnsQ1)")
                        }
                        if (ans2 == userAnsQ2) {
                            Log.d("ITM", "($ans2) ($userAnsQ2)")
                            check2 = "Q2 correct"
                        }
                        if (ans3 == userAnsQ3) {
                            Log.d("ITM", "($ans3) ($userAnsQ3)")
                            check3 = "Q3 correct"
                        }
                        binding.tvCheck.text = "${check1}\n${check2}\n${check3}"
                    }


                }

            }
            fun extractContent(input: String, startTag: String, endTag: String): String {
                val startIndex = input.indexOf(startTag) + startTag.length
                val endIndex = input.indexOf(endTag, startIndex)
                return input.substring(startIndex, endIndex).trim()
            }

            override fun onError(error: Exception) {
                Log.e("Thumb", "Error getting data", error)
            }
        })


    }


}