package com.example.mycarschool2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PodatPrihlaskuFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextDateOfBirth: EditText
    private lateinit var buttonSubmitForm: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Propojení s naším rozvržením (layoutem)
        return inflater.inflate(R.layout.fragment_podat_prihlasku, container, false)
    }

    // Kód, který bude volán hned po vytvoření View (po onCreateView),
    // tady můžeme najít naše View prvky.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Najdeme prvky podle jejich ID
        editTextName = view.findViewById(R.id.editTextName)
        editTextSurname = view.findViewById(R.id.editTextSurname)
        editTextDateOfBirth = view.findViewById(R.id.editTextDateOfBirth)
        buttonSubmitForm = view.findViewById(R.id.button_submit_form)

        // Příklad: co se stane po kliknutí na tlačítko
        buttonSubmitForm.setOnClickListener {
            val name = editTextName.text.toString()
            val surname = editTextSurname.text.toString()
            val dateOfBirth = editTextDateOfBirth.text.toString()

            if (name.isEmpty() || surname.isEmpty() || dateOfBirth.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("users")

            // Fetch the highest existing ID first
            usersCollection.orderBy("id", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnSuccessListener { documents ->
                    var newId = 1 // Default ID for the first user

                    if (!documents.isEmpty) {
                        val highestId = documents.documents[0].getLong("id") ?: 0
                        newId = highestId.toInt() + 1 // Increment ID for new user
                    }

                    // Generate a new document ID
                    val newUserRef = usersCollection.document()

                    // Create a map of the user data
                    val userData = hashMapOf(
                        "id" to newId, // Assign the new unique ID
                        "name" to name,
                        "last_name" to surname,
                        "birthdate" to dateOfBirth
                    )

                    // Store data in Firestore
                    newUserRef.set(userData)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "User added successfully!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error fetching user ID: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }




        }
    }
}
