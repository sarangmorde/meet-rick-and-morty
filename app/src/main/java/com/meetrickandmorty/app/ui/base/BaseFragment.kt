package com.meetrickandmorty.app.ui.base

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.meetrickandmorty.app.R

open class BaseFragment : Fragment() {

    protected fun showAlertMessage(title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                R.string.lbl_ok
            ) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}