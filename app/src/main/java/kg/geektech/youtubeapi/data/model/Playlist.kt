package kg.geektech.youtubeapi.data.model

data class Playlist(
    val etag: String? = null,
    val items: List<Item>? = null,
    val kind: String? = null,
    val nextPageToken: String? = null,
    val pageInfo: PageInfo? = null
)

data class Thumbnails(
    val default: Default? = null,
    val high: High? = null,
    val maxres: Maxres? = null,
    val medium: Medium? = null,
    val standard: Standard? = null
)

data class Standard(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

data class Snippet(
    val channelId: String? = null,
    val channelTitle: String? = null,
    val description: String? = null,
    val localized: Localized? = null,
    val publishedAt: String? = null,
    val thumbnails: Thumbnails? = null,
    val title: String? = null,
    val resourceId: ResourceId? = null
)

data class ResourceId(
    val kind: String? = null,
    val videoId: String? = null
)

data class PageInfo(
    val resultsPerPage: Int? = null,
    val totalResults: Int? = null
)

data class Medium(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

data class Maxres(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

data class Localized(
    val description: String? = null,
    val title: String? = null
)

data class Item(
    val contentDetails: ContentDetails? = null,
    val etag: String? = null,
    val id: String? = null,
    val kind: String? = null,
    val snippet: Snippet? = null,
    val status: Status? = null
)

data class Status(
    val privacyStatus: String? = null
)

data class High(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

data class Default(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

data class ContentDetails(
    val itemCount: Int? = null,
    val videoId: String? = null,
    val videoPublishedAt: String? = null
)