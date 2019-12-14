package com.bignerdranch.android.geoquiz

import android.os.Build
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk =  [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        mainActivity = Robolectric.setupActivity<MainActivity>(MainActivity::class.java)
    }

    @Test
    fun `onCreate - should display the first question on create`() {

        val textView: TextView = mainActivity.findViewById(R.id.question_text_field)

        assertThat(textView).isNotNull()

        assertThat(textView.text).isEqualTo("Canberra is the capital of Australia")

    }



    @Test
    fun `onCreate - should display the second question after clicking the next button`() {

        mainActivity.next_button.performClick()

        val textView: TextView = mainActivity.findViewById(R.id.question_text_field)

        assertThat(textView).isNotNull()

        assertThat(textView.text).isEqualTo("Lake Baikal is the world's oldest and deepest lake")

    }

    @Test
    fun `onCreate - should display the second question again clicking the prev button`() {
        //when
        mainActivity.next_button.performClick()
        mainActivity.next_button.performClick()
        mainActivity.prev_button.performClick()

        val textView: TextView = mainActivity.findViewById(R.id.question_text_field)

        assertThat(textView).isNotNull()

        assertThat(textView.text).isEqualTo("Lake Baikal is the world's oldest and deepest lake")

    }
}