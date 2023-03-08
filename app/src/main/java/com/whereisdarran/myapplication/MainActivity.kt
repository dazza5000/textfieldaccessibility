@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.whereisdarran.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whereisdarran.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val error = remember { mutableStateOf("") }
            val isLoading = remember { mutableStateOf(false) }
            val value = remember { mutableStateOf(TextFieldValue("")) }

            MyApplicationTheme {
                Scaffold {
                    Column(modifier = Modifier.padding(it)) {
                        LoadingDialog(showLoadingDialog = isLoading.value)
                        TextField(
                            value = value.value,
                            label = {Text("Name")},
                            onValueChange = { textFieldValue ->
                                value.value = textFieldValue
                            }
                        )

                        if(error.value.isNotBlank()) {
                            Text(
                                text = error.value,
                                style = MaterialTheme.typography.caption.copy(color = Color.Red),
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .semantics(mergeDescendants = true) {
                                        liveRegion = LiveRegionMode.Polite
                                    },
                            )
                        }
                        if(value.value.text.isNotBlank()) {
                            Text(
                                text = "Only blank is allowed.  You have "+ value.value.text,
                                style = MaterialTheme.typography.caption.copy(color = Color.Red),
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .semantics(mergeDescendants = true) {
                                        liveRegion = LiveRegionMode.Polite
                                    },
                            )
                        }
                        Button({
                            isLoading.value = true
                            GlobalScope.launch {
                                delay(500L)
                                error.value = if (error.value.isBlank()) "Name is required" else ""
                                isLoading.value = false
                            }
                        }) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog(showLoadingDialog: Boolean) {
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
                        .fillMaxSize(),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
