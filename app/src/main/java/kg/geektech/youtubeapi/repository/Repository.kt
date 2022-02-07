package kg.geektech.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kg.geektech.App.Companion.apiService
import kg.geektech.youtubeapi.BuildConfig.API_KEY
import kg.geektech.youtubeapi.core.network.Resource
import kg.geektech.youtubeapi.data.model.Playlist
import kg.geektech.youtubeapi.utilities.Constants
import kotlinx.coroutines.Dispatchers

class Repository {

    fun getPlaylists(pageToken: String?): LiveData<Resource<Playlist?>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val response = apiService.getPlaylists(
            Constants.PART,
            Constants.CHANNEL_ID,
            API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        )
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        } else {
            emit(Resource.error(response.message(), response.body(), response.code()))
        }
    }

    fun getItemPlaylists(playlistId: String, pageToken: String?): LiveData<Resource<Playlist?>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = apiService.getItemPlaylists(
                Constants.PART,
                playlistId,
                API_KEY,
                Constants.MAX_RESULTS,
                pageToken
            )
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.body(), response.code()))
            }
        }

    fun getVideos(videosId: String): LiveData<Resource<Playlist>> =
        liveData(Dispatchers.IO) {
            val response = apiService.getVideos(
                Constants.PART,
                videosId,
                API_KEY
            )

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.body(), response.code()))
            }
        }

}