package com.example.mycristmasapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnHome = findViewById<Button>(R.id.btnHome)

        val correctCount = intent.getIntExtra("CORRECT_COUNT", 0)
        val total = intent.getIntExtra("TOTAL_QUESTIONS", 5)

        tvResult.text = "Máš $correctCount z $total správně."

        // Opakovat kvíz
        btnRetry.setOnClickListener {
            // Spustíme znovu kvízovou aktivitu
            val intent = Intent(this, KvizActivity::class.java)
            startActivity(intent)
            finish() // Ukončit ResultActivity, aby se uživatel nevracel sem
        }

        // Hlavní stránka
        btnHome.setOnClickListener {
            // Přechod na MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
