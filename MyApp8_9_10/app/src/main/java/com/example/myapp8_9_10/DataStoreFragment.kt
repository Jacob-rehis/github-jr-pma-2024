package com.example.myapp8_9_10

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "my_datastore")

class DataStoreFragment : Fragment(R.layout.fragment_datastore) {

    //  Definujeme klíč, pod kterým bude uložen náš text
    private val TEXT_KEY = stringPreferencesKey("text_key")

    private lateinit var editText: EditText
    private lateinit var resultText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText = view.findViewById(R.id.ds_edit_text)
        resultText = view.findViewById(R.id.ds_result_text)

        val saveButton = view.findViewById<Button>(R.id.ds_save_button)
        val loadButton = view.findViewById<Button>(R.id.ds_load_button)

        // Uložení do DataStore
        saveButton.setOnClickListener {
            val textToSave = editText.text.toString()
            if (textToSave.isNotEmpty()) {
                // DataStore je asynchronní -> použijeme runBlocking pro jednoduchý příklad
                runBlocking {
                    requireContext().dataStore.edit { prefs ->
                        prefs[TEXT_KEY] = textToSave
                    }
                }
                Toast.makeText(requireContext(), "Uloženo do DataStore: $textToSave", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Pole je prázdné", Toast.LENGTH_SHORT).show()
            }
        }

        // Načtení z DataStore
        loadButton.setOnClickListener {
            val loadedText = runBlocking {
                // hned přečteme první hodnotu
                val prefs = requireContext().dataStore.data.first()
                prefs[TEXT_KEY] ?: "Nic uloženo"
            }
            resultText.text = "Načteno z DataStore: $loadedText"
        }
    }
}
