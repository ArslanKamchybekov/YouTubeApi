package kg.geektech.youtubeapi.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geektech.youtubeapi.base.BaseViewModel
import kg.geektech.youtubeapi.data.model.Playlist
import kg.geektech.youtubeapi.data.remote.ApiService
import kg.geektech.youtubeapi.data.remote.RetrofitClient
import kg.geektech.youtubeapi.utilities.Constants
import retrofit2.Call
import retrofit2.Response

class MainViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun playlist(pageToken: String?): LiveData<Playlist> {
        return getPlaylists(pageToken)
    }

    private fun getPlaylists(pageToken: String?): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()

        apiService.getPlaylists(
            Constants.PART,
            Constants.CHANNEL_ID,
            kg.geektech.youtubeapi.BuildConfig.API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        )
            .enqueue(object : retrofit2.Callback<Playlist> {
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