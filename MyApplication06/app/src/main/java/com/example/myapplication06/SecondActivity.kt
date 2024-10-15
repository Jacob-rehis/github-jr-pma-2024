package com.example.myapplication06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication06.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val destination = intent.getStringExtra("DESTINATION")
        val date = intent.getStringExtra("DATE")
        binding.textViewTravelInfo.text = "Destination: $destination\nDate: $date"

        binding.buttonNextToThird.setOnClickListener {
            val transport = binding.editTextTransport.text.toString()
            val accommodation = binding.editTextAccommodation.text.toString()
            val intent = Intent(this, ThirdActivity::class.java).apply {
                putExtra("DESTINATION", destination)
                putExtra("DATE", date)
                putExtra("TRANSPORT", transport)
                putExtra("ACCOMMODATION", accommodation)
            }
            startActivity(intent)
        }
    }
}