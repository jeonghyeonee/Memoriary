package com.example.memoriary.signInUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memoriary.R
import com.example.memoriary.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var alertDialog: AlertDialog

    // Declare FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()
        // Initialize FirebaseFirestore
        firestore = FirebaseFirestore.getInstance()

        // Sign Up 버튼 클릭 리스너 설정
        binding.buttonSignUp.setOnClickListener {
            signUp()
        }

        // NumberPicker의 범위 설정
        binding.numberPickerYear.minValue = 1900
        binding.numberPickerYear.maxValue = 2100

        binding.numberPickerMonth.minValue = 1
        binding.numberPickerMonth.maxValue = 12

        binding.numberPickerDay.minValue = 1
        binding.numberPickerDay.maxValue = 31
    }

    private fun signUp() {
        // 모든 필드에 대한 입력 확인
        val name = binding.nameInput.text.toString()
        val birthDate = "${binding.numberPickerYear.value}-${binding.numberPickerMonth.value}-${binding.numberPickerDay.value}"
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val passwordConfirmation = binding.editTextPasswordConfirmation.text.toString()
        val gender = if (binding.radioButtonMale.isChecked) "Male" else if (binding.radioButtonFemale.isChecked) "Female" else ""
        val agree = binding.checkBoxAgree.isChecked

        // 입력 유효성 검사
        if (name.isEmpty() || birthDate.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() || gender.isEmpty() || !agree) {
            // 모든 필드를 기입하도록 메시지 표시
            showToast("Please fill in all fields.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 사용자 등록 성공
                    showToast("User registered successfully.")
                    // Firestore에 사용자 정보 저장
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "gender" to gender,
                        "birthdate" to birthDate
                    )

                    firestore.collection("users")
                        .document(auth.currentUser?.uid ?: "")
                        .set(user)
                        .addOnSuccessListener {
                            // Firestore에 사용자 정보 저장 성공
                            showToast("User information saved to Firestore.")
                        }
                        .addOnFailureListener { e ->
                            // Firestore에 사용자 정보 저장 실패
                            showToast("Failed to save user information to Firestore. ${e.message}")
                        }

                    // 팝업 메시지 표시
                    showSignupCompletedDialog()
                } else {
                    // 사용자 등록 실패
                    showToast("User registration failed. ${task.exception?.message}")
                }
            }

        // 회원 가입이 완료되면 팝업 메시지 표시
//        showSignupCompletedDialog()
    }

    private fun showSignupCompletedDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_signup_completed, null)

        val btnSignIn = view.findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener {
            // SignIn 버튼 클릭 시 SignIn 페이지로 이동
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            alertDialog.dismiss() // AlertDialog 닫기
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false) // 뒤로 가기 버튼으로 닫히지 않도록 설정

        alertDialog = alertDialogBuilder.create() // Initialize alertDialog
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
