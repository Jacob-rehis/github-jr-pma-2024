package com.example.myapp07

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp07.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener pro Toast
        binding.btnShowToast.setOnClickListener {
            showCustomToast()
        }

        // Listener pro Snackbar
        binding.btnShowSnackbar.setOnClickListener {
            Snackbar.make(binding.root, "Toto je Snackbar zpráva!", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    Toast.makeText(this, "Akce vrácena!", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Listener pro potvrzení výběru
        binding.submitButton.setOnClickListener {
            val playerName = binding.playerName.text.toString()
            val selectedPositionId = binding.positionGroup.checkedRadioButtonId

            if (playerName.isEmpty()) {
                Toast.makeText(this, "Zadej jméno hráče", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedPositionId == -1) {
                Toast.makeText(this, "Vyber pozici", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadioButton = findViewById<RadioButton>(selectedPositionId)
            val selectedPosition = selectedRadioButton.text.toString()

            // Vytvoření a přechod na novou aktivitu s daty
            val intent = Intent(this, LolActivity::class.java).apply {
                putExtra("PLAYER_NAME", playerName)
                putExtra("PLAYER_POSITION", selectedPosition)
            }
            startActivity(intent)
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomToast() {
        val inflater: LayoutInflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_toast, null)

        val toastText: TextView = layout.findViewById(R.id.toastText)
        val toastIcon: ImageView = layout.findViewById(R.id.toastIcon)

        toastText.text = "Vlastní Toast zpráva!"
        toastIcon.setImageResource(R.drawable.dalleapp)

        val params = toastIcon.layoutParams
        params.width = 64
        params.height = 64
        toastIcon.layoutParams = params

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}