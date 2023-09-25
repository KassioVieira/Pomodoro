package com.example.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.LinearLayout.LayoutParams

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var optionsMenu: LinearLayout;
    private lateinit var homeActive: ImageButton;
    private lateinit var settingsActive: ImageButton;
    private lateinit var progressComponent: View
    private lateinit var settingsComponent: View
    private lateinit var menuHome: ImageButton
    private lateinit var menuSettings: ImageButton
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
        menuClick()
    }

    private fun settingUI() {
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)
        shortBreak = findViewById(R.id.shortbreak)
        pomodoro = findViewById(R.id.pomodoro)
        longBreak = findViewById(R.id.longbreak)
        progressComponent = findViewById(R.id.progress_component)
        settingsComponent = findViewById(R.id.settings_component)
        menuHome = findViewById(R.id.menu_home)
        menuSettings = findViewById(R.id.menu_settings)
        optionsMenu = findViewById(R.id.options)
        homeActive = findViewById(R.id.home_active)
        settingsActive = findViewById(R.id.settings_active)
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

    private fun menuClick () {

        menuHome.setOnClickListener {
            progressComponent.visibility = View.VISIBLE
            homeActive.visibility = View.VISIBLE
            optionsMenu.visibility = View.VISIBLE
            menuHome.setBackgroundResource(R.drawable.home_active)
            settingsComponent.visibility = View.GONE
            settingsActive.visibility = View.GONE
            menuSettings.setBackgroundResource(R.drawable.settings)

        }

        menuSettings.setOnClickListener {
            progressComponent.visibility = View.GONE
            homeActive.visibility = View.GONE
            optionsMenu.visibility = View.GONE
            menuHome.setBackgroundResource(R.drawable.home)
            settingsComponent.visibility = View.VISIBLE
            settingsActive.visibility = View.VISIBLE
            menuSettings.setBackgroundResource(R.drawable.settings_active)
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