package com.abz.abztest.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abz.common.R
import com.abz.abztest.ui.common.CommonButton
import com.abz.abztest.ui.theme.Black87
import com.abz.abztest.ui.theme.Typography

@Composable
fun NoInternetScreen() {

    Log.d("TTT","NoInternetScreen")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_internet_img),
                contentDescription = stringResource(id = R.string.no_internet_connection)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.there_is_no_internet_connection),
                color = Black87,
                style = Typography.headlineLarge,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CommonButton(buttonText = stringResource(id = R.string.try_again)) {
            }
        }
    }
}
