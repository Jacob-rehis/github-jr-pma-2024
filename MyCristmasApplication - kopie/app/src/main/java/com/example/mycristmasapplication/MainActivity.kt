package com.example.mycristmasapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Zajištění odsazení pro systemBars (statusbar, navigační panel apod.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Najdeme tlačítka z layoutu
        val btnZajimavosti = findViewById<Button>(R.id.btnZajimavosti)
        val btnKviz = findViewById<Button>(R.id.btnKviz)

        // Otevře Zajimavosti
        btnZajimavosti.setOnClickListener {
            val intent = Intent(this, ZajimavostiActivity::class.java)
            startActivity(intent)
        }

        // Otevře Kviz
        btnKviz.setOnClickListener {
            val intent = Intent(this, KvizActivity::class.java)
            startActivity(intent)
        }
    }
}
