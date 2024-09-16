package com.abz.abztest.ui.signup

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abz.common.R
import com.abz.abztest.ui.common.CommonHeader
import com.abz.abztest.ui.common.CommonButton
import com.abz.abztest.ui.common.PositionSaver
import com.abz.abztest.ui.theme.Black48
import com.abz.abztest.ui.theme.Black60
import com.abz.abztest.ui.theme.Black87
import com.abz.abztest.ui.theme.ErrorRed
import com.abz.abztest.ui.theme.Secondary
import com.abz.abztest.ui.theme.Typography
import com.abz.abztest.utils.getFileFromUri
import com.abz.domain.model.InputErrors
import com.abz.domain.model.Position

@Composable
fun SignUpScreen(
    signUpSuccess: () -> Unit,
    signUpFail: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val positions by signUpViewModel.positions.collectAsState()
    val formErrors by signUpViewModel.inputErrors.collectAsState()
    val name = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val phone = rememberSaveable { mutableStateOf("") }
    val selectedPosition = rememberSaveable(stateSaver = PositionSaver) {
        mutableStateOf<Position?>(null)
    }
    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val showSheet = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(positions) {
        if (positions.isNotEmpty() && selectedPosition.value == null) {
            selectedPosition.value = positions.first()
        }
    }

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
                CircularProgressIndicator(color = Secondary)
            } else {
                SelectPosition(positions = positions, selectedPosition = selectedPosition) {
                    selectedPosition.value = it
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            UploadPhotoComponent(userPhoto = selectedImageUri?.let {
                getFileFromUri(
                    uri = it,
                    context = context
                )
            }, photoError = formErrors.photoError, onUploadPhotoClicked = {
                showSheet.value = true
            })
            Spacer(modifier = Modifier.height(24.dp))
            CommonButton(buttonText = stringResource(id = R.string.sign_up)) {
                val photoFile = getFileFromUri(context, selectedImageUri)
                signUpViewModel.signUpUser(
                    name = name.value,
                    email = email.value,
                    positionId = selectedPosition.value!!.id,
                    phone = phone.value,
                    photoFile = photoFile
                ) { isSuccess ->
                    if (isSuccess) {
                        signUpSuccess()
                    } else {
                        signUpFail()
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            ShowBottomSheet(
                showSheet = showSheet,
                onPhotoPicked = { uri ->
                    selectedImageUri = uri
                }
            )
        }
    }
}

@Composable
fun ShowBottomSheet(
    showSheet: MutableState<Boolean>,
    onPhotoPicked: (Uri) -> Unit
) {
    if (showSheet.value) {
        BottomSheet(
            onDismiss = { showSheet.value = false },
            onPhotoPicked = { uri ->
                onPhotoPicked(uri)
                showSheet.value = false
            }
        )
    }
}

@Composable
fun InputText(
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    phoneState: MutableState<String>,
    inputErrors: InputErrors
) {
    var isNameError = inputErrors.nameError != null
    var isEmailError = inputErrors.emailError != null
    var isPhoneError = inputErrors.phoneError != null

    val textFiledColors = TextFieldDefaults.colors(
        unfocusedLabelColor = Black48,
        focusedTextColor = Black87,
        unfocusedTextColor = Black87,
        focusedLabelColor = Secondary,
        errorContainerColor = Color.White,
        errorLabelColor = ErrorRed,
        errorTextColor = Black87,
        errorSupportingTextColor = ErrorRed,
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        unfocusedIndicatorColor = Black48,
        focusedIndicatorColor = Secondary,
        errorIndicatorColor = ErrorRed
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
    positions: List<Position>,
    selectedPosition: MutableState<Position?>,
    onSelectPosition: (Position) -> Unit
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