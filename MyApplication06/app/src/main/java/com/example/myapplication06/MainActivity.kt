package com.example.myapplication06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication06.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener {
            val destination = binding.editTextDestination.text.toString()
            val date = binding.editTextDate.text.toString()
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("DESTINATION", destination)
                putExtra("DATE", date)
            }
            startActivity(intent)
        }
    }
}