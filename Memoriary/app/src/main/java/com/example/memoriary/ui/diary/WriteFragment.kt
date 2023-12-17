package com.example.memoriary.ui.diary

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_NO_MATCH
import android.speech.SpeechRecognizer.ERROR_SPEECH_TIMEOUT
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.image.ImageURL
import com.aallam.openai.client.OpenAI
import android.Manifest
import com.example.memoriary.BuildConfig
import com.example.memoriary.databinding.FragmentWriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

interface WriteFragmentCallback {
    fun onDataSaved()
}
class WriteFragment : Fragment() {
    lateinit var binding : FragmentWriteBinding

    lateinit var auth: FirebaseAuth
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private var writeFragmentCallback: WriteFragmentCallback? = null
    lateinit var thumbnailsFragment: ThumbnailsFragment
    private val SPEECH_REQUEST_CODE_TITLE = 1
    private val SPEECH_REQUEST_CODE_CONTENT = 2
    private val RECORD_AUDIO_PERMISSION_CODE = 200

    private fun requestMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        }
    }
    private fun saveToFirebase(title: String, content: String, image: List<ImageURL>) {

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        var email = user?.email!!
        var userName = email.substring(0, email.indexOf('@'))

        val currentUserID = userName
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
        requestMicrophonePermission()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleListen.setOnClickListener {
            startSpeechToText(SPEECH_REQUEST_CODE_TITLE)
        }

        // Microphone icon click listener for content
        binding.contentListen.setOnClickListener {
            startSpeechToText(SPEECH_REQUEST_CODE_CONTENT)
        }

        val submitButton = binding.submitDiaryButton
        submitButton.setOnClickListener{
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()


            if (title.isNotEmpty() && content.isNotEmpty()) {
                CoroutineScope(Main).launch {
                    val image=imageGen(content)
                    saveToFirebase(title, content, image)
                    Log.d("ITM", "Succeed!")
                }
            } else {
                // Handle empty title or content
            }
        }
    }

    fun setWriteFragmentCallback (callback: WriteFragmentCallback) {
        writeFragmentCallback = callback
    }

    suspend fun imageGen(content: String): List<ImageURL>{
        val apiKey = BuildConfig.OPENAI_API_KEY

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
    private fun startSpeechToText(requestCode: Int) {
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        startActivityForResult(speechIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = matches?.get(0) // Get the recognized text

            when (requestCode) {
                SPEECH_REQUEST_CODE_TITLE -> {
                    binding.title.setText(spokenText)
                }
                SPEECH_REQUEST_CODE_CONTENT -> {
                    binding.content.setText(spokenText)
                }
            }
        }
    }

}