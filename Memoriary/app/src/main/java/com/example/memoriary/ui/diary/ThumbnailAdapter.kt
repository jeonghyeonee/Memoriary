import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoriary.databinding.ItemViewBinding
import com.example.memoriary.ui.diary.Post

class ThumbnailAdapter(val posts: MutableList<Post>): RecyclerView.Adapter<ThumbnailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("adapter", "here!")
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thumbnail = posts.get(position)
        holder.bind(thumbnail)
    }

    class ViewHolder(val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            Log.d("adapter", "title changed!")
            binding.title.text = post.title
            binding.content.text = post.content
        }
    }


}
