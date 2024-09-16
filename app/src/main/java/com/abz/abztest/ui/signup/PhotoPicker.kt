package com.abz.abztest.ui.signup

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.abz.abztest.ui.theme.Black48
import com.abz.abztest.ui.theme.Typography
import com.abz.common.R
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, onPhotoPicked: (Uri) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        BottomSheetContent {
            onPhotoPicked(it)
        }
    }
}

@Composable
fun BottomSheetContent(modifier: Modifier = Modifier, onPhotoPicked: (Uri) -> Unit) {

    val context = LocalContext.current

    val tempImageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val authority = stringResource(id = R.string.fileprovider)

    fun getTempUri(): Uri? {
        val directory = File(context.cacheDir, "images")
        directory.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )
            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                onPhotoPicked(it)
            }
        })

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { _ ->
            tempImageUri.value?.let {
                onPhotoPicked(it)
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val tempUri = getTempUri()
            tempImageUri.value = tempUri
            cameraLauncher.launch(tempImageUri.value!!)

        } else {
            Toast.makeText(context, "Internet permission denied", Toast.LENGTH_SHORT)
                .show()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.choose_how_you_want_add_photo),
            color = Black48,
            style = Typography.bodyMedium
        )

        Spacer(modifier = modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.clickable {
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val tmpUri = getTempUri()
                    tempImageUri.value = tmpUri
                    cameraLauncher.launch(tempImageUri.value!!)
                } else {
                    cameraPermissionLauncher.launch(permission)
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.camera_img),
                    contentDescription = stringResource(id = R.string.camera),
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.camera),
                    color = Color.Black,
                    style = Typography.bodyMedium
                )
            }

            Column(modifier = modifier.clickable {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Image(
                    painter = painterResource(id = R.drawable.gallery_img),
                    contentDescription = stringResource(id = R.string.camera),
                    modifier = modifier.size(56.dp)
                )
                Spacer(modifier = modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.gallery),
                    color = Color.Black,
                    style = Typography.bodyMedium
                )
            }
        }
        Spacer(modifier = modifier.height(32.dp))
    }
}