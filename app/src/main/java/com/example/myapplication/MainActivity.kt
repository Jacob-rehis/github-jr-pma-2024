package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    // Pole receptů a ingrediencí
    private val recipes = arrayOf("Špagety Carbonara", "Kuřecí řízek", "Bramborový salát")
    private val ingredients = arrayOf(
        "Ingredience pro Špagety Carbonara: \n- Špagety\n- Slanina\n- Vejce\n- Sýr Pecorino\n- Pepř",
        "Ingredience pro Kuřecí řízek: \n- Kuřecí prsa\n- Strouhanka\n- Mouka\n- Vejce\n- Sůl, pepř",
        "Ingredience pro Bramborový salát: \n- Brambory\n- Mrkev\n- Cibule\n- Majonéza\n- Okurky"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeListView: ListView = findViewById(R.id.recipe_list)
        val outputTextView: TextView = findViewById(R.id.output)

        // Adaptér pro ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipes)
        recipeListView.adapter = adapter

        // Klikací posluchač na položky v ListView
        recipeListView.setOnItemClickListener { _, _, position, _ ->
            outputTextView.text = ingredients[position]
        }
    }
}