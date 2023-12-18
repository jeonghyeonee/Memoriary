package com.example.memoriary.ui.todo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoriary.ui.todo.adapter.TodoAdapter
import com.example.memoriary.databinding.FragmentDonelistBinding
import com.example.memoriary.ui.todo.model.Memo
import com.example.memoriary.ui.todo.viewmodel.MemoViewModel

class DoneListFragment : Fragment() {

    private var binding: FragmentDonelistBinding? = null
    private val memoViewModel: MemoViewModel by viewModels()
    private val adapter: TodoAdapter by lazy { TodoAdapter(memoViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonelistBinding.inflate(inflater, container, false)
        adapter.setHasStableIds(true)
        binding!!.doneRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding!!.doneRecyclerView.adapter = adapter

        memoViewModel.memoList.observe(viewLifecycleOwner, Observer { memoList ->
            // 데이터가 변경될 때마다 호출되는 코드
            adapter.setData(memoList)
        })

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memoViewModel.readDoneMemos()

        memoViewModel.readDoneData.observe(viewLifecycleOwner, Observer { doneMemos ->
            updateUI(doneMemos)
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun updateUI(doneMemos: List<Memo>) {
        Handler(Looper.getMainLooper()).post {
            adapter.setData(doneMemos)
        }
    }
}
