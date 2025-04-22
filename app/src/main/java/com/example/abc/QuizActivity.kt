package com.example.abc

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var toggleLanguageButton: Button

    private var isHindi = false // Default language is English

    private val questions = listOf(
        Pair(
            "How are you feeling today?" to listOf("😀 Very happy", "😊 Somewhat okay", "😐 Neutral", "🥺 Sad", "😓 Very down"),
            "आज आप कैसा महसूस कर रहे हैं?" to listOf("😀 बहुत खुश", "😊 थोड़ा ठीक", "😐 सामान्य", "🥺 उदास", "😓 बहुत निराश")
        ),
        Pair(
            "What is your energy level today?" to listOf("🏃‍♂️ High", "🚶‍➡️ Moderate", "🧍‍♂️ Low"),
            "आज आपकी ऊर्जा का स्तर कैसा है?" to listOf("🏃‍♂️ अधिक", "🚶‍➡️ मध्यम", "🧍‍♂️ कम")
        ),
        Pair(
            "What caused you the most stress today?" to listOf("🏫 Studies / Work", "🏠 Family", "❤️ Relationship", "💰 Financial issues", "🤷‍♂️ No specific reason"),
            "आज आपको सबसे ज्यादा तनाव किस चीज़ से हुआ?" to listOf("🏫 पढ़ाई / काम", "🏠 परिवार", "❤️ रिश्ते", "💰 आर्थिक समस्या", "🤷‍♂️ कोई विशेष कारण नहीं")
        ),
        Pair(
            "Did you get enough sleep last night?" to listOf("😴 Yes, fully rested", "😕 Somewhat", "😟 Not really", "😩 Barely slept"),
            "क्या आपको कल रात भरपूर नींद मिली?" to listOf("😴 हाँ, पूरी तरह आराम किया", "😕 थोड़ा बहुत", "😟 नहीं, ज्यादा नहीं", "😩 बिल्कुल नहीं")
        ),
        Pair(
            "How social did you feel today?" to listOf("😊 Very social", "🙂 Somewhat social", "😐 Neutral", "🙃 Avoided socializing"),
            "आज आप कितने सामाजिक महसूस कर रहे थे?" to listOf("😊 बहुत ज्यादा", "🙂 थोड़ा बहुत", "😐 सामान्य", "🙃 सामाजिकता से बचा")
        ),
        Pair(
            "Have you exercised today?" to listOf("💪 Yes", "🚶‍♂️ Just a little", "🛋️ No"),
            "क्या आपने आज व्यायाम किया?" to listOf("💪 हाँ", "🚶‍♂️ थोड़ा बहुत", "🛋️ नहीं")
        ),
        Pair(
            "Have you eaten healthy food today?" to listOf("🥗 Yes, very healthy", "🍔 Somewhat", "🍟 Not really"),
            "क्या आपने आज स्वस्थ भोजन खाया?" to listOf("🥗 हाँ, बहुत स्वस्थ", "🍔 थोड़ा बहुत", "🍟 नहीं")
        ),
        Pair(
            "How motivated do you feel today?" to listOf("🔥 Very motivated", "😊 Somewhat motivated", "😐 Neutral", "😞 Not motivated"),
            "आज आप कितने प्रेरित महसूस कर रहे हैं?" to listOf("🔥 बहुत प्रेरित", "😊 थोड़ा बहुत", "😐 सामान्य", "😞 बिल्कुल नहीं")
        )
    )


    private val correctAnswers = listOf(0, 1, 2, 0, 0, 0, 0, 0) // Example

    private var currentQuestionIndex = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionTextView = findViewById(R.id.questionTextView)
        optionsGroup = findViewById(R.id.optionsGroup)
        submitButton = findViewById(R.id.submitButton)
        toggleLanguageButton = findViewById(R.id.btnToggleLanguage)

        loadQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
        }

        toggleLanguageButton.setOnClickListener {
            isHindi = !isHindi
            toggleLanguageButton.text = if (isHindi) "Switch to English" else "Switch to Hindi"
            loadQuestion() // Reload question in selected language
        }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex < questions.size) {
            val (english, hindi) = questions[currentQuestionIndex]
            val question = if (isHindi) hindi.first else english.first
            val options = if (isHindi) hindi.second else english.second
            optionsGroup.clearCheck() // Reset radio selection after language toggle

            questionTextView.text = question
            optionsGroup.removeAllViews()  // Clear previous options

            for ((index, option) in options.withIndex()) {
                val radioButton = RadioButton(this)
                radioButton.text = option
                radioButton.id = index
                optionsGroup.addView(radioButton)
            }
        } else {
            showResult()
        }
    }

    private fun checkAnswer() {
        val selectedId = optionsGroup.checkedRadioButtonId
        if (selectedId == -1) {
            showMessage(if (isHindi) "कृपया एक उत्तर चुनें!" else "Please select an answer!")
            return
        }

        val message = if (selectedId == correctAnswers[currentQuestionIndex]) {
            if (isHindi) "🎉 सही उत्तर!" else "🎉 Correct Answer!"
        } else {
            if (isHindi) "❌ गलत उत्तर! फिर से कोशिश करें।" else "❌ Wrong Answer! Try Again."
        }

        showMessage(message)
        currentQuestionIndex++
        loadQuestion() // Move to the next question
        optionsGroup.clearCheck()  // Reset the selection after moving to the next question
    }

    private fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(if (isHindi) "क्विज़ परिणाम" else "Quiz Result")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showResult() {
        AlertDialog.Builder(this)
            .setTitle(if (isHindi) "क्विज़ पूरा हुआ" else "Quiz Completed")
            .setMessage(if (isHindi) "आपने क्विज़ पूरा कर लिया है!" else "You have completed the quiz!")
            .setPositiveButton("OK") { dialog, _ -> finish() }
            .show()
    }
}
