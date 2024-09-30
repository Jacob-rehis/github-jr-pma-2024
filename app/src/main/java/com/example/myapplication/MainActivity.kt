package com.example.myapplication


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare the EditText and Button variables
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var townEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var deleteButton: Button
    private lateinit var outputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link the views with their corresponding IDs
        nameEditText = findViewById(R.id.name)
        surnameEditText = findViewById(R.id.surname)
        ageEditText = findViewById(R.id.age)
        townEditText = findViewById(R.id.obec)
        sendButton = findViewById(R.id.send)
        deleteButton = findViewById(R.id.Delete)
        outputTextView = findViewById(R.id.output)

        // Set an onClickListener for the sendButton to format and display the input
        sendButton.setOnClickListener {
            displayFormattedText()
        }

        // Set an onClickListener for the deleteButton to clear the input fields and TextView
        deleteButton.setOnClickListener {
            clearFields()
        }
    }

    // Function to format the input and display it in the TextView
    private fun displayFormattedText() {
        val name = nameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val age = ageEditText.text.toString().trim()
        val town = townEditText.text.toString().trim()

        // Format the text, each on a new line
        val formattedText = "Jméno: $name\nPříjmení: $surname\nVěk: $age\nObec: $town"

        // Set the formatted text to the TextView
        outputTextView.text = formattedText
    }

    // Function to clear the input fields and TextView
    private fun clearFields() {
        nameEditText.text.clear()
        surnameEditText.text.clear()
        ageEditText.text.clear()
        townEditText.text.clear()
        outputTextView.text = ""
    }
}