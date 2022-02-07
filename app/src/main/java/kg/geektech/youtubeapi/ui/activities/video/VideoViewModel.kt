package kg.geektech.youtubeapi.ui.activities.video

import androidx.lifecycle.LiveData
import kg.geektech.App
import kg.geektech.youtubeapi.core.base.BaseViewModel
import kg.geektech.youtubeapi.core.network.Resource
import kg.geektech.youtubeapi.data.model.Playlist

class VideoViewModel: BaseViewModel() {

    fun getVideos(videosId: String): LiveData<Resource<Playlist>> {
        return App.repository.getVideos(videosId)
    }
}