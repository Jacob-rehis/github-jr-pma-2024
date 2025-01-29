package com.example.mycarschool2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Při spuštění aplikace zobrazíme HomeFragment (nebo jiný defaultní)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Tlačítko pro přepnutí na "Objednat jízdu"
        findViewById<Button>(R.id.button_objednat_jizdu).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ObjednatJizduFragment())
                .commit()
        }

        // Tlačítko pro přepnutí na "Teoretická hodina"
        findViewById<Button>(R.id.button_teoreticka_hodina).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TeoretickaHodinaFragment())
                .commit()
        }

        // Tlačítko pro přepnutí na "Podat přihlášku"
        findViewById<Button>(R.id.button_podat_prihlasku).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PodatPrihlaskuFragment())
                .commit()
        }

        // ------------- Nové tlačítko "Zobrazit uživatele" -------------
        findViewById<Button>(R.id.button_zobrazit_uzivatele).setOnClickListener {
            // Vytvoříme (nebo jen použijeme) fragment pro zobrazení uživatelů
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ZobrazitUzivateleFragment())
                .commit()
        }
    }
}
