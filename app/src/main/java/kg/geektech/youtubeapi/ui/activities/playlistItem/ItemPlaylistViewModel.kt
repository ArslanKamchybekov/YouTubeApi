package kg.geektech.youtubeapi.ui.activities.playlistItem

import androidx.lifecycle.LiveData
import kg.geektech.App
import kg.geektech.youtubeapi.core.base.BaseViewModel
import kg.geektech.youtubeapi.data.model.Playlist

class ItemPlaylistViewModel : BaseViewModel() {

    fun getDetailPlaylists(playlistId: String, pageToken: String?): LiveData<Playlist> {
        return App.repository.getItemPlaylists(playlistId, pageToken)
    }
}