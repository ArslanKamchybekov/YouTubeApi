package kg.geektech.youtubeapi.ui.activities.playlistItem

import android.view.LayoutInflater
import android.widget.Toast
import kg.geektech.youtubeapi.base.BaseActivity
import kg.geektech.youtubeapi.base.BaseViewModel
import kg.geektech.youtubeapi.databinding.ActivityPlaylistItemBinding
import kg.geektech.youtubeapi.utilities.Constants

class PlaylistItemActivity(override val viewModel: BaseViewModel) :
    BaseActivity<BaseViewModel, ActivityPlaylistItemBinding>() {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistItemBinding {
        return ActivityPlaylistItemBinding.inflate(layoutInflater)
    }

    override fun init() {
        intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}