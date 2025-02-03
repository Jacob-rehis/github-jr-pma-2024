package com.example.myapp8_9_10

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Nastavíme první fragment při spuštění
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Přepínání na HomeFragment (Car)
        findViewById<Button>(R.id.button_home).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Přepínání na SettingsFragment
        findViewById<Button>(R.id.button_settings).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .commit()
        }

        // Přidáváme tlačítko pro DataStoreFragment
        findViewById<Button>(R.id.button_datastore).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DataStoreFragment())
                .commit()
        }
    }
}
