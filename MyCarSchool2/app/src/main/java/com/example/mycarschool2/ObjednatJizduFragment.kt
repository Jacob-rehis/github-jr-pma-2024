package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mycarschool2.databinding.FragmentObjednatJizduBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// Jednoduchá data class pro uživatele.
data class User(
    val id: Int = 0,
    val name: String = "",
    val last_name: String = "",
    val birthdate: String = ""
)

class ObjednatJizduFragment : Fragment() {

    private var _binding: FragmentObjednatJizduBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    // Proměnné pro vybraného uživatele:
    // - celé jméno (pro zobrazení v Toastu)
    // - ID (které se uloží do lessons)
    private var selectedUserFullName: String? = null
    private var selectedUserId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObjednatJizduBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val spinnerUsers = binding.spinnerUsers

        // ------------------ 1) SPINNER PRO UŽIVATELE ------------------
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                // Seznamy pro zobrazovaná jména a pro ID
                val userNames = mutableListOf<String>()
                val userIds = mutableListOf<Int>()

                for (document in documents) {
                    val user = document.toObject<User>()
                    val fullName = "${user.name} ${user.last_name}"

                    // Uložíme do seznamů
                    userNames.add(fullName)
                    userIds.add(user.id)
                }

                // Naplníme Spinner řetězci pro zobrazení
                val userAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userNames
                )
                userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerUsers.adapter = userAdapter

                // Listener pro vybranou položku
                spinnerUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Uložíme si vybrané jméno a ID
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

        // ------------------ 2) SPINNER PRO AUTA ------------------
        val cars = listOf("Škoda Fabia", "Audi A3", "Mercedes-Benz")
        val adapterCars = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cars)
        adapterCars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCars.adapter = adapterCars

        // ------------------ 3) VÝBĚR DATA (CALENDARVIEW) ------------------
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth.${month + 1}.$year"
        }

        // ------------------ 4) TIMEPICKER ------------------
        binding.timePicker.setIs24HourView(true)
        binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
            if (hour in 7..18) {  // Povolený čas 7:00 až 19:00
                selectedTime = String.format("%02d:%02d", hour, minute)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Jízdu lze objednat pouze od 7:00 do 19:00.",
                    Toast.LENGTH_SHORT
                ).show()
                // Reset na 7:00 nebo 19:00
                if (hour < 7) {
                    binding.timePicker.hour = 6
                    binding.timePicker.minute = 59
                } else {
                    binding.timePicker.hour = 19
                    binding.timePicker.minute = 0
                }
            }
        }

        // ------------------ 5) POTVRZENÍ JÍZDY ------------------
        binding.buttonConfirmDrive.setOnClickListener {
            val selectedCar = binding.spinnerCars.selectedItem.toString()

            // Zkontrolujeme, zda máme datum, čas i vybraného uživatele
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty() && selectedUserId != null) {
                // Zobrazíme toast pro kontrolu
                Toast.makeText(
                    requireContext(),
                    "Jízda zarezervována: $selectedDate $selectedTime, Auto: $selectedCar," +
                            " Uživatel: $selectedUserFullName (ID=$selectedUserId)",
                    Toast.LENGTH_LONG
                ).show()

                // Uložíme do Firestore (kolekce "lessons")
                val lessonsCollection = db.collection("lessons")
                val newLessonRef = lessonsCollection.document()

                // Ukládáme do "user" pouze ID uživatele:
                val lessonData = hashMapOf(
                    "date" to "$selectedDate $selectedTime",
                    "type" to "practical",
                    "car" to selectedCar,
                    "user" to selectedUserId
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
                    "Vyberte uživatele, datum a čas jízdy!",
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
