package com.velocip.ybs.data.repo

import com.velocip.ybs.core.di.DefaultDispatcher
import com.velocip.ybs.core.utils.Clock
import com.velocip.ybs.data.utils.toPhotoEntity
import com.velocip.ybs.data.utils.toPhotoItemUi
import com.velocip.ybs.data.utils.toPhotoUi
import com.velocip.ybs.database.dao.PhotoDao
import com.velocip.ybs.database.entity.PhotoEntity
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.network.data_source.PhotoRemoteDataSource
import com.velocip.ybs.network.data_source.RemoteConfigDataSourceApi
import com.velocip.ybs.network.result.PhotoDetailsItem
import com.velocip.ybs.network.result.PhotoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import javax.inject.Inject


class PhotoSearchRepository @Inject constructor(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val photoDao: PhotoDao,
    private val remoteConfigDataSource: RemoteConfigDataSourceApi,
    private val clock: Clock,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : PhotoSearchRepo {

    override suspend fun searchPhotos(query: String, refresh: Boolean): Result<Unit> {
        return withContext(dispatcher) {
            try {
                if (refresh) {
                    photoDao.deleteAll()
                } else {
                    val localPhotos = photoDao.getPhotos(query).firstOrNull() ?: emptyList()
                    val isDataExpired = localPhotos.any {
                        clock.nowInstant().minus(it.timestamp) > remoteConfigDataSource.getCacheRemoteConfig().dataExpirationTime
                    }
                    if (localPhotos.isNotEmpty() && !isDataExpired) {
                        return@withContext Result.success(Unit)
                    }
                }

                val remoteResult = photoRemoteDataSource.getPhotos(query)
                remoteResult.fold(
                    onSuccess = { photos: List<PhotoItem> ->
                        if (photos.isNotEmpty()) {
                            photoDao.upsertAll(photos.map { it.toPhotoEntity(query) })
                        }
                        Result.success(Unit)
                    },
                    onFailure = { error ->
                        Result.failure(Exception("Error fetching photos", error))
                    }
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getPhotosStream(query: String): Flow<List<PhotoItemUi>> =
        photoDao.getPhotos(query).map {
            withContext(dispatcher) {
                it.map { photoEntity: PhotoEntity -> photoEntity.toPhotoUi() }
            }
        }.catch { e ->
            // TODO - log error
            emit(emptyList())
        }



    override suspend fun getPhotoDetails(photoId: String): Result<PhotoItemUi> {
        val photoEntity: PhotoEntity? = photoDao.getPhoto(photoId)
        return if (photoEntity != null && photoEntity.photoDetailsId.isNotEmpty()) {
            Result.success(photoEntity.toPhotoUi())
        } else {
            photoRemoteDataSource.getPhotoDetails(photoId).fold(
                onSuccess = { photoDetails: PhotoDetailsItem ->
                    val updatedPhotoEntity = photoEntity?.let {
                        updatePhotoEntity(it, photoDetails)
                    } ?: createPhotoEntity(photoId, photoDetails)

                    photoDao.upsertPhoto(updatedPhotoEntity)
                    Result.success(updatedPhotoEntity.toPhotoUi())
                },
                onFailure = { error ->
                    Result.failure(Exception("Error fetching photo details", error))
                }
            )
        }
    }

    override suspend fun getUserPhotos(userId: String): Result<List<PhotoItemUi>> {
        return withContext(dispatcher) {
            try {
                val remoteResult = photoRemoteDataSource.getUserPhotos(userId)
                remoteResult.fold(
                    onSuccess = { photos: List<PhotoItem> ->
                        Result.success(photos.map { it.toPhotoItemUi() })
                    },
                    onFailure = { error ->
                        Result.failure(Exception("Error fetching user photos", error))
                    }
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun createPhotoEntity(photoId: String, photoDetails: PhotoDetailsItem): PhotoEntity {
        return PhotoEntity(
            photoId = photoId,
            title = photoDetails.title,
            description = photoDetails.description,
            dateTaken = photoDetails.dateTaken,
            views = photoDetails.views,
            latitude = photoDetails.latitude,
            longitude = photoDetails.longitude,
            country = photoDetails.country,
            takenBy = photoDetails.takenBy,
            photoDetailsId = photoDetails.id,
            photoUrl = "", // fetched with getPhotos
            tags = emptyList(), // fetched with getPhotos
            userId = "", // fetched with getPhotos
            userIcon = "", // fetched with getPhotos
            userQuery = "", // fetched with getPhotos
            timestamp = Instant.DISTANT_PAST
        )
    }

    private fun updatePhotoEntity(
        photoEntity: PhotoEntity,
        photoDetails: PhotoDetailsItem
    ): PhotoEntity {
        return photoEntity.copy(
            title = photoDetails.title,
            description = photoDetails.description,
            dateTaken = photoDetails.dateTaken,
            views = photoDetails.views,
            latitude = photoDetails.latitude,
            longitude = photoDetails.longitude,
            country = photoDetails.country,
            takenBy = photoDetails.takenBy
        )
    }
}


interface PhotoSearchRepo {
    suspend fun searchPhotos(query: String, refresh: Boolean): Result<Unit>
    suspend fun getUserPhotos(userId: String): Result<List<PhotoItemUi>>
    suspend fun getPhotosStream(query: String): Flow<List<PhotoItemUi>>
    suspend fun getPhotoDetails(photoId: String): Result<PhotoItemUi>
}

