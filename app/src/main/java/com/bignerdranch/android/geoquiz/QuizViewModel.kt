package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    init {
        Log.d(TAG, "ViewModel Instance Created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel Instance About to Be Destroyed!")
    }
}



//CTRL 0 for overrides!
