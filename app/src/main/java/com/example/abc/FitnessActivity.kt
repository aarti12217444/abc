package com.example.abc

import android.annotation.SuppressLint
import android.provider.AlarmClock
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random
import androidx.activity.result.contract.ActivityResultContracts

class FitnessActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var nextButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tipTextView: TextView
    private lateinit var alarmButton: Button

    private lateinit var dancingButton: Button
    private lateinit var singingButton: Button
    private lateinit var paintingButton: Button
    private lateinit var travelButton: Button
    private lateinit var bhajanButton: Button
    private lateinit var templeButton: Button
    private lateinit var familyButton: Button

    private val questions = listOf(
        Pair(
            "In the past week, how often have you felt very anxious?" to listOf("😊 Never", "🥺 Sometimes", "☁️ Often", "😐 Very often"),
            "पिछले हफ्ते में, आपने कितनी बार बहुत अधिक चिंता महसूस की?" to listOf("😊 कभी नहीं", "🥺 कभी-कभी", "☁️ अक्सर", "😐️ बहुत ज्यादा")
        ),
        Pair(
            "Do you have trouble sleeping?" to listOf("😀 No, I sleep well", "😐 Sometimes, takes time", "🥺️ Yes, I struggle to sleep"),
            "क्या आपको सोने में कठिनाई होती है?" to listOf("😀 नहीं, मैं ठीक से सोता/सोती हूँ", "😐 कभी-कभी, थोड़ा समय लगता है", "🥺️ हाँ, सोने में दिक्कत होती है")
        )
    )

    private val mentalTips = listOf(
        "💡 Tip: Drink plenty of water daily!",
        "💡 Tip: Take 5 deep breaths whenever you feel low.",
        "💡 Tip: Journaling your thoughts can reduce anxiety.",
        "💡 Tip: Avoid screens 30 minutes before sleep.",
        "💡 Tip: Laugh more, stress less!"
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private val CHANNEL_ID = "MindCheckNotification"

    private fun setAlarm() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Self-care Reminder")
            putExtra(AlarmClock.EXTRA_HOUR, 11)
            putExtra(AlarmClock.EXTRA_MINUTES, 11)
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }


        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            Toast.makeText(this, "Alarm set for 11:00 AM", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No app found to set alarm", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness)

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView)
        optionsGroup = findViewById(R.id.optionsGroup)
        nextButton = findViewById(R.id.nextButton)
        progressBar = findViewById(R.id.progressBar)
        tipTextView = findViewById(R.id.tipTextView)
        alarmButton = findViewById(R.id.alarmButton)

        dancingButton = findViewById(R.id.dancingButton)
        singingButton = findViewById(R.id.singingButton)
        paintingButton = findViewById(R.id.paintingButton)
        travelButton = findViewById(R.id.travelButton)
        bhajanButton = findViewById(R.id.bhajanButton)
        templeButton = findViewById(R.id.templeButton)
        familyButton = findViewById(R.id.familyButton)

        progressBar.max = questions.size
        showRandomTip()
        loadQuestion()
        createNotificationChannel()

        nextButton.setOnClickListener {
            val selectedOptionId = optionsGroup.checkedRadioButtonId
            if (selectedOptionId != -1) {
                val selectedOption = findViewById<RadioButton>(selectedOptionId).text.toString()
                updateScore(selectedOption)
                currentQuestionIndex++
                progressBar.progress = currentQuestionIndex

                if (currentQuestionIndex < questions.size) {
                    loadQuestion()
                    showRandomTip()
                } else {
                    showResult()
                }
            } else {
                Toast.makeText(this, "Please choose one option\nकृपया एक उत्तर चुनें", Toast.LENGTH_SHORT).show()
            }
        }

        // Button Actions with Intents
        dancingButton.setOnClickListener { openVideo("https://www.youtube.com/watch?v=eM1E6FFnuZ8") }
        singingButton.setOnClickListener { openVideo("https://www.youtube.com/watch?v=b66-sa8kvE4") }
        paintingButton.setOnClickListener { startActivity(Intent(this, PaintingActivity::class.java)) }
        travelButton.setOnClickListener { startActivity(Intent(this, TravelActivity::class.java)) }
        bhajanButton.setOnClickListener { startActivity(Intent(this, BhajanActivity::class.java)) }
        templeButton.setOnClickListener { findNearestTemple() }
        familyButton.setOnClickListener { showFamilyMessage() }

        // Alarm button

        alarmButton.setOnClickListener {
            Toast.makeText(this, "Alarm Button Clicked", Toast.LENGTH_SHORT).show()
            setAlarm()
            sendReminderNotification("Reminder: Your self-care alarm is set for 11:00 AM.")
        }
    }

    // ✅ Now define the function OUTSIDE onCreate()
    private fun loadQuestion() {
        val (englishQuestion, englishOptions) = questions[currentQuestionIndex].first
        val (hindiQuestion, hindiOptions) = questions[currentQuestionIndex].second

        questionTextView.text = "$englishQuestion\n$hindiQuestion"
        optionsGroup.removeAllViews()

        val combinedOptions = englishOptions.zip(hindiOptions) { en, hi -> "$en\n$hi" }

        for (option in combinedOptions) {
            val radioButton = RadioButton(this)
            radioButton.text = option
            optionsGroup.addView(radioButton)
        }
    }

    private fun updateScore(selectedOption: String) {
        when {
            selectedOption.contains("Very often") || selectedOption.contains("बहुत ज्यादा") ||
                    selectedOption.contains("Yes, I struggle") || selectedOption.contains("हाँ, सोने में") -> score += 2

            selectedOption.contains("Often") || selectedOption.contains("अक्सर") ||
                    selectedOption.contains("Sometimes") || selectedOption.contains("कभी-कभी") -> score += 1
        }
    }

    private fun showResult() {
        val resultMessage = when {
            score >= 4 -> "You seem to be experiencing a lot of stress. Please talk to someone you trust.\nआपको बहुत ज्यादा चिंता होती है। कृपया किसी से बात करें।"
            score in 2..3 -> "Your mental state is normal, but take care of yourself.\nआपकी मानसिक स्थिति सामान्य है, लेकिन खुद का ध्यान रखें।"
            else -> "You have positive energy! Stay happy.\nआपमें पॉजिटिव एनर्जी है! ऐसे ही खुश रहें।"
        }

        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show()
        sendNotification(resultMessage)
    }

    private fun showRandomTip() {
        val randomTip = mentalTips[Random.nextInt(mentalTips.size)]
        tipTextView.text = randomTip
    }

    private fun openVideo(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun findNearestTemple() {
        val gmmIntentUri = Uri.parse("geo:0,0?q=temple")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun showFamilyMessage() {
        val message = "If you are far from family, call them. If nearby, spend time together!"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // Notification channel creation
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MindCheckChannel"
            val descriptionText = "MindCheck Self-Care Notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission", "NotificationPermission")
    private fun sendNotification(message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🧠 MindCheck Result")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(101, builder.build())
        }
    }
    @SuppressLint("MissingPermission")
    private fun sendReminderNotification(message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("⏰ Reminder")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(102, builder.build())
        }
    }

}
