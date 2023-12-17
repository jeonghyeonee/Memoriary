package com.example.memoriary.ui.diary

import ThumbnailAdapter
import ThumbnailAdapter.Companion.posts
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ThumbnailsFragment : Fragment() {
    lateinit var binding: FragmentThumbnailsBinding

    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    interface FirebaseCallBack {
        fun onDataLoaded(posts: MutableList<Post>)
        fun onError(error: Exception)
    }
    private fun readFromFirebase(callback: FirebaseCallBack) {

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
//        if (user != null) {
//            email = user.email!!
//            userName = email.substring(0, email.indexOf('@'))
//        }
        var email = user?.email!!
        var userName = email.substring(0, email.indexOf('@'))

        val userId = userName
        //val userId = "rainday0828"
        database = Firebase.database.reference
        val posts = mutableListOf<Post>()

        database.child(userId).child("posts").get().addOnSuccessListener {
            for (post in it.children) {
                val key = post.key.toString()
                val title = post.child("title").value.toString()
                val content = post.child("content").value.toString()
                val image = post.child("image").child("0").child("url").value.toString()
                val post = Post(key, title, content, image)
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

    private fun updateAdapterAfterDeletion(post: Post) {
        val thumbnailAdapter = ThumbnailAdapter(posts)
        binding.thumbnailsRecyclerView.adapter = thumbnailAdapter
        binding.thumbnailsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}

