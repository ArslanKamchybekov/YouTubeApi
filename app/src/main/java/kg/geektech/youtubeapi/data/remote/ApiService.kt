package kg.geektech.youtubeapi.data.remote

import kg.geektech.youtubeapi.data.model.Playlist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String? = null
    ): Call<Playlist>

    @GET("playlistItems")
    fun getDetailPlaylists(
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Call<Playlist>

}