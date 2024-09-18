package com.velocip.ybs.data

import com.google.common.truth.Truth.assertThat
import com.velocip.ybs.core.fake.FakeClock
import com.velocip.ybs.data.repo.PhotoSearchRepository
import com.velocip.ybs.database.entity.PhotoEntity
import com.velocip.ybs.database.fakes.FakePhotoDao
import com.velocip.ybs.network.fakes.FakeRemoteConfigDataSource
import com.velocip.ybs.network.fakes.FakeRemoteDataSource
import com.velocip.ybs.network.result.PhotoDetailsItem
import com.velocip.ybs.network.result.PhotoItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.days

private val timestamp = Instant.parse("2024-09-18T11:17:29.418262Z")

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoRepositoryTest {


    // Class under test
    private lateinit var photoRepository: PhotoSearchRepository

    // Test dependencies
    private lateinit var photoRemoteDataSource: FakeRemoteDataSource
    private lateinit var photoDao: FakePhotoDao
    private lateinit var remoteConfigDataSource: FakeRemoteConfigDataSource
    private lateinit var clock: FakeClock

    // scope
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)


    @Before
    fun setup() {
        photoRemoteDataSource = FakeRemoteDataSource(defaultPhotoItems.toMutableList())
        photoDao = FakePhotoDao()
        remoteConfigDataSource = FakeRemoteConfigDataSource()
        clock = FakeClock(timestamp)

        photoRepository = PhotoSearchRepository(
            photoRemoteDataSource = photoRemoteDataSource,
            photoDao = photoDao,
            remoteConfigDataSource = remoteConfigDataSource,
            clock = clock,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `first default search should delete all existing photos and fetch new photos and update db`() = testScope.runTest {
        photoDao.upsertAll(photoEntities)
        photoRepository.searchPhotos("London", true)
        val photos = photoRepository.getPhotosStream("London").first()
        assertThat(photos.size).isEqualTo(4)
        assertThat(photos[0].id).isEqualTo(defaultPhotoItems[0].photoId)
        assertThat(photos[1].id).isEqualTo(defaultPhotoItems[1].photoId)
        assertThat(photos[2].id).isEqualTo(defaultPhotoItems[2].photoId)
        assertThat(photos[3].id).isEqualTo(defaultPhotoItems[3].photoId)

    }


    @Test
    fun `second default search should get photos from db`() = testScope.runTest {
        photoDao.upsertAll(photoEntities)
        photoRepository.searchPhotos("London", false)
        val photos = photoRepository.getPhotosStream("London").first()
        assertThat(photos.size).isEqualTo(3)
        assertThat(photos[0].id).isEqualTo(photoEntities[0].photoId)
        assertThat(photos[1].id).isEqualTo(photoEntities[1].photoId)
        assertThat(photos[2].id).isEqualTo(photoEntities[2].photoId)
    }

    @Test
    fun `second default search with outdated timestamp should fetch photos from the remote data source`() = testScope.runTest {
        photoDao.upsertAll(photoEntities)
        clock.advanceTimeBy(2.days)
        photoRepository.searchPhotos("London", false)
        val photos = photoRepository.getPhotosStream("London").first()

        assertThat(photos.size).isEqualTo(7)
        assertThat(photos[0].id).isEqualTo(photoEntities[0].photoId)
        assertThat(photos[1].id).isEqualTo(photoEntities[1].photoId)
        assertThat(photos[2].id).isEqualTo(photoEntities[2].photoId)
        assertThat(photos[3].id).isEqualTo(defaultPhotoItems[0].photoId)
        assertThat(photos[4].id).isEqualTo(defaultPhotoItems[1].photoId)
        assertThat(photos[5].id).isEqualTo(defaultPhotoItems[2].photoId)
        assertThat(photos[6].id).isEqualTo(defaultPhotoItems[3].photoId)

    }


    @Test
    fun `second search with outdated timestamp should fetch photos from the remote data source`() = testScope.runTest {
        photoDao.upsertAll(photoEntities)
        clock.advanceTimeBy(2.days)
        photoRepository.searchPhotos("London", true)
        val photos = photoRepository.getPhotosStream("London").first()

        assertThat(photos.size).isEqualTo(4)
        assertThat(photos[0].id).isEqualTo(defaultPhotoItems[0].photoId)
        assertThat(photos[1].id).isEqualTo(defaultPhotoItems[1].photoId)
        assertThat(photos[2].id).isEqualTo(defaultPhotoItems[2].photoId)
        assertThat(photos[3].id).isEqualTo(defaultPhotoItems[3].photoId)

    }


    @Test
    fun `new search should delete all existing photos and fetch new photos and update db`() = testScope.runTest {
        photoDao.upsertAll(photoEntities)
        photoRepository.searchPhotos("query", true)
        val photos = photoRepository.getPhotosStream("query").first()
        assertThat(photos.size).isEqualTo(4)
        assertThat(photos[0].id).isEqualTo(defaultPhotoItems[0].photoId)
        assertThat(photos[1].id).isEqualTo(defaultPhotoItems[1].photoId)
        assertThat(photos[2].id).isEqualTo(defaultPhotoItems[2].photoId)
        assertThat(photos[3].id).isEqualTo(defaultPhotoItems[3].photoId)

        photoRemoteDataSource.photos = photoItems.toMutableList()
        photoRepository.searchPhotos("query2", true)

        val photos2 = photoRepository.getPhotosStream("query2").first()
        assertThat(photos2.size).isEqualTo(3)
        assertThat(photos2[0].id).isEqualTo(photoItems[0].photoId)
        assertThat(photos2[1].id).isEqualTo(photoItems[1].photoId)
        assertThat(photos2[2].id).isEqualTo(photoItems[2].photoId)

    }


    @Test
    fun `network error should return failure result`() = testScope.runTest {
        photoRemoteDataSource.setShouldThrowError(true)
        photoDao.upsertAll(photoEntities)
        val result = photoRepository.searchPhotos("query", true)
        assertThat(result.isFailure).isTrue()
        val photos = photoRepository.getPhotosStream("query").first()
        assertThat(photos.size).isEqualTo(0)
    }

    @Test
 fun `get Photo Details should return photo details from remote source if not in local db`() = testScope.runTest {
    val photoId = "dto_item_1"
    val photoDetailsItem = PhotoDetailsItem(
        id = photoId,
        title = "Sample Title",
        description = "Sample Description",
        dateTaken = "18 Sep 2024",
        views = 100,
        latitude = 51.5074,
        longitude = -0.1278,
        country = "UK",
        takenBy = "Photographer"
    )
    photoRemoteDataSource.setUpPhotoDetailsItem(photoId, photoDetailsItem)

    val result = photoRepository.getPhotoDetails(photoId)

    assertThat(result.isSuccess).isTrue()
    val photoItemUi = result.getOrNull()
    assertThat(photoItemUi).isNotNull()
    assertThat(photoItemUi?.id).isEqualTo(photoId)
    assertThat(photoItemUi?.details?.title).isEqualTo(photoDetailsItem.title)
}

    @Test
    fun `get Photo Details second time return photo details from local db`() = testScope.runTest {
        val photoId = "dto_item_1"
        val photoDetailsItem = PhotoDetailsItem(
            id = photoId,
            title = "Sample Title",
            description = "Sample Description",
            dateTaken = "18 Sep 2024",
            views = 100,
            latitude = 51.5074,
            longitude = -0.1278,
            country = "UK",
            takenBy = "Photographer"
        )
        photoRemoteDataSource.setUpPhotoDetailsItem(photoId, photoDetailsItem)

        // first time get photo details
        photoRepository.getPhotoDetails(photoId)

        // remote data has changed
        val photoDetailsItem2 = PhotoDetailsItem(
            id = photoId,
            title = "Sample Title 2",
            description = "Sample Description",
            dateTaken = "18 Sep 2024",
            views = 100,
            latitude = 51.5074,
            longitude = -0.1278,
            country = "UK",
            takenBy = "Photographer"
        )
        photoRemoteDataSource.setUpPhotoDetailsItem(photoId, photoDetailsItem2)

        // second time get photo details
        val result = photoRepository.getPhotoDetails(photoId)

        assertThat(result.isSuccess).isTrue()
        val photoItemUi = result.getOrNull()
        assertThat(photoItemUi).isNotNull()
        assertThat(photoItemUi?.id).isEqualTo(photoId)
        assertThat(photoItemUi?.details?.title).isEqualTo(photoDetailsItem.title)
    }


}


val defaultPhotoItems = listOf(
    PhotoItem(
        photoId = "dto_item_1",
        userId = "1",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
    PhotoItem(
        photoId = "dto_item_2",
        userId = "1",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
    PhotoItem(
        photoId = "dto_item_3",
        userId = "2",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
    PhotoItem(
        photoId = "dto_item_4",
        userId = "2",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
)

val photoItems = listOf(
    PhotoItem(
        photoId = "dto_item_5",
        userId = "3",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
    PhotoItem(
        photoId = "dto_item_6",
        userId = "3",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
    PhotoItem(
        photoId = "dto_item_7",
        userId = "4",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        timestamp = timestamp
    ),
)

val photoEntities = listOf(
    PhotoEntity(
        photoId = "entity_item_1",
        photoDetailsId = "1",
        userId = "1",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        userQuery = "London",
        title = "title",
        description = "description",
        dateTaken = "18 Sep 2024",
        views = 0,
        latitude = null,
        longitude = null,
        country = "",
        takenBy = "",
        timestamp = timestamp
    ),
    PhotoEntity(
        photoId = "entity_item_2",
        photoDetailsId = "2",
        userId = "1",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        userQuery = "London",
        title = "title",
        description = "description",
        dateTaken = "18 Sep 2024",
        views = 0,
        latitude = null,
        longitude = null,
        country = "",
        takenBy = "",
        timestamp = timestamp
    ),
    PhotoEntity(
        photoId = "entity_item_3",
        photoDetailsId = "3",
        userId = "2",
        userIcon = "icon",
        photoUrl = "url",
        tags = listOf("tag1", "tag2"),
        userQuery = "London",
        title = "title",
        description = "description",
        dateTaken = "18 Sep 2024",
        views = 0,
        latitude = null,
        longitude = null,
        country = "",
        takenBy = "",
        timestamp = timestamp
    ),

)