package com.example.memoriary.ui.diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memoriary.R
import com.example.memoriary.databinding.FragmentDiaryBinding
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Implement Diary Page

class DiaryFragment : Fragment(), WriteFragmentCallback {

private var _binding: FragmentDiaryBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    lateinit var writeTab: WriteFragment
    lateinit var thumbnailTab: ThumbnailsFragment
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DiaryViewModel::class.java)

    _binding = FragmentDiaryBinding.inflate(inflater, container, false)
    val root: View = binding.root

      writeTab = WriteFragment()
      thumbnailTab = ThumbnailsFragment()

      val fragments = arrayListOf<Fragment>(writeTab, thumbnailTab)
      val tabAdapter = object : FragmentStateAdapter(this){
          override fun getItemCount(): Int {
              return fragments.size
          }

          override fun createFragment(position: Int): Fragment {
              Log.d("Diary", "${fragments[position]} created")
              return fragments[position]
          }
      }
      binding.viewPager2.adapter = tabAdapter
      TabLayoutMediator(binding.tabLayout, binding.viewPager2) {tab,position ->
          when (position) {
              0 -> tab.setIcon(R.drawable.pencil)
              else -> tab.setIcon(R.drawable.diary)
          }
      }.attach()
    return root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        writeTab.setWriteFragmentCallback(this)
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDataSaved() {
        thumbnailTab.updateDataFromFirebase()
    }
}