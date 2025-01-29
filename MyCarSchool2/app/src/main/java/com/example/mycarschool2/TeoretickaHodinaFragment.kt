package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mycarschool2.databinding.FragmentTeoretickaHodinaBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*

// Jednoduchá datová třída pro uživatele


class TeoretickaHodinaFragment : Fragment() {

    private var _binding: FragmentTeoretickaHodinaBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    // Uložíme si zvlášť vybraného uživatele:
    // - jeho celé jméno pro zobrazení
    // - jeho ID pro uložení do "lessons"
    private var selectedUserFullName: String? = null
    private var selectedUserId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeoretickaHodinaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        // ------------------ 1) NAPLNĚNÍ SPINNERU UŽIVATELI ------------------
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                // Seznam jmen (pro zobrazení ve Spinneru)
                val userNames = mutableListOf<String>()
                // Seznam odpovídajících ID (pro uložení do lessons)
                val userIds = mutableListOf<Int>()

                for (doc in documents) {
                    val user = doc.toObject<User>()
                    val fullName = "${user.name} ${user.last_name}"

                    userNames.add(fullName)
                    userIds.add(user.id)  // sem uložíme ID z dokumentu
                }

                val userAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userNames
                )
                userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.spinnerUsers.adapter = userAdapter

                // Reakce na vybranou položku ve Spinneru
                binding.spinnerUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Uložíme vybraného uživatele: jméno + ID
                        selectedUserFullName = userNames[position]
                        selectedUserId = userIds[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectedUserFullName = null
                        selectedUserId = null
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Chyba při načítání uživatelů: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // ------------------ 2) DATE PICKER ------------------
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

                // Teoretické hodiny povolíme pouze v úterý a ve čtvrtek
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

        // ------------------ 3) TIMEPICKER (16:00 - 17:30) ------------------
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

        // ------------------ 4) POTVRZENÍ (ULOŽENÍ DO LESSONS) ------------------
        binding.buttonConfirmTheory.setOnClickListener {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty() && selectedUserId != null) {

                // pro kontrolu zobrazíme i vybrané celé jméno
                Toast.makeText(
                    requireContext(),
                    "Přihlášení na teorii: $selectedDate $selectedTime, " +
                            "Uživatel: $selectedUserFullName (ID=$selectedUserId)",
                    Toast.LENGTH_LONG
                ).show()

                val lessonsCollection = db.collection("lessons")
                val newLessonRef = lessonsCollection.document()

                // Uložíme do pole "user" už jen ID
                val lessonData = hashMapOf(
                    "date" to "$selectedDate $selectedTime", // datum a čas dohromady
                    "type" to "teoretic",
                    "user" to selectedUserId  // <--- Ukládáme jen ID
                )

                newLessonRef.set(lessonData)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Lesson added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Vyberte platný den, čas a uživatele!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
