package com.example.memoriary.ui.diary

import ThumbnailAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoriary.MainActivity
import com.example.memoriary.R
import com.example.memoriary.databinding.FragmentThumbnailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ThumbnailsFragment : Fragment() {
    lateinit var binding: FragmentThumbnailsBinding
    private lateinit var database: DatabaseReference

    interface FirebaseCallBack {
        fun onDataLoaded(posts: MutableList<Post>)
        fun onError(error: Exception)
    }
    private fun readFromFirebase(callback: FirebaseCallBack) {
        val userId = "rainday0828"
        database = Firebase.database.reference
        val posts = mutableListOf<Post>()

        database.child(userId).child("posts").get().addOnSuccessListener {
            for (post in it.children) {
                val title = post.child("title").value.toString()
                val content = post.child("content").value.toString()
                val post = Post(title, content)
                Log.d("Thumb", "$post")
                posts.add(post)
            }
            Log.d("Thumb", "$posts")
            callback.onDataLoaded(posts)
        }.addOnFailureListener{error ->
            callback.onError(error)
        }
        Log.d("Thumb", "$posts")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThumbnailsBinding.inflate(inflater, container, false)
        readFromFirebase(object: FirebaseCallBack{
            override fun onDataLoaded(posts: MutableList<Post>) {
                val thumbnailAdapter = ThumbnailAdapter(posts)
                binding.thumbnailsRecyclerView.adapter = thumbnailAdapter
                binding.thumbnailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

            override fun onError(error: Exception) {
                Log.e("Thumb", "Error getting data", error)
            }
        })
        return binding.root
    }

    fun updateDataFromFirebase() {
        readFromFirebase(object : FirebaseCallBack {
            override fun onDataLoaded(posts: MutableList<Post>) {
                val thumbnailAdapter = ThumbnailAdapter(posts)
                binding.thumbnailsRecyclerView.adapter = thumbnailAdapter
                binding.thumbnailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

            override fun onError(error: Exception) {
                Log.e("Thumb", "Error getting data", error)
            }
        })
    }
}

