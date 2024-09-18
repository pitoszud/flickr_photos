package com.velocip.ybs.photos.presentation.screens.photos_search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velocip.ybs.photos.utils.Tags.TAG_SEARCH_ICON
import com.velocip.ybs.photos.utils.Tags.TAG_SEARCH_PHOTO_INPUT


@Composable
fun SearchPhotoInput(
    userInput: String,
    onValueChange: (String) -> Unit,
    onSearchPhotos: () -> Unit
) {

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        val scrollState = rememberScrollState(0)
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .testTag(TAG_SEARCH_PHOTO_INPUT),
            value = userInput,
            onValueChange = { typedValue ->
                onValueChange(typedValue)
            },
            maxLines = 4,
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences,
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSearchPhotos()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = colorScheme.onSurface,
                focusedTextColor = colorScheme.onSurface,
                focusedBorderColor = if (userInput.isNotEmpty()) colorScheme.primary else colorScheme.onSurface.copy(alpha = 0.5f),
                unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
            ),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.testTag(TAG_SEARCH_ICON),
            enabled = userInput.isNotEmpty(),
            onClick = { onSearchPhotos() }
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatInputPreview() {
    SearchPhotoInput(
        userInput = "Yorkshire",
        onValueChange = {},
        onSearchPhotos = {}
    )
}