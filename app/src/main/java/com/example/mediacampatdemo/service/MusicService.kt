package com.example.mediacampatdemo.service

import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat

class MusicService : MediaBrowserServiceCompat() {


    private var mMediaSession: MediaSessionCompat? = null
    private val TAG = "Ocean MusicService"
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MusicService onCreate()")

        mMediaSession = MediaSessionCompat(this, "MusicService")
        mMediaSession?.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mMediaSession?.setCallback(mMediaSessionCallback)

        sessionToken = mMediaSession?.sessionToken


    }


    private val mMediaSessionCallback = object : MediaSessionCompat.Callback() {

    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        Log.d(TAG, "onGetRoot")
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        Log.d(TAG, "onLoadChildren")
        result.sendResult(mutableListOf())
    }


    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "onTaskRemoved")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}