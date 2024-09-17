package com.velocip.ybs.network.data_source

import com.velocip.ybs.network.BuildConfig
import com.velocip.ybs.network.dto.photo_details.FlickrPhotoDetailsDto
import com.velocip.ybs.network.dto.photos.FlickrPhotosDto
import com.velocip.ybs.network.result.PhotoDetailsItem
import com.velocip.ybs.network.result.PhotoItem
import com.velocip.ybs.network.utils.ApiMethod
import com.velocip.ybs.network.utils.PhotoItemBuilder
import com.velocip.ybs.network.utils.UrlConfigInterface
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PhotoDefaultRemoteDataSource @Inject constructor(
    private val client: HttpClient,
    private val urlConfig: UrlConfigInterface,
    private val photoItemBuilder: PhotoItemBuilder
) : PhotoRemoteDataSource {

    override suspend fun getPhotos(query: String): Result<List<PhotoItem>> {
        return try {
            val photoResponseJson: String = client.get("${urlConfig.getBaseUrl()}?method=${urlConfig.getMethod(ApiMethod.PHOTOS_SEARCH)}&api_key=${BuildConfig.FLICKR_API_KEY_ID}&format=json&nojsoncallback=1&text=$query&safe_search=1&per_page=30&page=1&extras=tags").body()
            if (photoResponseJson.isEmpty()) {
                Result.failure(Exception("Empty Photo response"))
            } else {
                val format = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                val photoItem = format.decodeFromString<FlickrPhotosDto>(photoResponseJson)
                val allPhotos = photoItem.photos.photos.map { photo ->
                    photoItemBuilder.buildPhotoItem(photo)
                }
                Result.success(allPhotos)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPhotoDetails(photoId: String): Result<PhotoDetailsItem> {
        return try {
            val photoResponseJson: String = client.get("${urlConfig.getBaseUrl()}?method=${urlConfig.getMethod(ApiMethod.PHOTOS_INFO)}&api_key=${BuildConfig.FLICKR_API_KEY_ID}&format=json&nojsoncallback=1&photo_id=${photoId}").body()
            if (photoResponseJson.isEmpty()) {
                Result.failure(Exception("Empty Photo response"))
            } else {
                val format = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                val photoDetailsItem: FlickrPhotoDetailsDto = format.decodeFromString<FlickrPhotoDetailsDto>(photoResponseJson)
                val item = photoItemBuilder.buildPhotoItemDetails(photoDetailsItem)
                Result.success(item)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserPhotos(userId: String): Result<List<PhotoItem>> {
        return try {
            val photoResponseJson: String = client.get("${urlConfig.getBaseUrl()}?method=${urlConfig.getMethod(ApiMethod.PEOPLE_PHOTOS)}&api_key=${BuildConfig.FLICKR_API_KEY_ID}&format=json&nojsoncallback=1&user_id=${userId}").body()
            if (photoResponseJson.isEmpty()) {
                Result.failure(Exception("Empty Photo response"))
            } else {
                val format = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                val photoItem = format.decodeFromString<FlickrPhotosDto>(photoResponseJson)
                val allPhotos = photoItem.photos.photos.map { photo ->
                    photoItemBuilder.buildPhotoItem(photo)
                }
                Result.success(allPhotos)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


interface PhotoRemoteDataSource {
    suspend fun getPhotos(query: String): Result<List<PhotoItem>>
    suspend fun getUserPhotos(userId: String): Result<List<PhotoItem>>
    suspend fun getPhotoDetails(photoId: String): Result<PhotoDetailsItem>
}