package com.whereisdarran.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.whereisdarran.myapplication.databinding.ViewLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    private var error: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            lifecycleScope.launch {
                val dialog = AlertUtil.getLoadingDialog(this@MainActivity)
                dialog.show()

                delay(1500L)

                dialog.dismiss()


                error = !error


                findViewById<TextInputLayout>(R.id.firstNameInputLayout).showError(error)
            }
        }
    }
}

fun TextInputLayout.showError(
    showError: Boolean = true
) {
    if (showError) {
        this.error = "this field has an error"
    } else {
        this.error = null
    }
}

object AlertUtil {
    fun getLoadingDialog(context: Context): androidx.appcompat.app.AlertDialog {
        val view = ViewLoadingBinding.inflate(LayoutInflater.from(context))
        val dialog =
            MaterialAlertDialogBuilder(context).setView(view.root)
                .setOnDismissListener {
                    Log.e("darrantalkback","talkback dialog dismissed")
                }
                .setCancelable(false)
                .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}

