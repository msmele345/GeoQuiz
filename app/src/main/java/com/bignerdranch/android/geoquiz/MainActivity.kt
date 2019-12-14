package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.geoquiz.models.Question
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageView
    private lateinit var prevButton: ImageView
    private lateinit var questionTextView: TextView

    var currentIndex = 0
    var correctCount = 0
    var incorrectCount = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_asia, true, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate(Bundle?) called")


        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_field)

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        trueButton.setOnClickListener { view ->
            checkAnswer(true)
            questionBank[currentIndex].isDisabled = true
            //or remove the question from the bank?
        }

        falseButton.setOnClickListener { view ->
            checkAnswer(false)
            questionBank[currentIndex].isDisabled = true
        }

        nextButton.setOnClickListener {
            nextQuestion()
            if (questionBank.all { it.isDisabled }) {
                Toast.makeText(
                    this,
                    "Game Complete!",
                    Toast.LENGTH_SHORT

                ).also {
                    it.setGravity(Gravity.TOP, 0, 30)
                }.show()
            }
            when {
                checkIfGameComplete() -> {
                    finishGame()
                }
                else -> updateQuestion()
            }

        }

        prevButton.setOnClickListener {
            currentIndex = when {
                currentIndex == 0 -> 0
                else -> (currentIndex - 1) % questionBank.size
            }
            updateQuestion()
        }

        updateQuestion()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun checkIfGameComplete() : Boolean {
        if (questionBank.all { it.isDisabled }) {
            Toast.makeText(
                this,
                "Congrats! You finished with a score of: ${calculateScore()}",
                Toast.LENGTH_SHORT

            ).also {
                it.setGravity(Gravity.TOP, 0, 50)
            }.show()
            return true
        }
        return false
    }

    private fun finishGame() {
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
        questionTextView.visibility = View.GONE
    }

    private fun calculateScore(): Int {
        return ((correctCount.toDouble() / questionBank.size.toDouble()) * 100).roundToInt()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(answer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        when (correctAnswer == answer) {
            true -> {
                Toast.makeText(
                    this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT
                ).also {
                    it.setGravity(Gravity.TOP, 0, 30)
                }.show()
                correctCount++
                println("correct count: $correctCount")
            }
            else -> {
                Toast.makeText(
                    this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT
                ).also {
                    it.setGravity(Gravity.TOP, 0, 30)
                }.show()
                incorrectCount++
                println("correct count: $incorrectCount")
            }
        }
    }
}