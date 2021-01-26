package com.example.mediacampatdemo.ui

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar

/**
 * Custom SeekBar with  Animator
 */
class MediaSeekBar : AppCompatSeekBar {

    private var mIsTracking = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener?) {
        super.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                l?.onProgressChanged(seekBar, progress, fromUser)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                l?.onStartTrackingTouch(seekBar)
                mIsTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                l?.onStopTrackingTouch(seekBar)
                mIsTracking = false
            }
        })
    }

    // animator when change seekBar
    private var mProgressAnimator: ValueAnimator? = null


    fun startProgressAnim(start: Int, end: Int, duration: Long) {
        stopProgressAnim()
        mProgressAnimator = ValueAnimator.ofInt(start, end).setDuration(duration)
        mProgressAnimator?.interpolator = LinearInterpolator()
        mProgressAnimator?.addUpdateListener {
            onProgressUpdate(it)
        }

        mProgressAnimator?.start()
    }

    private fun stopProgressAnim() {
        mProgressAnimator?.cancel()
        mProgressAnimator = null
    }

    private fun onProgressUpdate(valueAnimator: ValueAnimator) {
        // when user is changing seekBar ,should cancel animation.
        if (mIsTracking) {
            valueAnimator.cancel()
            return
        }
        val value = valueAnimator.animatedValue as Int
        progress = value
    }
}