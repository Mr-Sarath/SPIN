package com.app.spin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.app.spin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Animation.AnimationListener {
    private var isSpinning: Boolean = false
    private var binding_: ActivityMainBinding? = null
    private var count = 0
    private val prizes = intArrayOf(1, 5, 10, 0, 1, 2, 4, 10, 20, 25, 15, 6)
    private var mSpinDuration: Long = 0
    private var mSpinRevolution = 0f
    private var prizeText = "N/A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding_?.root)

        handleEvents()
    }

    private fun handleEvents() {
        binding_?.ivSpin?.setOnClickListener {
            if (!isSpinning) {
                startSpinner()
            }
        }
    }

    private fun startSpinner() {
        mSpinRevolution = 3600f
        mSpinDuration = 5000
        if (count >= 30) {
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60) {
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()
        }
        val end = Math.floor(Math.random() * 3600).toInt() // random : 0-360
        val numOfPrizes = prizes.size // quantity of prize
        val shift = 0 // shit where the arrow points
        val prizeIndex = (shift + end) % numOfPrizes

        prizeText = "Prize is : ${prizes[prizeIndex]}"
        val rotateAnim = RotateAnimation(
            0f, mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        binding_?.ivImageCircle!!.startAnimation(rotateAnim)

    }

    override fun onAnimationStart(p0: Animation?) {
        isSpinning = true
        binding_?.tvSpin?.text = "Spinning..."
    }
    override fun onAnimationEnd(p0: Animation?) {
        binding_?.tvSpin?.text = prizeText
        isSpinning = false
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }
}