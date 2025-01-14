package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mycarschool2.databinding.FragmentTeoretickaHodinaBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class TeoretickaHodinaFragment : Fragment() {

    private var _binding: FragmentTeoretickaHodinaBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeoretickaHodinaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Otevření dialogového kalendáře pro výběr datumu
        binding.btnDatePicker.setOnClickListener {
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Vyberte datum teoretické hodiny")
                .setSelection(today)
                .build()

            datePicker.addOnPositiveButtonClickListener { date ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = date
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

                if (dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.THURSDAY) {
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val year = calendar.get(Calendar.YEAR)
                    selectedDate = "$dayOfMonth.$month.$year"
                    Toast.makeText(requireContext(), "Vybráno: $selectedDate", Toast.LENGTH_SHORT).show()
                } else {
                    selectedDate = ""
                    Toast.makeText(
                        requireContext(),
                        "Teoretické hodiny jsou možné pouze v úterý a čtvrtek.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            datePicker.show(parentFragmentManager, "datePicker")
        }

        // TimePicker: pouze čas od 16:00 do 17:30
        binding.timePickerTheory.setIs24HourView(true)
        binding.timePickerTheory.setOnTimeChangedListener { _, hour, minute ->
            if (hour < 16 || hour > 17 || (hour == 17 && minute > 30)) {
                Toast.makeText(
                    requireContext(),
                    "Teoretické hodiny jsou pouze od 16:00 do 17:30.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.timePickerTheory.hour = 16
                binding.timePickerTheory.minute = 0
            } else {
                selectedTime = String.format("%02d:%02d", hour, minute)
            }
        }

        // Potvrzení přihlášení
        binding.buttonConfirmTheory.setOnClickListener {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Přihlášení na teorii: $selectedDate $selectedTime",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(requireContext(), "Vyberte platný den a čas!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
