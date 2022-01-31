package kg.geektech.youtubeapi.ui.activities.main

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kg.geektech.youtubeapi.base.BaseActivity
import kg.geektech.youtubeapi.data.model.Item
import kg.geektech.youtubeapi.databinding.ActivityMainBinding
import kg.geektech.youtubeapi.ui.activities.playlistItem.PlaylistItemActivity
import kg.geektech.youtubeapi.utilities.Constants

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    PlaylistAdapter.OnItemClick {


    override fun initViews() {
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
                        getInfo()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.rvPlaylists.visibility = View.GONE
                        binding.include.root.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun getInfo() {
        val adapter = PlaylistAdapter()
        adapter.setOnItemClick(this)
        binding.rvPlaylists.adapter = adapter
        viewModel.playlist(null).observe(this) {
            if (it != null) {
                adapter.setList(it.items)
            }
        }
    }

    override fun onClick(item: Item) {
        val intent = Intent(this, PlaylistItemActivity::class.java)
        intent.putExtra(Constants.KEY_PLAYLIST_ID, item.id)
        startActivity(intent)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        binding.include.btnTryAgain.setOnClickListener {
            connectionCheck()
        }
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
}