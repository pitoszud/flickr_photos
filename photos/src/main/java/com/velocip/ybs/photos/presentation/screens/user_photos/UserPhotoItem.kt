package com.velocip.ybs.photos.presentation.screens.user_photos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.velocip.ybs.core.R
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation
import com.velocip.ybs.photos.utils.Tags.TAG_USER_PHOTO


@Composable
fun UserPhotoItem(
    photoItem: PhotoItemUi,
    preview: Boolean = false
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp))
            .testTag("${TAG_USER_PHOTO}_${photoItem.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (preview) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = stringResource(id = com.velocip.ybs.photos.R.string.search_result_photo)
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoItem.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = com.velocip.ybs.photos.R.string.search_result_photo),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
@Preview
fun UserPhotoItemPreview() {
    UserPhotoItem(
        photoItem = PhotoItemUi(
            id = "customer_id",
            userId = "happy_ybs_customer_id",
            userIcon = "https://randomuser.me/api/portraits",
            photoUrl = "https://user.photo.com",
            tags = listOf("tag1", "tag2"),
            details = PhotoDetails(),
            location = PhotoLocation(),
            query = "Nature",
        ),
        preview = true
    )
}