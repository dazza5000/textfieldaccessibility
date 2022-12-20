@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)

package com.whereisdarran.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereisdarran.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val error = remember { mutableStateOf("") }
            val isError = remember { mutableStateOf(false) }
            val value = remember { mutableStateOf(TextFieldValue("")) }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        Column(

                        ) {
                            TextFieldSample(
                                modifier = Modifier.semantics(mergeDescendants = true) {
                                    if (isError.value) liveRegion = LiveRegionMode.Polite
                                },
                                value = value.value,
                                onValueChange = {
                                                value.value = it
                                },
                                errorMessage = error.value,
                                label = "text field test"
                            )
                            TextFieldSample(
                                value = value.value,
                                onValueChange = {
                                    value.value = it
                                },
                                errorMessage = error.value,
                                label = "text field test two"
                            )
                            TextFieldSample(
                                value = value.value,
                                onValueChange = {
                                    value.value = it
                                },
                                errorMessage = error.value,
                                label = "text field test three"
                            )
                        }
                        Button({
                            isError.value = !isError.value
                            error.value = if (isError.value) "please complete this field" else ""
                        }) {
                            Text("Button")
                        }
                    }
                }
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

@Composable
fun TextFieldSample(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    lines: Int = 1,
    isOutlinedTextField: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    autofillTypes: List<AutofillType> = listOf(),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    hideLabel: Boolean = false
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    // https://issuetracker.google.com/issues/251162419
    val contentDesc = if (hideLabel || placeholder != null) { label } else null
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .wrapContentHeight()
    ) {
        BasicTextField(
            value = value,
            onValueChange = { value ->

//              https://issuetracker.google.com/issues/202353328
//              https://issuetracker.google.com/issues/225399256
                if (keyboardOptions.keyboardType == KeyboardType.Number) {
                    if (!value.text.contains('.')) onValueChange.invoke(value)
                } else {
                    onValueChange.invoke(value)
                }
            },
            modifier = modifier
                .background(colors.backgroundColor(enabled).value, MaterialTheme.shapes.small)
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor(errorMessage != null).value),
            keyboardOptions = keyboardOptions,
            // https://issuetracker.google.com/issues/197121898
            keyboardActions = KeyboardActions(
                onDone = {
                    softwareKeyboardController?.hide()
                }
            ),
            interactionSource = interactionSource,
            singleLine = lines == 1,
            maxLines = lines,
            decorationBox = @Composable { innerTextField ->
                DecorationBox(
                    isOutlinedTextField,
                    value,
                    innerTextField,
                    placeholder,
                    if (hideLabel) null else { { Text(text = label) } },
                    trailingIcon,
                    lines,
                    enabled,
                    errorMessage,
                    interactionSource,
                    colors
                )
            }
        )

        errorMessage?.run {
            Text(
                text = this,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

fun Modifier.modifyIf(condition: Boolean, modify: Modifier.() -> Modifier) =
    if (condition) modify() else this

fun <T> Modifier.modifyIfNotNull(modifier: T?, modify: Modifier.(T) -> Modifier): Modifier =
    modifier?.run {
        modify(this)
    } ?: this


@Composable
private fun DecorationBox(
    isOutlinedTextField: Boolean,
    value: TextFieldValue,
    innerTextField: @Composable () -> Unit,
    placeholder: @Composable() (() -> Unit)?,
    label: @Composable() (() -> Unit)?,
    trailingIcon: @Composable() (() -> Unit)?,
    lines: Int,
    enabled: Boolean,
    errorMessage: String?,
    interactionSource: MutableInteractionSource,
    colors: TextFieldColors
) {
    val noLabelPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
        top = 0.dp,
        bottom = 0.dp
    )
    if (isOutlinedTextField) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value.text,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            placeholder = placeholder,
            label = label,
            trailingIcon = trailingIcon,
            singleLine = lines == 1,
            enabled = enabled,
            isError = errorMessage != null,
            interactionSource = interactionSource,
            colors = colors,
            contentPadding = if (label == null) {
                noLabelPadding
            } else {
                TextFieldDefaults.outlinedTextFieldPadding()
            },
            border = {
                TextFieldDefaults.BorderBox(
                    enabled,
                    errorMessage != null,
                    interactionSource,
                    colors,
                    MaterialTheme.shapes.small
                )
            }
        )
    } else {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value.text,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            placeholder = placeholder,
            label = label,
            trailingIcon = trailingIcon,
            singleLine = lines == 1,
            enabled = enabled,
            isError = errorMessage != null,
            interactionSource = interactionSource,
            colors = colors,
            contentPadding = if (label == null) {
                noLabelPadding
            } else {
                TextFieldDefaults.textFieldWithLabelPadding()
            }
        )
    }
}
