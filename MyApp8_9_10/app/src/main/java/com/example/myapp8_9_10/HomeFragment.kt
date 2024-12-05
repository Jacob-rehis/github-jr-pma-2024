package com.example.myapp8_9_10

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val radioGroup = view.findViewById<RadioGroup>(R.id.vehicle_radio_group)
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)

        // Načtení uloženého výběru vozidla
        val savedVehicle = sharedPreferences.getString("selected_vehicle", null)
        when (savedVehicle) {
            "Škoda" -> radioGroup.check(R.id.radio_skoda)
            "Audi" -> radioGroup.check(R.id.radio_audi)
            "Mercedes" -> radioGroup.check(R.id.radio_mercedes)
        }

        // Potvrzení a uložení výběru vozidla
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
                Toast.makeText(requireContext(), "$selectedVehicle selected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please select a vehicle", Toast.LENGTH_SHORT).show()
            }


        }
    }
}
