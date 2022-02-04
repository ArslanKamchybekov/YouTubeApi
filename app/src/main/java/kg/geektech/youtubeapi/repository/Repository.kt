package kg.geektech.youtubeapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geektech.App.Companion.apiService
import kg.geektech.youtubeapi.BuildConfig
import kg.geektech.youtubeapi.data.model.Playlist
import kg.geektech.youtubeapi.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getPlaylists(pageToken: String?): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()

        apiService.getPlaylists(
            Constants.PART,
            Constants.CHANNEL_ID,
            BuildConfig.API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        ).enqueue(object : Callback<Playlist> {
            override fun onResponse(
                call: Call<Playlist>,
                response: Response<Playlist>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
            }
        })

        return data
    }

    fun getItemPlaylists(playlistId: String, pageToken: String?): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()

        apiService.getDetailPlaylists(
            Constants.PART,
            playlistId,
            BuildConfig.API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        ).enqueue(object : Callback<Playlist> {
            override fun onResponse(
                call: Call<Playlist>,
                response: Response<Playlist>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {
            }
        })
        return data
    }
}