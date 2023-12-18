package com.example.memoriary.ui.todo

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoriary.ui.todo.dialog.MyCustomDialog
import com.example.memoriary.ui.todo.dialog.MyCustomDialogInterface
import com.example.memoriary.ui.todo.adapter.TodoAdapter
import com.example.memoriary.databinding.FragmentTodolistBinding
import com.example.memoriary.ui.todo.model.Memo
import com.example.memoriary.ui.todo.viewmodel.MemoViewModel
import java.util.*

class TodoListFragment : Fragment(), MyCustomDialogInterface {

    private var binding: FragmentTodolistBinding? = null
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결
    private val adapter: TodoAdapter by lazy { TodoAdapter(memoViewModel) } // 어댑터 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodolistBinding.inflate(inflater, container, false)

        adapter.setHasStableIds(true)

        binding!!.todoRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding!!.todoRecyclerView.adapter = adapter

        // LiveData를 사용하여 데이터를 관찰
        memoViewModel.memoList.observe(viewLifecycleOwner, Observer { memoList ->
            // 데이터가 변경될 때마다 호출되는 코드
            adapter.setData(memoList)
        })

        binding!!.dialogButton.setOnClickListener {
            onFabClicked()
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onOkButtonClicked(content: String) {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DATE)

        val newMemo = Memo(0, false, content, year, month, day)

        adapter.addMemo(newMemo)
        memoViewModel.addMemo(newMemo)

        Log.d("TODOLIST", newMemo.toString())
    }

    private fun onFabClicked() {
        val myCustomDialog = MyCustomDialog(requireActivity(), this)
        myCustomDialog.show()
    }
}
