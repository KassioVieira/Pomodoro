package com.example.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private val totalTimeInMinutes: Long = 1
    private val millisecondsInOneSecond: Long = 1000
    private val secondsInOneMinute: Long = 60
    private var totalTimeInMillis: Long = totalTimeInMinutes * secondsInOneMinute * millisecondsInOneSecond
    private var timeLeftInMillis: Long = totalTimeInMillis
    private val countdownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingUI()
        startCountdown()
    }

    private fun settingUI() {
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)
    }

    private fun startCountdown() {
        val countDownTimer = object : CountDownTimer(totalTimeInMillis, countdownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateUI()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateUI()
            }
        }

        countDownTimer.start()
    }

    fun updateUI() {
        val minutes = (timeLeftInMillis / millisecondsInOneSecond / secondsInOneMinute).toInt()
        val seconds = (timeLeftInMillis / millisecondsInOneSecond % secondsInOneMinute).toInt()
        val timeFormatted = String.format("%02d:%02d", minutes, seconds)

        progressText.text = timeFormatted

        val progress = ((timeLeftInMillis.toDouble() / totalTimeInMillis.toDouble()) * 100).toInt()
        progressBar.progress = progress
    }
}