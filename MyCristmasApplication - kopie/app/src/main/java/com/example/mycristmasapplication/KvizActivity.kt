package com.example.mycristmasapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class KvizActivity : AppCompatActivity() {

    // Tady můžeš definovat list otázek
    private val questions = listOf(
        Question(
            questionText = "Který den v Česku tradičně nosí dárky Ježíšek?",
            optionA = "24. prosince",
            optionB = "25. prosince",
            optionC = "6. prosince",
            correctAnswer = 'A'
        ),
        Question(
            questionText = "Jak se jmenuje vánoční píseň: „... All Ye Faithful“?",
            optionA = "Come",
            optionB = "Walk",
            optionC = "Run",
            correctAnswer = 'A'
        ),
        Question(
            questionText = "Co symbolizuje adventní věnec se 4 svíčkami?",
            optionA = "4 roční období",
            optionB = "4 adventní neděle",
            optionC = "4 světové strany",
            correctAnswer = 'B'
        ),
        Question(
            questionText = "Jak se říká 26. prosinci v některých zemích?",
            optionA = "Den díkůvzdání",
            optionB = "Boxing Day",
            optionC = "Epiphany",
            correctAnswer = 'B'
        ),
        Question(
            questionText = "Jak se v anglosaských zemích nazývá Štědrý den?",
            optionA = "Eve Day",
            optionB = "Christmas Feast",
            optionC = "Christmas Eve",
            correctAnswer = 'C'
        )
    )

    private var currentQuestionIndex = 0

    // Pole k uložení výsledků (true = správně, false = špatně, null = neodpovězeno)
    private val answers = Array<Boolean?>(questions.size) { null }

    private lateinit var tvQuestion: TextView
    private lateinit var btnOptionA: Button
    private lateinit var btnOptionB: Button
    private lateinit var btnOptionC: Button
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kviz)

        tvQuestion = findViewById(R.id.tvQuestion)
        btnOptionA = findViewById(R.id.btnOptionA)
        btnOptionB = findViewById(R.id.btnOptionB)
        btnOptionC = findViewById(R.id.btnOptionC)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)

        // Načteme první otázku
        loadQuestion(currentQuestionIndex)

        // Kliknutí na odpověď A
        btnOptionA.setOnClickListener {
            checkAnswer('A', btnOptionA)
        }

        // Kliknutí na odpověď B
        btnOptionB.setOnClickListener {
            checkAnswer('B', btnOptionB)
        }

        // Kliknutí na odpověď C
        btnOptionC.setOnClickListener {
            checkAnswer('C', btnOptionC)
        }

        // Tlačítko Zpět
        btnBack.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                loadQuestion(currentQuestionIndex)
            }
        }

        // Tlačítko Další
        btnNext.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                loadQuestion(currentQuestionIndex)
            } else {
                // Jsme na konci - zobrazíme výsledek (např. nová aktivita)
                showResult()
            }
        }
    }

    private fun loadQuestion(index: Int) {
        // Reset barev tlačítek
        resetButtonColors()

        val q = questions[index]
        tvQuestion.text = q.questionText
        btnOptionA.text = "A) " + q.optionA
        btnOptionB.text = "B) " + q.optionB
        btnOptionC.text = "C) " + q.optionC

        // Pokud už bylo odpovězeno, zvýrazníme to:
        val userAnswer = answers[index]
        if (userAnswer != null) {
            // Najdeme, která odpověď je správná
            val correct = q.correctAnswer
            // Zvýrazníme správnou/špatnou volbu
            when (correct) {
                'A' -> btnOptionA.setBackgroundColor(Color.GREEN)
                'B' -> btnOptionB.setBackgroundColor(Color.GREEN)
                'C' -> btnOptionC.setBackgroundColor(Color.GREEN)
            }
            // Kterou odpověď označil uživatel?
            if (userAnswer == false) {
                // Uživatel odpověděl špatně, potřebujeme zjistit, co zvolil
                // Tady si můžeš buď ukládat i konkrétní písmeno,
                // nebo to vyřešit jinak.
                // Pro zjednodušení - pokud nepotřebuješ vědět, co zvolil,
                // jen víš, že je to špatně.
            }
        }
    }

    private fun checkAnswer(selectedOption: Char, button: Button) {
        val correctOption = questions[currentQuestionIndex].correctAnswer
        if (selectedOption == correctOption) {
            // Správně
            button.setBackgroundColor(Color.GREEN)
            answers[currentQuestionIndex] = true
        } else {
            // Špatně
            button.setBackgroundColor(Color.RED)
            answers[currentQuestionIndex] = false
            // Současně můžeme zvýraznit, která byla správná
            highlightCorrectAnswer()
        }
    }

    private fun highlightCorrectAnswer() {
        val correctOption = questions[currentQuestionIndex].correctAnswer
        when (correctOption) {
            'A' -> btnOptionA.setBackgroundColor(Color.GREEN)
            'B' -> btnOptionB.setBackgroundColor(Color.GREEN)
            'C' -> btnOptionC.setBackgroundColor(Color.GREEN)
        }
    }

    private fun resetButtonColors() {
        // Např. na default button background
        // Můžeš použít i btnOptionA.setBackgroundResource(android.R.drawable.btn_default)
        // Pokud chceš styling, přidej vlastní style.
        btnOptionA.setBackgroundColor(Color.parseColor("#6200EE"))
        btnOptionB.setBackgroundColor(Color.parseColor("#6200EE"))
        btnOptionC.setBackgroundColor(Color.parseColor("#6200EE"))
    }

    private fun showResult() {
        // Spočítáme počet správných odpovědí
        val correctCount = answers.count { it == true }
        // Přeneseme např. do nové aktivity
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("CORRECT_COUNT", correctCount)
        intent.putExtra("TOTAL_QUESTIONS", questions.size)
        startActivity(intent)
        finish()
    }
}
