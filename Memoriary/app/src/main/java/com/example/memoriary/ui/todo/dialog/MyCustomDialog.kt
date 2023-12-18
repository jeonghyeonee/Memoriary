package com.example.memoriary.ui.todo.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.memoriary.databinding.LayoutTodoDialogBinding

class MyCustomDialog(context: Context, myInterface: MyCustomDialogInterface) : Dialog(context) {

    private val myCustomDialogInterface: MyCustomDialogInterface = myInterface
    private lateinit var binding: LayoutTodoDialogBinding // ViewBinding 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutTodoDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okButton: Button = binding.okButton
        val cancelButton: Button = binding.cancelButton
        val memoEditView: EditText = binding.memoEditView

        okButton.setOnClickListener {
            val content = memoEditView.text.toString()

            // 입력하지 않았을 때
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(context, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 메모를 추가해줌
                myCustomDialogInterface.onOkButtonClicked(content)
                dismiss()
            }
        }

        // 취소 버튼 클릭 시 종료
        cancelButton.setOnClickListener { dismiss() }
    }
}
