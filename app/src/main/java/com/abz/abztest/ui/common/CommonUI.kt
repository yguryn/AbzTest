package com.abz.abztest.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abz.abztest.ui.theme.Black87
import com.abz.abztest.ui.theme.Primary
import com.abz.abztest.ui.theme.Typography

@Composable
fun CommonButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .width(140.dp)
            .height(48.dp)
    ) {
        Text(text = buttonText, style = Typography.bodyLarge, color = Black87)
    }
}

@Composable
fun CommonHeader(text: String) {
    Text(
        text,
        style = Typography.headlineLarge,
        color = Black87,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(Primary)
            .padding(24.dp)
            .fillMaxWidth()
    )
}

