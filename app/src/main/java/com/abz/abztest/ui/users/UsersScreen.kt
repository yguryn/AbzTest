package com.abz.abztest.ui.users

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.abz.common.R
import com.abz.abztest.ui.common.CommonHeader
import com.abz.abztest.ui.theme.Black48
import com.abz.abztest.ui.theme.Black60
import com.abz.abztest.ui.theme.Black87
import com.abz.abztest.ui.theme.Secondary
import com.abz.abztest.ui.theme.Typography
import com.abz.domain.model.User

@Composable
fun UsersScreen(viewModel: UsersViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()

    Log.d("TTT","UsersScreen")

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        CommonHeader(text = stringResource(id = R.string.working_with_get_request))
        if (users.isEmpty()) {
            NoUsersYet()
        } else {
            UsersList(nextUrl = viewModel.nextUrl, users = users) {
                viewModel.loadUsers()
            }
        }
    }
}

@Composable
fun UsersList(
    modifier: Modifier = Modifier,
    nextUrl: String?,
    users: List<User>,
    loadUsers: () -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        items(users) { user ->
            UserItem(user = user)
        }
        item {
            if (nextUrl != null) {
                Spacer(modifier = modifier.height(24.dp))
                CircularProgressIndicator(
                    color = Secondary,
                    modifier = modifier
                        .size(48.dp)
                )
                Spacer(modifier = modifier.height(24.dp))
                LaunchedEffect(Unit) {
                    loadUsers()
                }
            }
        }
    }
}

@Composable
fun UserItem(modifier: Modifier = Modifier, user: User) {
    Row(modifier = modifier.padding(start = 16.dp, top = 24.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photo)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.photo_description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            Text(
                text = user.name,
                style = Typography.bodyMedium,
                color = Black87,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = user.position,
                style = Typography.bodySmall,
                color = Black60,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 4.dp)
            )
            Text(
                text = user.email,
                style = Typography.bodySmall,
                color = Black87,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 8.dp)
            )
            Text(
                text = user.phone,
                style = Typography.bodySmall,
                color = Black87,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 4.dp)
            )
            Divider(color = Black48, modifier = modifier.padding(top = 24.dp))
        }
    }
}

@Composable
fun NoUsersYet(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_users_img),
                contentDescription = stringResource(id = R.string.no_internet_connection)
            )

            Text(
                text = stringResource(id = R.string.no_users_yet),
                style = Typography.headlineLarge,
                modifier = modifier.padding(top = 24.dp)
            )
        }
    }
}