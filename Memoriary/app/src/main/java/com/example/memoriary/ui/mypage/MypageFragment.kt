package com.example.memoriary.ui.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.memoriary.MainActivity
import com.example.memoriary.R
import com.example.memoriary.databinding.FragmentMypageBinding
import com.example.memoriary.signInUp.SigninSignupActivity
import com.example.memoriary.ui.quiz.QuizActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentMypageBinding

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage
    lateinit var uri: Uri

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        // Initialize SharedPreferences using the context of the hosting activity
        sharedPreferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val user = auth.currentUser
        if (user != null) {
            val email = user.email!!
            val userName = email.substring(0, email.indexOf('@'))

            binding.tvWelcome.text = "Hello, " + userName
            binding.tvProfile.text = "Name: " + userName + "\nAge: 72\nother specifics"
        }

        database.get().addOnSuccessListener {
            it.child("rainday0828").child("posts").let {
                for (i in it.children) {
                    var data = i
                    Log.d("ITM", "data: $data")
                }
            }

        }

        storage = FirebaseStorage.getInstance()
        binding.btnChangeMyProfile.setOnClickListener {
            activity?.let {
                // ACTION_PICK을 사용하여 앨범을 호출한다.
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                registerForActivityResult.launch(intent)
            }
        }


        binding.btnSetting.setOnClickListener {
            // setting을 activity로 할지 fragment로 할지 고민
            activity?.let {
                val intent = Intent(context, QuizActivity::class.java)
                startActivity(intent)
                imageUpload(uri)
            }
        }

        binding.btnAnnouncements.setOnClickListener {
            activity?.let {
                val intent = Intent(context, AnnouncementsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnEvents.setOnClickListener {
            activity?.let {
                val intent = Intent(context, EventsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnCustomerService.setOnClickListener {
            activity?.let {
                val intent = Intent(context, CustomerserviceActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnQuestion.setOnClickListener {
            activity?.let {
                val intent = Intent(context, QuestionActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnSignOut.setOnClickListener {
            activity?.let {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(context, SigninSignupActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {

                    // 변수 uri에 전달 받은 이미지 uri를 넣어준다.
                    uri = result.data?.data!!
                    // 이미지를 ImageView에 표시한다.
                    binding.imageView.setImageURI(uri)
                }
            }
        }

    private fun imageUpload(uri: Uri) {
        // storage 인스턴스 생성
        val storage = FirebaseStorage.getInstance()
        // storage 참조
        val storageRef = storage.getReference("image")
        // storage에 저장할 파일명 선언
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mountainsRef = storageRef.child("${fileName}.png")

        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            Toast.makeText(getActivity(), "사진 업로드 성공", Toast.LENGTH_SHORT).show();
        }.addOnFailureListener {
            // 파일 업로드 실패
            Toast.makeText(getActivity(), "사진 업로드 실패", Toast.LENGTH_SHORT).show();
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}