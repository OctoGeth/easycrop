package com.mr0xf00.easycrop.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mr0xf00.easycrop.*
import com.mr0xf00.easycrop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropperDialog(
    modifier: Modifier = Modifier,
    state: CropState,
    style: CropperStyle = DefaultCropperStyle,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        usePlatformDefaultWidth = false
    ),
    topBar: @Composable (CropState) -> Unit = { DefaultTopBar(it) },
    cropControls: @Composable BoxScope.(CropState) -> Unit = { DefaultControls(it) }
) {
    val systemUiController: SystemUiController = rememberSystemUiController()

    BackHandler {}

    DisposableEffect(Unit) {
        systemUiController.setNavigationBarColor(Color.Black)
        onDispose {
            systemUiController.setNavigationBarColor(Color.White)
        }
    }

    CompositionLocalProvider(LocalCropperStyle provides style) {
        BasicAlertDialog(
            modifier = modifier,
            properties = dialogProperties,
            onDismissRequest = {},
        ) {
            Column {
                topBar(state)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds(),
                ) {
                    CropperPreview(state = state, modifier = Modifier.fillMaxSize())
                    cropControls(state)
                }
            }
        }
    }
}

@Composable
private fun BoxScope.DefaultControls(state: CropState) {
    val verticalControls =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    CropperControls(
        isVertical = verticalControls,
        state = state,
        modifier = Modifier
            .align(if (!verticalControls) Alignment.BottomCenter else Alignment.CenterEnd)
            .padding(16.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTopBar(state: CropState) {
    TopAppBar(title = {},
        navigationIcon = {
            IconButton(onClick = { state.done(accept = false) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        },
        actions = {
            IconButton(onClick = { state.reset() }) {
                Icon(painterResource(R.drawable.restore), null)
            }
            IconButton(onClick = { state.done(accept = true) }, enabled = !state.accepted) {
                Icon(Icons.Default.Done, null)
            }
        }
    )
}
