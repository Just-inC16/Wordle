package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Choose a random word
        val randomWord=FourLetterWordList.getRandomFourLetterWord()
        Log.v("The word chosen is", randomWord)
        //Guess button functionality
        val guessButton=findViewById<Button>(R.id.guessButton)
        guessButton.setOnClickListener(){
            Toast.makeText(this,"Successful Guess!", Toast.LENGTH_SHORT).show()
            Log.v("The word chosen is", randomWord)
        }
    }
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
//    private fun checkGuess(guess: String) : String {
//        var result = ""
//        for (i in 0..3) {
//            if (guess[i] == wordToGuess[i]) {
//                result += "O"
//            }
//            else if (guess[i] in wordToGuess) {
//                result += "+"
//            }
//            else {
//                result += "X"
//            }
//        }
//        return result
//    }
}