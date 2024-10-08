package com.example.myapplicationobjednavka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationobjednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení obrázku podle výběru druhu steaku
        binding.radioGroupSteak.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioButtonRibeye.id -> {
                    binding.imageViewSteak.setImageResource(R.drawable.ribeye)
                }
                binding.radioButtonTbone.id -> {
                    binding.imageViewSteak.setImageResource(R.drawable.tbone)
                }
                binding.radioButtonSirloin.id -> {
                    binding.imageViewSteak.setImageResource(R.drawable.sirloin)
                }
            }
        }

        // Nastavení souhrnu objednávky při kliknutí na tlačítko "odeslat"
        binding.odeslat.setOnClickListener {
            val steakType = when (binding.radioGroupSteak.checkedRadioButtonId) {
                binding.radioButtonRibeye.id -> "Ribeye"
                binding.radioButtonTbone.id -> "T-Bone"
                binding.radioButtonSirloin.id -> "Sirloin"
                else -> ""
            }

            val doneness = when (binding.radioGroupDoneness.checkedRadioButtonId) {
                binding.radioButtonRare.id -> "Rare"
                binding.radioButtonMedium.id -> "Medium"
                binding.radioButtonWellDone.id -> "Well Done"
                else -> ""
            }

            val spices = mutableListOf<String>()
            if (binding.checkBoxPepper.isChecked) spices.add("Pepř")
            if (binding.checkBoxGarlic.isChecked) spices.add("Česnek")
            if (binding.checkBoxPaprika.isChecked) spices.add("Paprika")

            val summary = "Steak: $steakType\nPropečení: $doneness\nKoření: ${spices.joinToString(", ")}"
            binding.summaryTextView.text = "Souhrn objednávky:\n$summary"
        }
    }
}
