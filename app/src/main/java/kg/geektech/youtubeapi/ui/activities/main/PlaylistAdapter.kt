package kg.geektech.youtubeapi.ui.activities.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ItemPlaylistBinding
import kg.geektech.youtubeapi.extensions.load

class PlaylistAdapter :
    RecyclerView.Adapter<PlaylistAdapter.MainViewHolder>() {

    private var list = arrayListOf<Item>()
    private lateinit var onItemClick: OnItemClick

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistAdapter.MainViewHolder {
        val binding =
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    inner class MainViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            binding.ivPlaylist.load(item.snippet.thumbnails.high.url)
            binding.tvTitle.text = item.snippet.localized.title
            val videoCount = item.contentDetails.itemCount.toString() + " videos"
            binding.tvAmount.text = videoCount
            binding.tvPlaylist.text = item.kind
            itemView.setOnClickListener {
                onItemClick.onClick(item)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}