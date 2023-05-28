package com.krutika.bookmyshow.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.krutika.bookmyshow.utils.ext.AppError
import com.krutika.bookmyshow.utils.ext.stringRes
import com.krutika.bookmyshow.utils.ext.toApiError
import com.krutika.bookmyshow.utils.retrofit.SingleLiveObserver
import com.krutika.myapplication.R


abstract class BaseFragment<M : BaseViewModel> : Fragment() {

    abstract fun getViewModel(): M

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCommonObserver()
    }

    private fun setupCommonObserver() {
        getViewModel().errorLiveData.observe(
            viewLifecycleOwner,
            SingleLiveObserver { appError ->
                when (appError) {
                    is AppError.ApiException.BadRequestException -> {
                        val apiError = appError.toApiError()
                        showMessage(apiError.errorMessage() ?: getString(R.string.error_unknown))
                    }

                    else -> {
                        showMessage(getString(appError.stringRes()))
                    }
                }
            }
        )

        getViewModel().messageLiveData.observe(
            viewLifecycleOwner,
            SingleLiveObserver { message ->
                showMessage(message)
            }
        )

        getViewModel().loadingLiveData.observe(
            viewLifecycleOwner,
            SingleLiveObserver { visible ->
                showProgress(visible)
            }
        )
    }

    interface DateListener {
        fun onDateSet(date: String)
    }


    fun showMessage(message: String) {
        getBaseActivity().showMessage(message)
    }

    private fun getBaseActivity(): BaseActivity {
        if (requireActivity() is BaseActivity) {
            return requireActivity() as BaseActivity
        } else {
            throw IllegalArgumentException("Your parent activity is not base activity, Please extend Base Activity.")
        }
    }

    fun onError(appError: AppError) {
        getViewModel().onError(appError)
    }

    private fun showProgress(visible: Boolean) {
        getBaseActivity().showProgress(visible)
    }

    fun hideProgressBar() {
        getBaseActivity().hideProgressBar()
    }

    fun showToast(message: String?) {
        getBaseActivity().showToast(message)
    }
}