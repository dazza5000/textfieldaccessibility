package com.whereisdarran.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.whereisdarran.myapplication.databinding.FragmentProfileBinding

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
                error = !error
                firstNameInputLayout.showError(error)
            }
        }
    }
}


fun TextInputLayout.showError(
    showError: Boolean = true
) {
    this.isErrorEnabled = false
    if (showError) {
        this.editText?.requestFocus()
        this.error = "this field has an error"
        this.isErrorEnabled = true
    }
}
