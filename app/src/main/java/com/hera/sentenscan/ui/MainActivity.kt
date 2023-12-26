package com.hera.sentenscan.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hera.sentenscan.R

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var sentenceEditText: EditText
    private val PROFILE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.ed_name)
        sentenceEditText = findViewById(R.id.ed_polindrome)

        val checkButton: Button = findViewById(R.id.btn_check)
        checkButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val sentence = sentenceEditText.text.toString()

            if (name.isBlank() && sentence.isBlank()) {
                showErrorMessage("The name and palindrome sentence cannot be empty.")
            } else if (name.isBlank()) {
                showErrorMessage("The name cannot be empty.")
            } else if (sentence.isBlank()) {
                showErrorMessage("The palindrome sentence cannot be empty.")
            } else {
                val isPalindrome = checkPalindrome(sentence)
                val message = if (isPalindrome) "Is Palindrome" else "Not Palindrome"

                AlertDialog.Builder(this)
                    .setTitle("Palindrome")
                    .setMessage(message)
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        val nextButton: Button = findViewById(R.id.btn_next)
        nextButton.setOnClickListener {
            val name = nameEditText.text.toString()

            if (name.isNotBlank()) {
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.apply()

                startActivityForResult(ProfileActivity.newIntent(this, name), PROFILE_REQUEST_CODE)
            } else {
                Toast.makeText(this, "The name cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s".toRegex(), "").toLowerCase()
        val reversedText = cleanText.reversed()
        return cleanText == reversedText
    }

    private fun showErrorMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onResume() {
        super.onResume()

        nameEditText.setText("")
        sentenceEditText.setText("")
    }
}
