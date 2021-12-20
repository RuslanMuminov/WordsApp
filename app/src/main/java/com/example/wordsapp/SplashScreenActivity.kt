package com.example.wordsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.github.ybq.android.spinkit.style.DoubleBounce

import com.github.ybq.android.spinkit.sprite.Sprite

import android.R
import android.view.View
import com.example.wordsapp.databinding.ActivitySplashScreenBinding
import com.github.ybq.android.spinkit.style.FadingCircle


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = findViewById<View>(com.example.wordsapp.R.id.spinKit) as ProgressBar
        val doubleBounce: Sprite = FadingCircle()
        progressBar.indeterminateDrawable = doubleBounce

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, 3500)
    }
}