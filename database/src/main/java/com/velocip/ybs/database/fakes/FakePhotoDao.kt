package com.velocip.ybs.database.fakes

import com.velocip.ybs.database.dao.PhotoDao
import com.velocip.ybs.database.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePhotoDao() : PhotoDao {

    private val photos = mutableListOf<PhotoEntity>()


    override fun getPhotos(usrQuery: String): Flow<List<PhotoEntity>> = flow {
        val filteredPhotos = photos.filter { it.userQuery == usrQuery }
        emit(filteredPhotos)
    }

    override suspend fun deleteAll() {
        photos.clear()
    }

    override suspend fun upsertAll(photos: List<PhotoEntity>) {
        this.photos.addAll(photos)
    }

    override suspend fun getPhoto(photoId: String): PhotoEntity? {
        return photos.find { it.photoId == photoId }
    }

    override suspend fun upsertPhoto(photoEntity: PhotoEntity) {
        photos.add(photoEntity)
    }
}