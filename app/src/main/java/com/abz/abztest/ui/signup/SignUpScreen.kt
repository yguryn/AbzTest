@file:Suppress("UNREACHABLE_CODE")

package com.abz.abztest.ui.signup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.abz.common.R
import com.abz.abztest.ui.common.CommonHeader
import com.abz.abztest.ui.common.CommonButton
import com.abz.abztest.ui.theme.Black48
import com.abz.abztest.ui.theme.Black60
import com.abz.abztest.ui.theme.Black87
import com.abz.abztest.ui.theme.BorderGray
import com.abz.abztest.ui.theme.ErrorRed
import com.abz.abztest.ui.theme.Primary
import com.abz.abztest.ui.theme.Secondary
import com.abz.abztest.ui.theme.Typography
import java.io.File

@Composable
fun SignUpScreen(
    signUpSuccess: () -> Unit,
    signUpFail: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val positions by signUpViewModel.positions.collectAsState()

    Log.d("TTT","SignUpScreen")

    val formErrors by signUpViewModel.inputErrors.collectAsState()
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val selectedPosition = remember {
        mutableStateOf<com.abz.domain.model.Position?>(null)
    }

    var showSheet by remember { mutableStateOf(false) }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {

        CommonHeader(text = stringResource(id = R.string.working_with_post_request))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)

        ) {

            Spacer(modifier = Modifier.height(24.dp))

            InputText(
                nameState = name,
                emailState = email,
                phoneState = phone,
                inputErrors = formErrors
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (positions.isEmpty()) {
                CircularProgressIndicator(color = Primary)
            } else {
                if (selectedPosition.value == null) selectedPosition.value = positions.first()
                SelectPosition(positions = positions, selectedPosition = selectedPosition) {
                    selectedPosition.value = it
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            UploadPhotoComponent(userPhoto = selectedImageUri.value?.let {
                uriToFile(
                    uri = it,
                    context = LocalContext.current
                )
            }, photoError = formErrors.photoError, onUploadPhotoClicked = {
                showSheet = true
            })
            Spacer(modifier = Modifier.height(24.dp))
            CommonButton(buttonText = stringResource(id = R.string.sign_up)) {
                val photoFile = getFileFromUri(context, selectedImageUri.value)
                signUpViewModel.onSubmit(
                    name = name.value,
                    email = email.value,
                    positionId = selectedPosition.value!!.id,
                    phone = phone.value,
                    photoFile = photoFile
                ) {
                    if (it) signUpSuccess()
                    else signUpFail()
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            if (showSheet) {
                BottomSheet(onDismiss = {
                    showSheet = false
                }, onPhotoPicked = {
                    selectedImageUri.value = it
                    Log.d("TTT", "${selectedImageUri.value}")
                    showSheet = false
                })
            }
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri?): File? {
    if (uri == null) return null
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "upload.jpg")
    file.outputStream().use {
        inputStream.copyTo(it)
    }
    return file
}


@Composable
fun InputText(
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    phoneState: MutableState<String>,
    inputErrors: com.abz.domain.model.InputErrors
) {
    var isNameError = inputErrors.nameError != null
    var isEmailError = inputErrors.emailError != null
    var isPhoneError = inputErrors.phoneError != null

    val textFiledColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        errorContainerColor = Color.White,
        focusedTextColor = Black87,
        unfocusedTextColor = Black48,
        unfocusedIndicatorColor = BorderGray,
        focusedIndicatorColor = Secondary,
        errorTextColor = ErrorRed,
        focusedLabelColor = Secondary,
        unfocusedLabelColor = Black48
    )

    OutlinedTextField(
        value = nameState.value,
        onValueChange = { nameState.value = it; isNameError = false },
        label = { Text(stringResource(id = R.string.your_name)) },
        isError = isNameError,
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        colors = textFiledColors,
        supportingText = {
            if (isNameError) Text(
                inputErrors.nameError.toString(),
                color = MaterialTheme.colorScheme.error
            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = emailState.value,
        onValueChange = { emailState.value = it; isEmailError = false },
        label = { Text(stringResource(id = R.string.email)) },
        isError = isEmailError,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = textFiledColors,
        supportingText = {
            if (isEmailError) Text(
                inputErrors.emailError!!.toString(),
                color = MaterialTheme.colorScheme.error
            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = phoneState.value,
        onValueChange = { phoneState.value = it; isPhoneError = false },
        label = { Text(stringResource(id = R.string.phone)) },
        isError = isPhoneError,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = textFiledColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        supportingText = {
            if (isPhoneError) {
                Text(
                    inputErrors.phoneError!!.toString(),
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text(stringResource(id = R.string.phone_example), color = Black60)
            }
        }
    )
}

@Composable
fun SelectPosition(
    modifier: Modifier = Modifier,
    positions: List<com.abz.domain.model.Position>,
    selectedPosition: MutableState<com.abz.domain.model.Position?>,
    onSelectPosition: (com.abz.domain.model.Position) -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        Text(
            stringResource(id = R.string.select_your_position),
            style = Typography.bodyLarge,
            color = Color.Black
        )
        positions.forEach { position ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedPosition.value == position,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Secondary,
                        unselectedColor = Secondary
                    ),
                    onClick = { onSelectPosition(position) }
                )
                Text(
                    text = position.name,
                    style = Typography.bodyMedium,
                    color = Black87,
                    modifier = modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

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
        BottomSheetContent() {
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
        return null
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
            Log.d("TTT", "${tempImageUri.value}")
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
