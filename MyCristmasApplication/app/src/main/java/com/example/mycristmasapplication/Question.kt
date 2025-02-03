package com.example.mycristmasapplication

data class Question(
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val correctAnswer: Char // 'A', 'B' nebo 'C'
)
