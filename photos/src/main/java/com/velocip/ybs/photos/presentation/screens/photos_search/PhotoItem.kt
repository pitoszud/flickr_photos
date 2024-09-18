package com.velocip.ybs.photos.presentation.screens.photos_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation
import com.velocip.ybs.photos.R
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_CARD_TAGS_ROW
import com.velocip.ybs.core.R as CoreR


@Composable
fun PhotoItem(
    onPhotoClick: (String) -> Unit,
    onUserIconClick: (String) -> Unit,
    photoItem: PhotoItemUi,
    preview: Boolean = false
) {

    val isDarkTheme = isSystemInDarkTheme()
    val borderColor = if (isDarkTheme) Color.LightGray else colorScheme.onSurface.copy(alpha = 0.5f)

    Card(
        //modifier = Modifier.height(140.dp),
        modifier = Modifier
            .padding(8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(16.dp))
            .testTag("photo_card_${photoItem.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(8.dp)
                ) {
                    if (preview) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(id = CoreR.drawable.sample_image),
                            contentDescription = stringResource(id = R.string.search_result_photo)
                        )
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onPhotoClick(photoItem.id)
                                },
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photoItem.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(id = R.string.search_result_photo),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }

                Box(modifier = Modifier.weight(0.5f)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageModifier = Modifier
                                .clip(CircleShape)
                                .size(44.dp)
                                .clickable {
                                    onUserIconClick(photoItem.userId)
                                }
                            if (preview) {
                                Image(
                                    modifier = imageModifier,
                                    painter = painterResource(id = R.drawable.ic_account),
                                    contentDescription = stringResource(id = R.string.search_result_photo)
                                )
                            } else {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photoItem.userIcon)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = stringResource(id = R.string.photo_owner_icon),
                                    modifier = imageModifier,
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = photoItem.userId,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }
            }

            if (photoItem.tags.isNotEmpty()) {
                LazyRow(modifier = Modifier
                    .padding(16.dp)
                    .testTag("${TAG_PHOTO_CARD_TAGS_ROW}_${photoItem.id}")
                ) {
                    items(photoItem.tags) { tag ->
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PhotoItemPreview() {
    PhotoItem(
        onPhotoClick = {},
        onUserIconClick = {},
        photoItem = PhotoItemUi(
            id = "customer_id",
            userId = "happy_ybs_customer_id",
            userIcon = "https://randomuser.me/api/portraits",
            photoUrl = "https://user.photo.com",
            tags = listOf(
                "Nature",
                "Landscape",
                "Mountain",
                "River",
                "Lake",
                "Valley",
                "Dune",
                "Sunset"
            ),
            details = PhotoDetails(),
            location = PhotoLocation(),
            query = "Nature",
        ),
        preview = true,
    )
}