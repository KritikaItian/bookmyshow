package com.krutika.bookmyshow.ui.common

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.krutika.bookmyshow.utils.ext.hideSoftInput
import com.krutika.myapplication.R
import com.krutika.myapplication.databinding.SpinViewBinding
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    companion object {}


    fun showMessage(message: String) {
        hideSoftInput()
        Snackbar.make(
            findViewById(R.id.nav_host_fragment), message, Snackbar.LENGTH_SHORT
        ).show()
    }

    fun showProgress(visible: Boolean) {
        if (visible) {
            hideSoftInput()
            hideProgressBar()
            progressDialog = showLoadingDialog(this,visible)
        } else {
            hideProgressBar()
        }
    }

    fun hideProgressBar() {
        progressDialog?.cancel()
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun showLoadingDialog(
        context: Context?, hideProgressView: Boolean = false
    ): ProgressDialog? {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp: WindowManager.LayoutParams = progressDialog.window!!.attributes
            lp.dimAmount = 0.3f // Dim level. 0.0 - no dim, 1.0 - completely opaque
            progressDialog.window!!.attributes = lp;
        }
        val spinView = SpinViewBinding.inflate(LayoutInflater.from(this), null, false)
        spinView.progressBar.isVisible = hideProgressView.not()

        progressDialog.setContentView(spinView.root)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

}
