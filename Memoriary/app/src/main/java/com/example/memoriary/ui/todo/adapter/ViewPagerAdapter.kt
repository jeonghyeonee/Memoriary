package com.example.memoriary.ui.todo.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memoriary.ui.todo.CalendarFragment
import com.example.memoriary.ui.todo.DoneListFragment
import com.example.memoriary.ui.todo.TodoListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoListFragment()
            1 -> CalendarFragment()
            2 -> DoneListFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}