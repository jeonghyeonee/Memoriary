package com.example.memoriary.ui.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.memoriary.ui.todo.adapter.ViewPagerAdapter
import com.example.memoriary.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TodoFragment", "onCreateView")
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TodoFragment", "onCreateView")

        // Tab selected listener (optional)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab selected
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselected
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselected
            }
        })

        // Set up the ViewPager with the adapter
        binding.pager.adapter = ViewPagerAdapter(this)

        // Connect TabLayout and ViewPager
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "TodoList"
                1 -> tab.text = "Calendar"
                2 -> tab.text = "DoneList"
                else -> throw IllegalArgumentException("Invalid position")
            }
        }.attach()
    }

    override fun onDestroyView() {
        Log.d("TodoFragment", "onDestroyView")
        super.onDestroyView()
    }
}