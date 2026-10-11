package com.example.minishopmanager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etStudentName = findViewById<EditText>(R.id.etStudentName)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        prefs = getSharedPreferences("ShopPrefs", MODE_PRIVATE)

        etStudentName.setText(
            prefs.getString("USER_NAME", "")
        )

        cbRemember.isChecked =
            prefs.getBoolean("REMEMBER", false)

        btnLogin.setOnClickListener {
            val name = etStudentName.text.toString().trim()

            if (name.isEmpty()) {
                etStudentName.error = "Entrez votre nom"
                return@setOnClickListener
            }

            val editor = prefs.edit()

            if (cbRemember.isChecked) {
                editor.putString("USER_NAME", name)
                editor.putBoolean("REMEMBER", true)
            } else {
                editor.remove("USER_NAME")
                editor.putBoolean("REMEMBER", false)
            }

            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USER_NAME", name)
            startActivity(intent)
        }
    }
}
