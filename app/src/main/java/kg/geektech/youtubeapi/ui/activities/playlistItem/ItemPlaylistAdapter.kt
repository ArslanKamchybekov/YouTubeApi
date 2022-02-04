package kg.geektech.youtubeapi.ui.activities.playlistItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubeapi.core.extensions.load
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ItemPlaylistItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ItemPlaylistAdapter(private val onItemClick: OnItemClick?) :
    RecyclerView.Adapter<ItemPlaylistAdapter.ViewHolder>() {

    private var list = arrayListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemPlaylistAdapter.ViewHolder {
        val binding =
            ItemPlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemPlaylistAdapter.ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Item>) {
        this.list.clear()
        this.list.addAll(list)
        list.forEachIndexed { index, _ ->
            notifyItemChanged(index)
        }
    }

    inner class ViewHolder(private val binding: ItemPlaylistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            item.snippet?.thumbnails?.high?.url?.let { binding.ivPlaylist.load(it) }
            binding.tvTitle.text = item.snippet?.title
            val videoCount = item.contentDetails?.videoPublishedAt
            binding.tvVideoTime.text = formatDate(videoCount)

            itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
        }

        private fun formatDate(dateString: String?): String? {
            return if (dateString == null) {
                null
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date: Date = format.parse(dateString) ?: Calendar.getInstance().time
                val newFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
                newFormat.format(date)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}