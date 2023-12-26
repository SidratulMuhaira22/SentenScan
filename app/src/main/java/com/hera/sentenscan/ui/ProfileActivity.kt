package com.hera.sentenscan.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hera.sentenscan.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvSelectedUser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val chooseUserButton: Button = findViewById(R.id.btn_choose_user)
        chooseUserButton.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val nameFromPrefs = sharedPreferences.getString("name", "")

        tvName = findViewById(R.id.tv_name)
        tvName.text = nameFromPrefs

        tvSelectedUser = findViewById(R.id.textView4)

        // Ambil data dari Intent
        val selectedUserName = intent.getStringExtra("SELECTED_USER_NAME")

        // Tetapkan teks pada TextView
        tvSelectedUser.text = selectedUserName ?: "Selected User Name"

        val imgBack: ImageView = findViewById(R.id.ic_back)
        imgBack.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    companion object {
        fun newIntent(context: Context, name: String): Intent {
            val intent = Intent(context, ProfileActivity::class.java)
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.apply()

            return intent
        }
    }
}
