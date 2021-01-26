package com.example.mediacampatdemo.client

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.util.Log
import com.example.mediacampatdemo.service.MusicService

class MediaBrowserManager constructor(private val mContext: Context) {

    private val TAG = "Ocean MediaBrowserManager"
    private var mMediaBrowser: MediaBrowserCompat? = null
    private var mMediaController: MediaControllerCompat? = null

    private val mMediaBrowseConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Log.d(TAG, "MediaService is connected.")

            mMediaController = MediaControllerCompat(mContext, mMediaBrowser!!.sessionToken)
            mMediaController?.registerCallback(mMediaControllerCallback)


            mMediaControllerCallback.onMetadataChanged(mMediaController?.metadata)
            mMediaControllerCallback.onPlaybackStateChanged(mMediaController?.playbackState)


            mMediaBrowser?.subscribe(mMediaBrowser!!.root, mMediaBrowserSubscriptionCallback)


        }
    }

    private val mMediaBrowserSubscriptionCallback =
        object : MediaBrowserCompat.SubscriptionCallback() {

            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                Log.d(TAG, "onChildrenLoaded parentId = $parentId")
                super.onChildrenLoaded(parentId, children)
            }

            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>,
                options: Bundle
            ) {
                Log.d(TAG, "onChildrenLoaded parentId = $parentId, with options = $options")
                super.onChildrenLoaded(parentId, children, options)
            }

            override fun onError(parentId: String) {
                Log.d(TAG, "onError parentId = $parentId")
                super.onError(parentId)
            }

            override fun onError(parentId: String, options: Bundle) {
                super.onError(parentId, options)
            }


        }

    private val mMediaControllerCallback = object : MediaControllerCompat.Callback() {

    }

    fun onStart() {
        if (mMediaBrowser == null) {
            mMediaBrowser = MediaBrowserCompat(
                mContext,
                ComponentName(mContext, MusicService::class.java),
                mMediaBrowseConnectionCallback, null
            )
        }

        mMediaBrowser?.connect()
    }

    fun onStop() {
        mMediaBrowser?.apply {
            if (isConnected) {
                disconnect()
            }
        }
    }
}