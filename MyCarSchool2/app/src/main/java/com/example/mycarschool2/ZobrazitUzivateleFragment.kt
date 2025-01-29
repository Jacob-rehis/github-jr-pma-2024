package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mycarschool2.databinding.FragmentZobrazitUzivateleBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


data class Lesson(
    val date: String = "",
    val type: String = "" // "teoretic" / "practical"
    // + případná další pole (car, user apod.)
)

class ZobrazitUzivateleFragment : Fragment() {

    private var _binding: FragmentZobrazitUzivateleBinding? = null
    private val binding get() = _binding!!

    // Udržujeme si seznam jmen uživatelů a jejich ID.
    private val userNames = mutableListOf<String>()
    private val userIds = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZobrazitUzivateleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Naplnění spinneru uživateli
        val db = FirebaseFirestore.getInstance()
        db.collection("users").get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val user = doc.toObject<User>()
                    // Celé jméno pro zobrazení
                    val fullName = "${user.name} ${user.last_name}"
                    userNames.add(fullName)
                    userIds.add(user.id)
                }

                // Nastavíme adaptér
                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    userNames
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSelectUser.adapter = spinnerAdapter

            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Chyba: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        // 2) Po kliknutí na "Načíst údaje" zjistíme, koho uživatel vybral.
        binding.buttonLoadUser.setOnClickListener {
            val position = binding.spinnerSelectUser.selectedItemPosition
            if (position == AdapterView.INVALID_POSITION) {
                Toast.makeText(requireContext(), "Nejprve vyberte uživatele!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedUserId = userIds[position]

            // a) Načíst detail uživatele
            loadUserDetail(selectedUserId)

            // b) Načíst jeho lekce (lessons)
            loadUserLessons(selectedUserId)
        }
    }

    private fun loadUserDetail(userId: Int) {
        // V kolekci "users" jste měli "id" uvnitř dokumentu, ale nevíme, zda jméno dokumentu je "id"
        // nebo nějaký generovaný. Pokud ukládáte i "id" do dokumentu, musíte sami zjistit,
        // jak ho dohledat. Např.:
        //  - Buď prohledat documents, co mají pole "id" == userId
        //  - Nebo už dopředu ukládáte do Firestore s "document(userId.toString())"

        // Pro zjednodušení: prohledáme znovu kolekci "users" a najdeme ten správný záznam
        val db = FirebaseFirestore.getInstance()
        db.collection("users").whereEqualTo("id", userId).limit(1).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val user = documents.first().toObject<User>()
                    // Zobrazíme v textView
                    val detailText = """
                        Jméno: ${user.name}
                        Příjmení: ${user.last_name}
                        Narozen: ${user.birthdate}
                        (ID: ${user.id})
                    """.trimIndent()

                    binding.textViewUserDetails.text = detailText
                } else {
                    binding.textViewUserDetails.text = "Uživatel s id=$userId nebyl nalezen."
                }
            }
            .addOnFailureListener { e ->
                binding.textViewUserDetails.text = "Chyba při načítání uživatele: ${e.message}"
            }
    }

    private fun loadUserLessons(userId: Int) {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .whereEqualTo("user", userId)
            .get()
            .addOnSuccessListener { documents ->
                // Oddělíme teoretické a praktické
                val theoryLessons = mutableListOf<String>()
                val practicalLessons = mutableListOf<String>()

                for (doc in documents) {
                    val lesson = doc.toObject<Lesson>()
                    if (lesson.type == "teoretic") {
                        theoryLessons.add(lesson.date)
                    } else if (lesson.type == "practical") {
                        practicalLessons.add(lesson.date)
                    }
                }

                // Zobrazíme v textView
                binding.textViewTheoryLessons.text = if (theoryLessons.isEmpty()) {
                    "(Žádné teoretické lekce)"
                } else {
                    theoryLessons.joinToString(separator = "\n")
                }

                binding.textViewPracticalLessons.text = if (practicalLessons.isEmpty()) {
                    "(Žádné praktické lekce)"
                } else {
                    practicalLessons.joinToString(separator = "\n")
                }
            }
            .addOnFailureListener { e ->
                binding.textViewTheoryLessons.text = "Chyba: ${e.message}"
                binding.textViewPracticalLessons.text = "Chyba: ${e.message}"
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
