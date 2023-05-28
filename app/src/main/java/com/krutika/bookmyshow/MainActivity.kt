package com.krutika.bookmyshow

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.krutika.bookmyshow.ui.MainViewModel
import com.krutika.bookmyshow.ui.common.BaseActivity
import com.krutika.myapplication.R
import com.krutika.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    var mBinding: ActivityMainBinding? = null
    private val showTimeViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        supportActionBar?.hide()
    }
}