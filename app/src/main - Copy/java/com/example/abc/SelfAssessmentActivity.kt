package com.example.abc

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SelfAssessmentActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var nextButton: Button

    private val questions = listOf(
        Pair(
            "In the past week, how often have you felt very anxious?" to listOf(
                "🌟 Never",
                "🌤️ Sometimes",
                "☁️ Often",
                "🌧️ Very often"
            ),
            "पिछले हफ्ते में, आपने कितनी बार बहुत अधिक चिंता महसूस की?" to listOf(
                "🌟 कभी नहीं",
                "🌤️ कभी-कभी",
                "☁️ अक्सर",
                "🌧️ बहुत ज्यादा"
            )
        ),
        Pair(
            "Do you have trouble sleeping?" to listOf(
                "😴 No, I sleep well",
                "🌙 Sometimes, takes time",
                "🌧️ Yes, I struggle to sleep"
            ),
            "क्या आपको सोने में कठिनाई होती है?" to listOf(
                "😴 नहीं, मैं ठीक से सोता/सोती हूँ",
                "🌙 कभी-कभी, थोड़ा समय लगता है",
                "🌧️ हाँ, सोने में दिक्कत होती है"
            )
        ),
        Pair(
            "Do you feel like you have no one to talk to?" to listOf(
                "👨‍👩‍👧‍👦 No, I have support",
                "😕 Sometimes",
                "😔 Yes, I feel very lonely"
            ),
            "क्या आपको कभी ऐसा लगता है कि आप किसी से बात नहीं कर सकते?" to listOf(
                "👨‍👩‍👧‍👦 नहीं, मेरे पास सपोर्टिव लोग हैं",
                "😕 कभी-कभी",
                "😔 हाँ, मैं बहुत अकेला/अकेली महसूस करता/करती हूँ"
            )
        ),
        Pair(
            "Have you lost interest in daily activities?" to listOf(
                "😃 No, I enjoy them",
                "😐 Sometimes",
                "😞 Yes, I don't feel good anymore"
            ),
            "क्या आप रोज़ की एक्टिविटीज़ में रुचि खो चुके हैं?" to listOf(
                "😃 नहीं, मैं एंजॉय करता/करती हूँ",
                "😐 कभी-कभी",
                "😞 हाँ, मुझे अब कुछ अच्छा नहीं लगता"
            )
        ),
        Pair(
            "Do you feel tired throughout the day, even after proper sleep?" to listOf(
                "⚡ No, I feel energetic",
                "🌙 Sometimes",
                "😴 Yes, always tired"
            ),
            "क्या आपको दिनभर थकान महसूस होती है, भले ही आप ठीक से सोए हों?" to listOf(
                "⚡ नहीं, मैं एनर्जेटिक महसूस करता/करती हूँ",
                "🌙 कभी-कभी",
                "😴 हाँ, मुझे हमेशा थकान लगती है"
            )
        ),
        Pair(
            "Do you get irritated easily over small things?" to listOf(
                "😇 No, I stay calm",
                "😑 Sometimes",
                "😡 Yes, I get angry quickly"
            ),
            "क्या आपको छोटी-छोटी चीज़ों पर गुस्सा आ जाता है?" to listOf(
                "😇 नहीं, मैं शांत रहता/रहती हूँ",
                "😑 कभी-कभी",
                "😡 हाँ, बहुत जल्दी गुस्सा आ जाता है"
            )
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_assessment)

        questionTextView = findViewById(R.id.questionTextView)
        optionsGroup = findViewById(R.id.optionsGroup)
        nextButton = findViewById(R.id.nextButton)

        loadQuestion()

        nextButton.setOnClickListener {
            val selectedOptionId = optionsGroup.checkedRadioButtonId
            if (selectedOptionId != -1) {
                val selectedOption = findViewById<RadioButton>(selectedOptionId).text.toString()
                updateScore(selectedOption)
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    loadQuestion()
                } else {
                    showResult()
                }
            } else {
                Toast.makeText(this, "कृपया एक उत्तर चुनें", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadQuestion() {
        val (englishQuestion, englishOptions) = questions[currentQuestionIndex].first
        val (hindiQuestion, hindiOptions) = questions[currentQuestionIndex].second

        questionTextView.text = "$englishQuestion\n$hindiQuestion"
        optionsGroup.removeAllViews()

        // Combining English and Hindi options together
        val combinedOptions = englishOptions.zip(hindiOptions) { en, hi -> "$en\n$hi" }

        for (option in combinedOptions) {
            val radioButton = RadioButton(this)
            radioButton.text = option
            optionsGroup.addView(radioButton)
        }
    }

    private fun updateScore(selectedOption: String) {
        when {
            selectedOption.contains("🌧️ Very often") || selectedOption.contains("😔 Yes, I feel very lonely") || selectedOption.contains(
                "😞 Yes, I don't feel good anymore"
            ) || selectedOption.contains("😴 Yes, always tired") || selectedOption.contains("😡 Yes, I get angry quickly") -> score += 2

            selectedOption.contains("☁️ Often") || selectedOption.contains("😕 Sometimes") || selectedOption.contains(
                "😐 Sometimes"
            ) || selectedOption.contains("🌙 Sometimes") || selectedOption.contains("😑 Sometimes") -> score += 1
        }
    }

    private fun showResult() {
        val resultMessage = when {
            score >= 8 -> "You seem to be experiencing a lot of stress. Please talk to someone you trust.\nआपको बहुत ज्यादा चिंता होती है। कृपया किसी से बात करें।"
            score in 4..7 -> "Your mental state is normal, but take care of yourself.\nआपकी मानसिक स्थिति सामान्य है, लेकिन खुद का ध्यान रखें।"
            else -> "You have positive energy! Stay happy.\nआपमें पॉजिटिव एनर्जी है! ऐसे ही खुश रहें।"
        }
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show()
    }
}
