@file:OptIn(ExperimentalMaterial3Api::class)

package com.whereisdarran.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.whereisdarran.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var error = remember { mutableStateOf("") }
            var isError = remember { mutableStateOf(false) }
            var value = remember { mutableStateOf(TextFieldValue("")) }
            var focusRequester = remember { FocusRequester() }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column() {
                        OutlinedTextField(
                            value.value,
                            modifier = Modifier.semantics {
                                if (isError.value) liveRegion = LiveRegionMode.Polite
                            }.focusRequester(focusRequester),
                            onValueChange = {
                                            value.value = it
                            },
                            isError = isError.value,
                            supportingText = {
                                if (isError.value) Text(text = error.value) else null
                            })
                        Button({
                            isError.value = !isError.value
                            error.value = if (isError.value) "we got errorz" else ""
                        }) {
                            Text("Button")
                        }
                    }
                }


                    if (isError.value) focusRequester.requestFocus()

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}