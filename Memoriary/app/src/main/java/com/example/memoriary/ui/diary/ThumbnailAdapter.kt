import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoriary.databinding.ItemViewBinding
import com.example.memoriary.ui.diary.Post
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ThumbnailAdapter(val posts: MutableList<Post>): RecyclerView.Adapter<ThumbnailAdapter.ViewHolder>() {
    companion object {
        lateinit var posts: MutableList<Post>
    }
    init {
        Companion.posts = posts
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("adapter", "here!")
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thumbnail = posts[position]
        holder.bind(thumbnail)
    }

    class ViewHolder(val binding: ItemViewBinding, private val adapter: ThumbnailAdapter): RecyclerView.ViewHolder(binding.root) {
        lateinit var database: DatabaseReference

        fun bind(post: Post){
            Log.d("adapter", "title changed!")
            binding.title.text = post.title
            binding.content.text = post.content
            binding.deleteButton.setOnClickListener {
                // Call a function to delete the post from Firebase
                Log.d("ThumbnailAdapter", "Delete button clicked!")
                deletePost(post)
            }
        }

        private fun deletePost(post: Post) {
            val userId = "rainday0828"
            database = Firebase.database.reference

            // Remove the post from the database
            database.child(userId).child("posts").child(post.key).removeValue()
                .addOnSuccessListener {
                    Log.d("ThumbnailAdapter", "Deleting")
                    // If deletion is successful, update the adapter
                    updateAdapterAfterDeletion(post)
                }
                .addOnFailureListener { error ->
                    // Handle the error
                    Log.e("ThumbnailAdapter", "Error deleting post", error)
                }
        }

        private fun updateAdapterAfterDeletion(post: Post) {
            posts.remove(post)
            Log.d("ThumbnailAdapter", "Post removed")
            // Notify the adapter that the data set has changed
            adapter.notifyDataSetChanged()
        }


    }


}
