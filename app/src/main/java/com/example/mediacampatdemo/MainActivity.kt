package com.example.mediacampatdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mediacampatdemo.client.MediaBrowserManager
import com.example.mediacampatdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mMediaBrowserManager: MediaBrowserManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        mMediaBrowserManager = MediaBrowserManager(application)
    }

    override fun onStart() {
        super.onStart()
        mMediaBrowserManager?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMediaBrowserManager?.onStop()
    }
}