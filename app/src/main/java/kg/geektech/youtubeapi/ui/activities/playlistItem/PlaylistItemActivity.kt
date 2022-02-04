package kg.geektech.youtubeapi.ui.activities.playlistItem

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kg.geektech.youtubeapi.core.base.BaseActivity
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ActivityPlaylistItemBinding
import kg.geektech.youtubeapi.utilities.Constants

class PlaylistItemActivity :
    BaseActivity<ItemPlaylistViewModel, ActivityPlaylistItemBinding>(),
    ItemPlaylistAdapter.OnItemClick {

    override val viewModel: ItemPlaylistViewModel by lazy {
        ViewModelProvider(this)[ItemPlaylistViewModel::class.java]
    }
    private val adapter: ItemPlaylistAdapter by lazy {
        ItemPlaylistAdapter(this)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistItemBinding {
        return ActivityPlaylistItemBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        initRecyclerView()
        connectionCheck()
    }

    private fun connectionCheck() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkChangeFilter = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(networkChangeFilter,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        binding.appBar.visibility = View.VISIBLE
                        binding.fabPlay.visibility = View.VISIBLE
                        binding.rvPlaylistItems.visibility = View.VISIBLE
                        binding.include.root.visibility = View.GONE

                        binding.tvTitlePlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_TITLE)

                        binding.tvDescriptionPlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_DESC)

                        intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let { getData(it) }
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.appBar.visibility = View.GONE
                        binding.fabPlay.visibility = View.GONE
                        binding.rvPlaylistItems.visibility = View.GONE
                        binding.include.root.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun initRecyclerView() {
        binding.rvPlaylistItems.apply {
            adapter = this@PlaylistItemActivity.adapter
        }
    }

    private fun getData(playlistId: String) {
        viewModel.getDetailPlaylists(playlistId, null).observe(this) {
            it.items?.let { it1 -> adapter.setList(it1) }
            val videoCount = it.pageInfo?.totalResults.toString() + " video series"
            binding.tvCount.text = videoCount
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onClick(item: Item) {

    }
}