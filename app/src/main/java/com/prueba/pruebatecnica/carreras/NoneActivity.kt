package com.prueba.pruebatecnica.carreras

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prueba.pruebatecnica.auth.LoginActivity
import com.prueba.pruebatecnica.databinding.ActivityNoneBinding

class NoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoneBinding
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutUser()
    }

    private fun logoutUser() {
        binding.logut.setOnClickListener {
            val prefs = getSharedPreferences(prefs_file, Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}