package com.velocip.ybs.network

import com.google.common.truth.Truth.assertThat
import com.velocip.ybs.network.dto.photo_details.CountryDto
import com.velocip.ybs.network.dto.photo_details.DatesDto
import com.velocip.ybs.network.dto.photo_details.DescriptionDto
import com.velocip.ybs.network.dto.photo_details.FlickrPhotoDetailsDto
import com.velocip.ybs.network.dto.photo_details.LocationDto
import com.velocip.ybs.network.dto.photo_details.OwnerDto
import com.velocip.ybs.network.dto.photo_details.PhotoDetailsDto
import com.velocip.ybs.network.dto.photo_details.TitleDto
import com.velocip.ybs.network.dto.photos.PhotoDto
import com.velocip.ybs.network.result.PhotoItem
import com.velocip.ybs.network.utils.PhotoItemBuilder
import com.velocip.ybs.network.utils.PhotoItemBuilderImpl
import org.junit.Before
import org.junit.Test

class PhotoDetailDtoItemBuilderTest {

    private lateinit var photoItemBuilder: PhotoItemBuilder

    private val id = "photoId001"
    private val owner = "ownerId001"
    private val photoUrl = "https://farm1.staticflickr.com/server001/photoId001_secret001_m.jpg"
    private val userIcon = "https://farm1.staticflickr.com/server001/buddyicons/ownerId001.jpg"


    @Before
    fun setUp() {
        photoItemBuilder = PhotoItemBuilderImpl()
    }

    @Test
    fun `build photo item without tags`() {
        val photoDto = PhotoDto(
            id = id,
            owner = owner,
            secret = "secret001",
            server = "server001",
            farm = 1,
            title = "title001",
            isPublic = 1,
            isFriend = 0,
            isFamily = 0,
            tags = ""
        )
        val photoItem: PhotoItem = photoItemBuilder.buildPhotoItem(photoDto)

        assertThat(photoItem.photoId).isEqualTo(id)
        assertThat(photoItem.userId).isEqualTo(owner)
        assertThat(photoItem.userIcon).isEqualTo(userIcon)
        assertThat(photoItem.photoUrl).isEqualTo(photoUrl)
        assertThat(photoItem.tags).isEmpty()
    }


    @Test
    fun `build photo item with one tag`() {
        val photoDto = PhotoDto(
            id = id,
            owner = owner,
            secret = "secret001",
            server = "server001",
            farm = 1,
            title = "title001",
            isPublic = 1,
            isFriend = 0,
            isFamily = 0,
            tags = "tag1"
        )
        val photoItem: PhotoItem = photoItemBuilder.buildPhotoItem(photoDto)

        assertThat(photoItem.photoId).isEqualTo(id)
        assertThat(photoItem.userId).isEqualTo(owner)
        assertThat(photoItem.userIcon).isEqualTo(userIcon)
        assertThat(photoItem.photoUrl).isEqualTo(photoUrl)
        assertThat(photoItem.tags.size).isEqualTo(1)
        assertThat(photoItem.tags[0]).isEqualTo("tag1")
    }


    @Test
    fun `build photo item with multiple tags`() {
        val photoDto = PhotoDto(
            id = id,
            owner = owner,
            secret = "secret001",
            server = "server001",
            farm = 1,
            title = "title001",
            isPublic = 1,
            isFriend = 0,
            isFamily = 0,
            tags = "tag1 tag2 tag3"
        )
        val photoItem: PhotoItem = photoItemBuilder.buildPhotoItem(photoDto)

        assertThat(photoItem.photoId).isEqualTo(id)
        assertThat(photoItem.userId).isEqualTo(owner)
        assertThat(photoItem.userIcon).isEqualTo(userIcon)
        assertThat(photoItem.photoUrl).isEqualTo(photoUrl)
        assertThat(photoItem.tags.size).isEqualTo(3)
        assertThat(photoItem.tags[0]).isEqualTo("tag1")
        assertThat(photoItem.tags[1]).isEqualTo("tag2")
        assertThat(photoItem.tags[2]).isEqualTo("tag3")
    }

    @Test
    fun `build photo item details with invalid properties`() {
        val userName = "** John Doe **"
        val photoId = "photoId001"

        val photoDetailsDto = FlickrPhotoDetailsDto(
            photo = PhotoDetailsDto(
                id = photoId,
                views = "one hundred",
                location = LocationDto(
                    latitude = "latitude",
                    longitude = "longitude"
                ),
                owner = OwnerDto(
                    username = userName,
                )
            )
        )
        val photoDetailsItem = photoItemBuilder.buildPhotoItemDetails(photoDetailsDto)

        assertThat(photoDetailsItem.id).isEqualTo(photoId)
        assertThat(photoDetailsItem.views).isEqualTo(0)
        assertThat(photoDetailsItem.latitude).isNull()
        assertThat(photoDetailsItem.longitude).isNull()
        assertThat(photoDetailsItem.country).isEmpty()
        assertThat(photoDetailsItem.takenBy).isEqualTo(userName)
    }


    @Test
    fun `build photo item details with valid properties`() {
        val photoId = "photoId001"
        val dateTaken = "2021-08-01 12:00:00"
        val description = "Detailed Photo description"
        val title = "Photo title"
        val usrName = "** John Doe **"

        val photoDetailsDto = FlickrPhotoDetailsDto(
            photo = PhotoDetailsDto(
                id = photoId,
                dates = DatesDto(
                    taken = dateTaken,
                ),
                views = "100",
                description = DescriptionDto(
                    content = description
                ),
                title = TitleDto(
                    content = title
                ),
                location = LocationDto(
                    latitude = "53.837198",
                    longitude = "-1.780534",
                    country = CountryDto(
                        content = "UK"
                    )
                ),
                owner = OwnerDto(
                    username = usrName,
                )
            )
        )

        val photoDetailsItem = photoItemBuilder.buildPhotoItemDetails(photoDetailsDto)

        assertThat(photoDetailsItem.id).isEqualTo(photoId)
        assertThat(photoDetailsItem.title).isEqualTo(title)
        assertThat(photoDetailsItem.description).isEqualTo(description)
        assertThat(photoDetailsItem.dateTaken).isEqualTo(dateTaken)
        assertThat(photoDetailsItem.views).isEqualTo(100)
        assertThat(photoDetailsItem.latitude).isEqualTo(53.837198)
        assertThat(photoDetailsItem.longitude).isEqualTo(-1.780534)
        assertThat(photoDetailsItem.country).isEqualTo("UK")
        assertThat(photoDetailsItem.takenBy).isEqualTo(usrName)
    }
}