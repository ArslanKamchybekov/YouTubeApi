package kg.geektech

import android.app.Application
import kg.geektech.youtubeapi.core.network.RetrofitApiClient
import kg.geektech.youtubeapi.data.remote.ApiService
import kg.geektech.youtubeapi.repository.Repository

class App : Application() {

    companion object {
        val repository: Repository by lazy {
            Repository()
        }

        val apiService: ApiService by lazy {
            RetrofitApiClient.create()
        }
    }
}