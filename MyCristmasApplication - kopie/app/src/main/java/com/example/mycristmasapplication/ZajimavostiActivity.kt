package com.example.mycristmasapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ZajimavostiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zajimavosti)

        // Najdeme UI prvky
        val tvZajimavosti = findViewById<TextView>(R.id.tvZajimavosti)
        val btnHome = findViewById<Button>(R.id.btnHomeFromZajimavosti)

        // Nastavíme delší text
        tvZajimavosti.text = """
            Vánoce jsou v České republice jedním z nejoblíbenějších svátků, plným tradic a rodinných setkání. Typická večeře na Štědrý den zahrnuje rybí polévku, bramborový salát a smaženého kapra. Dárky obvykle nosí Ježíšek, a to 24. prosince, kdy se rodiny scházejí okolo vánočního stromečku. Oblíbenou tradicí je pouštění lodiček ze skořápek ořechů či rozkrajování jablíček pro předpověď zdraví. Mezi další evropské tradice patří rozdávání drobných dárků, zpívání koled či stavění betlémů. Moderní Vánoce často kombinují křesťanské i pohanské zvyklosti. Lidé zdobí své domy světly, pečou cukroví a užívají si slavnostní atmosféru po celý adventní čas. Navíc mnohé rodiny věří v udržování symbolů naděje a lásky.
        """.trimIndent()

        // Tlačítko "Hlavní stránka" vrátí uživatele do MainActivity
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Volitelné: finish(), pokud nechceme umožnit návrat zpět do zajímavostí
            finish()
        }
    }
}
