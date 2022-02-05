package kg.geektech.youtubeapi.ui.activities.playlistItem

import androidx.lifecycle.LiveData
import kg.geektech.App
import kg.geektech.youtubeapi.core.base.BaseViewModel
import kg.geektech.youtubeapi.core.network.Resource
import kg.geektech.youtubeapi.data.model.Playlist

class ItemPlaylistViewModel : BaseViewModel() {

    fun getItemPlaylists(playlistId: String, pageToken: String?): LiveData<Resource<Playlist?>> {
        return App.repository.getItemPlaylists(playlistId, pageToken)
    }
}