package com.example.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.LinearLayout.LayoutParams

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var shortBreak: TextView
    private lateinit var pomodoro: TextView
    private lateinit var longBreak: TextView
    private val totalTimeInMinutes: Long = 1
    private val millisecondsInOneSecond: Long = 1000
    private val secondsInOneMinute: Long = 60
    private var totalTimeInMillis: Long = totalTimeInMinutes * secondsInOneMinute * millisecondsInOneSecond
    private var timeLeftInMillis: Long = totalTimeInMillis
    private val countdownInterval: Long = 1000

    private val SHORT: Int = 1
    private  val POMODORO: Int = 2
    private  val LONG: Int = 2

    private var selectedItem: Int = POMODORO;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingUI()
        startCountdown()
        optionsClick()
    }

    private fun settingUI() {
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)
        shortBreak = findViewById(R.id.shortbreak)
        pomodoro = findViewById(R.id.pomodoro)
        longBreak = findViewById(R.id.longbreak)
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

    private fun optionsClick() {
        shortBreak.setOnClickListener {
            selectedItem = SHORT
            applyStyleSelectedTextView(shortBreak)
            applyStyleToTextView(pomodoro, longBreak)
        }

        pomodoro.setOnClickListener {
            selectedItem = POMODORO
            applyStyleSelectedTextView(pomodoro)
            applyStyleToTextView(shortBreak, longBreak)
        }

        longBreak.setOnClickListener{
            selectedItem = LONG
            applyStyleSelectedTextView(longBreak)
            applyStyleToTextView(shortBreak, pomodoro)
        }
    }

    private fun applyStyleToTextView(firstText: TextView, secondTextView: TextView) {
        val nonClickedTextViews = listOf(firstText, secondTextView)

        for (textView in nonClickedTextViews) {
                textView.setTextColor(getColor(R.color.textColor))
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.text_medium)
                )
            val layoutParams = textView.layoutParams as LayoutParams
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.text_medium_width)
            textView.layoutParams = layoutParams
        }
    }

    private fun applyStyleSelectedTextView(seletedTextView: TextView) {
        seletedTextView.setTextColor(getColor(R.color.primary))
        seletedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,resources.getDimension(R.dimen.text_large))
        val layoutParams = seletedTextView.layoutParams as LayoutParams
        layoutParams.width = resources.getDimensionPixelSize(R.dimen.text_large_width)
        seletedTextView.layoutParams = layoutParams
    }
}