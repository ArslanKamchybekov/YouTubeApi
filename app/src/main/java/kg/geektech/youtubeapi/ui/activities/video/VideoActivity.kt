@file:Suppress("DEPRECATION")

package kg.geektech.youtubeapi.ui.activities.video

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import kg.geektech.youtubeapi.R
import kg.geektech.youtubeapi.core.base.BaseActivity
import kg.geektech.youtubeapi.core.network.Status
import kg.geektech.youtubeapi.databinding.ActivityVideoBinding
import kg.geektech.youtubeapi.utilities.Constants

class VideoActivity :
    BaseActivity<VideoViewModel, ActivityVideoBinding>() {

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override val viewModel: VideoViewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun initViews() {
        initializePlayer()
        connectionCheck()
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()

            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentWindow, playbackPosition)
                exoPlayer.prepare()
            }
            .also { exoPlayer ->
                val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
                exoPlayer.setMediaItem(mediaItem)

            }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoBinding {
        return ActivityVideoBinding.inflate(layoutInflater)
    }

    private fun connectionCheck() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkChangeFilter = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(networkChangeFilter,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        binding.container.visibility = View.VISIBLE
                        binding.videoView.visibility = View.VISIBLE
                        binding.include.root.visibility = View.GONE
                        binding.toolbar.visibility = View.VISIBLE
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.container.visibility = View.GONE
                        binding.videoView.visibility = View.GONE
                        binding.include.root.visibility = View.VISIBLE
                        binding.toolbar.visibility = View.GONE
                    }
                }
            }
        )
    }

    override fun initViewModel() {
        intent.getStringExtra(Constants.VIDEO_ID)?.let { it ->
            viewModel.getVideos(it).observe(this) { resource ->
                if (resource.status == Status.SUCCESS) {
                    binding.tvTitle.text = resource.data?.items?.get(0)?.snippet?.title
                    binding.tvDescriptionVideo.text =
                        resource.data?.items?.get(0)?.snippet?.description
                } else if (resource.status == Status.ERROR) {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer()
        }
    }

    private fun hideSystemUi() {
        binding.videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }


    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }
}

