package kg.geektech.youtubeapi.data.remote

import kg.geektech.youtubeapi.data.model.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String? = null
    ): Response<Playlist>

    @GET("playlistItems")
    suspend fun getItemPlaylists(
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Response<Playlist>
}