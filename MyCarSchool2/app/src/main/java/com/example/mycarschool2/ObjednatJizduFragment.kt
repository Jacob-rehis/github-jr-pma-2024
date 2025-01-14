package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mycarschool2.databinding.FragmentObjednatJizduBinding

class ObjednatJizduFragment : Fragment() {

    private var _binding: FragmentObjednatJizduBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObjednatJizduBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nastavení výběru automobilu
        val cars = listOf("Škoda Fabia", "Audi A3", "Mercedes-Benz")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cars)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCars.adapter = adapter

        // Výběr data
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }

        // Nastavení TimePicker na 24hodinový formát a omezení času
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
            if (hour in 7..18) {  // Povolený čas je 7:00 až 19:00
                selectedTime = String.format("%02d:%02d", hour, minute)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Jízdu lze objednat pouze od 7:00 do 19:00.",
                    Toast.LENGTH_SHORT
                ).show()
                // Resetování času na 7:00 nebo 19:00, pokud je mimo povolený rozsah
                if (hour < 7) {
                    binding.timePicker.hour = 6
                    binding.timePicker.minute = 59
                } else {
                    binding.timePicker.hour = 19
                    binding.timePicker.minute = 0
                }
            }
        }

        // Potvrzení jízdy
        binding.buttonConfirmDrive.setOnClickListener {
            val selectedCar = binding.spinnerCars.selectedItem.toString()
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Jízda zarezervována: $selectedDate $selectedTime, Auto: $selectedCar",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(requireContext(), "Vyberte datum a čas jízdy!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
