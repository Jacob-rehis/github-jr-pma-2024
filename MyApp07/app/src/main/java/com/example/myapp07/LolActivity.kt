package com.example.myapp07

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class LolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lol)

        // Získání dat z předchozí aktivity
        val playerName = intent.getStringExtra("PLAYER_NAME")
        val playerPosition = intent.getStringExtra("PLAYER_POSITION")

        // Zobrazení dat v TextView
        val playerDataTextView: TextView = findViewById(R.id.playerData)
        playerDataTextView.text = "Hráč: $playerName, Pozice: $playerPosition"

        // Zobrazení Snackbaru s daty
        Snackbar.make(findViewById(R.id.playerData), "Hráč: $playerName, Pozice: $playerPosition", Snackbar.LENGTH_LONG).show()

        // Nastavení tlačítka zpět na MainActivity
        val backButton: Button = findViewById(R.id.btnBackToMain)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Ukončí aktuální aktivitu, aby uživatel nemohl stisknout tlačítko zpět a vrátit se zpět na LolActivity
        }
    }
}
