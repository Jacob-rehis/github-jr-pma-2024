package com.example.myapplication06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication06.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val destination = intent.getStringExtra("DESTINATION")
        val date = intent.getStringExtra("DATE")
        val transport = intent.getStringExtra("TRANSPORT")
        val accommodation = intent.getStringExtra("ACCOMMODATION")

        val summary = "Destination: $destination\nDate: $date\nTransportation: $transport\nAccommodation: $accommodation"
        binding.textViewTravelSummary.text = summary

    }
}