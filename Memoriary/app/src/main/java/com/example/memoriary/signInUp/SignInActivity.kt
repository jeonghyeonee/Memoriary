package com.example.memoriary.signInUp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memoriary.MainActivity
import com.example.memoriary.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    // Declare FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Sign In 버튼 클릭 리스너 설정
        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextID.text.toString()
            val password = binding.editTextPassword.text.toString()
            signIn(email, password)
        }

        // TODO: Sign Up 버튼 클릭 리스너 설정
        binding.buttonSignUp.setOnClickListener {
            // Sign Up 버튼 클릭 시 SignUpActivity로 이동
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    showToast("Sign in successful.")
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish() // 현재 액티비티를 종료하여 뒤로가기 시 MainActivity로 돌아가지 않도록 함
                } else {
                    // 로그인 실패
                    showToast("Sign in failed. ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
