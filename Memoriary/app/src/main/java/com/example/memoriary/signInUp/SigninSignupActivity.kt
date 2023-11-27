package com.example.memoriary.signInUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.memoriary.R

class SigninSignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

        val signInButton: Button = findViewById(R.id.signInButton)
        val signUpButton: Button = findViewById(R.id.signUpButton)

        // SignIn 버튼 클릭 시
        signInButton.setOnClickListener {
            // TODO: SignIn 버튼 클릭 시 동작할 내용을 추가하세요.
            // 예를 들어, SignInActivity로 전환하는 코드를 추가할 수 있습니다.
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
        }

        // SignUp 버튼 클릭 시
        signUpButton.setOnClickListener {
            // TODO: SignUp 버튼 클릭 시 동작할 내용을 추가하세요.
            // 예를 들어, SignUpActivity로 전환하는 코드를 추가할 수 있습니다.
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }
}

