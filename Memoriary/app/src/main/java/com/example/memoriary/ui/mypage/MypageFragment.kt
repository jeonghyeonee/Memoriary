package com.example.memoriary.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memoriary.MainActivity
import com.example.memoriary.R
import com.example.memoriary.databinding.FragmentMypageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.btnSetting.setOnClickListener {
            // setting을 activity로 할지 fragment로 할지 고민
        }

        binding.btnChangeMyProfile.setOnClickListener {
            activity?.let {
                val intent = Intent(context, ChangeprofileActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnAnnouncements.setOnClickListener {
            activity?.let {
                val intent = Intent(context, AnnouncementsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnEvents.setOnClickListener {
            activity?.let {
                val intent = Intent(context, EventsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnCustomerService.setOnClickListener {
            activity?.let {
                val intent = Intent(context, CustomerserviceActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnQuestion.setOnClickListener {
            activity?.let {
                val intent = Intent(context, QuestionActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnSignOut.setOnClickListener {
            // 파이어베이스 로그아웃 기능 적용하고, 로그인 페이지로 intent해야함
            activity?.let {
                val intent = Intent(context, MainActivity::class.java)  // 임시로 main activity로 연결함
                startActivity(intent)
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}