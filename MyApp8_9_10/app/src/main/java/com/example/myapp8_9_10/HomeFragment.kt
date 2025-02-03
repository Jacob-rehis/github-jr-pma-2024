package com.example.myapp8_9_10

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var selectedVehicleText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pojmenujeme všechny Views
        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val radioGroup = view.findViewById<RadioGroup>(R.id.vehicle_radio_group)
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        val loadButton = view.findViewById<Button>(R.id.load_button)
        selectedVehicleText = view.findViewById(R.id.selected_vehicle_text)

        // Confirm jen uloží
        confirmButton.setOnClickListener {
            val selectedVehicle = when (radioGroup.checkedRadioButtonId) {
                R.id.radio_skoda -> "Škoda"
                R.id.radio_audi -> "Audi"
                R.id.radio_mercedes -> "Mercedes"
                else -> null
            }

            if (selectedVehicle != null) {
                sharedPreferences.edit()
                    .putString("selected_vehicle", selectedVehicle)
                    .apply()
                Toast.makeText(requireContext(), "Uloženo: $selectedVehicle", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please select a vehicle", Toast.LENGTH_SHORT).show()
            }
        }

        // Load načte a zobrazí
        loadButton.setOnClickListener {
            loadSavedVehicle(radioGroup)
        }
    }

    private fun loadSavedVehicle(radioGroup: RadioGroup) {
        val savedVehicle = sharedPreferences.getString("selected_vehicle", null)
        when (savedVehicle) {
            "Škoda" -> radioGroup.check(R.id.radio_skoda)
            "Audi" -> radioGroup.check(R.id.radio_audi)
            "Mercedes" -> radioGroup.check(R.id.radio_mercedes)
            else -> {
                // nic není uloženo
                radioGroup.clearCheck()
                selectedVehicleText.text = "Zatím nebylo nic uloženo."
                Toast.makeText(requireContext(), "Žádné auto není uloženo", Toast.LENGTH_SHORT).show()
                return
            }
        }
        // Pokud je savedVehicle != null, ukaž ho v TextView
        selectedVehicleText.text = "Zvolené auto: $savedVehicle"
    }
}
