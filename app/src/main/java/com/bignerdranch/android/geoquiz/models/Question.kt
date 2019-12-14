package com.bignerdranch.android.geoquiz.models

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,
    val answer: Boolean,
    var isDisabled: Boolean
)
