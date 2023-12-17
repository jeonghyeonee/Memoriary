package com.example.memoriary.signInUp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.memoriary.MainActivity
import com.example.memoriary.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class SignInActivity : AppCompatActivity() {

    // Declare FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySigninBinding

    // Fingerprint login
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    // Shared preference
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Sign In 버튼 클릭 리스너 설정
        binding.buttonSignIn.setOnClickListener {
            var email = binding.editTextID.text.toString()
            var password = binding.editTextPassword.text.toString()

            // 개발중 편의를 위해 임시로 ID, PW 입력해놓는 변수 넣어놓음
//            email = "rainday0828@naver.com"
//            password = "000000"

            signIn(email, password)
        }


        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    // 개발중 편의를 위해 임시로 ID, PW 입력해놓는 변수 넣어놓음
//                    var email = "rainday0828@naver.com"
//                    var password = "000000"

                    // 자동 로그인 확인
                    if (isLoggedIn()) {
                        // 로그인 정보가 저장되어 있으면
                        var email = sharedPreferences.getString("username", "")
                        var password = sharedPreferences.getString("password", "")
                        signIn(email!!, password!!)
                        Toast.makeText(
                            applicationContext,
                            "Authentication succeeded!", Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        // 없으면
                        Toast.makeText(
                            applicationContext,
                            "You must login first", Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        binding.ivFinger.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
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

                    // sharedPreference에 email, pw 저장
                    saveLoginInfo(email, password)

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

    private fun saveLoginInfo(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        // 저장된 로그인 정보가 있는지 확인합니다.
        return sharedPreferences.contains("username") && sharedPreferences.contains("password")
    }
}
