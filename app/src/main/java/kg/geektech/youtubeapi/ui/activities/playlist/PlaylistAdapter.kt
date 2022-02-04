package kg.geektech.youtubeapi.ui.activities.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.youtubeapi.core.extensions.load
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ItemPlaylistBinding

class PlaylistAdapter(private val onItemClick: OnItemClick?) :
RecyclerView.Adapter<PlaylistAdapter.MainViewHolder>() {

    private var list = arrayListOf<Item>()

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

    fun setList(list: List<Item>) {
        this.list.clear()
        this.list.addAll(list)
        list.forEachIndexed { index, _ ->
            notifyItemChanged(index)
        }
    }

    inner class MainViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            item.snippet?.thumbnails?.high?.url?.let { binding.ivPlaylist.load(it) }
            binding.tvTitle.text = item.snippet?.localized?.title
            val videoCount = item.contentDetails?.itemCount.toString() + " video series"
            binding.tvAmount.text = videoCount

            itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}