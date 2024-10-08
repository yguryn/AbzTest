package com.abz.abztest.ui.signup

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abz.common.R
import com.abz.abztest.ui.theme.Black48
import com.abz.abztest.ui.theme.ErrorRed
import com.abz.abztest.ui.theme.Secondary
import com.abz.abztest.ui.theme.Typography
import java.io.File

@Composable
fun UploadPhotoComponent(
    userPhoto: File? = null,
    photoError: String? = null,
    onUploadPhotoClicked: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .border(width = 1.dp, color = if (photoError == null) Black48 else ErrorRed, RoundedCornerShape(4.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (userPhoto == null) {
                Text(
                    stringResource(id = R.string.upload_your_photo),
                    color = if (photoError == null) Black48 else ErrorRed,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.CenterStart)

                )
            } else {
                AsyncImage(
                    model = userPhoto,
                    contentDescription = stringResource(id = R.string.uploaded_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                )
            }
            Text(
                stringResource(id = R.string.upload),
                color = Secondary,
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .clickable {
                        onUploadPhotoClicked()
                    }
            )
        }
        if (photoError != null) {
            Text(
                text = photoError,
                color = ErrorRed,
                style = Typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp, start = 16.dp)
            )
        }
    }
}