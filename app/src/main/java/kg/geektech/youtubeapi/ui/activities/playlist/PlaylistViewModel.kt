package kg.geektech.youtubeapi.ui.activities.playlist

import androidx.lifecycle.LiveData
import kg.geektech.App
import kg.geektech.youtubeapi.core.base.BaseViewModel
import kg.geektech.youtubeapi.data.model.Playlist

class PlaylistViewModel : BaseViewModel() {

    fun getPlaylists(pageToken: String?): LiveData<Playlist> {
        return App.repository.getPlaylists(pageToken)
    }
}