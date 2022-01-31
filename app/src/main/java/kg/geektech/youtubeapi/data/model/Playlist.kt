package kg.geektech.youtubeapi.data.model

data class Playlist(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val pageInfo: PageInfo
)