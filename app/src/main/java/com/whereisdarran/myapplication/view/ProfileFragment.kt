package com.whereisdarran.myapplication.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.whereisdarran.myapplication.databinding.FragmentProfileBinding
import com.whereisdarran.myapplication.databinding.ViewLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _viewBinding: FragmentProfileBinding? = null
    private var error: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewBinding = FragmentProfileBinding.inflate(inflater, container, false)
        _viewBinding = viewBinding
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_viewBinding!!) {
            saveButton.setOnClickListener {
                lifecycleScope.launch {
                    val dialog = AlertUtil.getLoadingDialog(this@ProfileFragment.requireActivity())
                    dialog.show()

                    delay(500L)

                    dialog.dismiss()


                    error = !error
                    firstNameInputLayout.showError(error)
                }
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
            MaterialAlertDialogBuilder(context).setView(view.root).setCancelable(false).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}
