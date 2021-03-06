package kg.geektech.youtubeapi.ui.activities.playlist

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import kg.geektech.youtubeapi.core.base.BaseActivity
import kg.geektech.youtubeapi.core.network.Status
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ActivityPlaylistBinding
import kg.geektech.youtubeapi.ui.activities.playlistItem.ItemPlaylistActivity
import kg.geektech.youtubeapi.utilities.Constants

class PlaylistActivity : BaseActivity<PlaylistViewModel, ActivityPlaylistBinding>(),
    PlaylistAdapter.OnItemClick {

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }
    private val adapter: PlaylistAdapter by lazy {
        PlaylistAdapter(this)
    }

    private fun initRecyclerView() {
        binding.rvPlaylists.apply {
            adapter = this@PlaylistActivity.adapter
        }
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
                        binding.include.root.visibility = View.GONE
                        binding.rvPlaylists.visibility = View.VISIBLE
                        binding.toolbar.visibility = View.VISIBLE
                        binding.viewSupporter.visibility = View.VISIBLE
                        getInfo()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.rvPlaylists.visibility = View.GONE
                        binding.toolbar.visibility = View.GONE
                        binding.viewSupporter.visibility = View.GONE
                        binding.include.root.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun getInfo() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }

        viewModel.getPlaylists(null).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.items?.let { it1 -> adapter.setList(it1) }
                    viewModel.loading.postValue(false)
                }
                Status.ERROR -> {
                    Toast.makeText(baseContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                    viewModel.loading.postValue(false)
                }
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
            }
        }
    }

    override fun onClick(item: Item) {
        Intent(this, ItemPlaylistActivity::class.java).apply {
            putExtra(Constants.KEY_PLAYLIST_ID, item.id)
            putExtra(Constants.KEY_PLAYLIST_TITLE, item.snippet?.title)
            putExtra(Constants.KEY_PLAYLIST_DESC, item.snippet?.description)
            startActivity(this)
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        binding.include.btnTryAgain.setOnClickListener {
            connectionCheck()
        }
    }

    override fun onStart() {
        super.onStart()
        connectionCheck()
    }
}