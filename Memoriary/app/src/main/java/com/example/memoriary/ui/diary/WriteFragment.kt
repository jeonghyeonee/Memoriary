package com.example.memoriary.ui.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageJSON
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.image.ImageURL
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.memoriary.R
import com.example.memoriary.databinding.FragmentWriteBinding
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

interface WriteFragmentCallback {
    fun onDataSaved()
}
class WriteFragment : Fragment() {
    lateinit var binding : FragmentWriteBinding
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private var writeFragmentCallback: WriteFragmentCallback? = null
    lateinit var thumbnailsFragment: ThumbnailsFragment

    private fun saveToFirebase(title: String, content: String, image: List<ImageURL>) {
        val currentUserID = "rainday0828"
        val newPostRef = databaseReference.child(currentUserID).child("posts").push()

        val post = HashMap<String, Any>()
        post["title"] = title
        post["content"] = content
        post["image"] = image

        newPostRef.setValue(post)
            .addOnSuccessListener {
                Log.d("Database", "Succeed!!")
            }
            .addOnFailureListener {
                Log.d("Database", "Failed!!")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = binding.submitDiaryButton
        submitButton.setOnClickListener{
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                CoroutineScope(Main).launch {
                    val image=imageGen(content)
                    saveToFirebase(title, content, image)
                    Log.d("OpenAI", "Succeed!")
                }
            } else {
                // Handle empty title or content
            }
        }
    }

    fun setWriteFragmentCallback (callback: WriteFragmentCallback) {
        writeFragmentCallback = callback
    }

    val apiKey = com.example.memoriary.BuildConfig.OPENAI_API_KEY
    suspend fun imageGen(content: String): List<ImageURL>{
        val openai = OpenAI(
            token = apiKey,
            timeout = Timeout(socket = 60.seconds)
        )

        val image = openai.imageURL(
            creation = ImageCreation(
                prompt = content,
                n = 1,
                size = ImageSize.is256x256
            )
        )
        return image
    }
}