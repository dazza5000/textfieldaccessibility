package com.whereisdarran.myapplication.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

const val LOADING_DIALOG_TEST_TAG = "LOADING_DIALOG"

@Composable
fun LoadingDialog(showLoadingDialog: Boolean) {
    Log.d("darran" , "loading is $showLoadingDialog")
    if (showLoadingDialog) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
            ) {
                CircularProgressIndicator(
                    modifier =
                    Modifier
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(48.dp)
                        )
                        .padding(12.dp)
                        .fillMaxSize()
                        .testTag(LOADING_DIALOG_TEST_TAG),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
