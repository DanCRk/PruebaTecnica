package com.prueba.pruebatecnica.carreras

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prueba.pruebatecnica.auth.LoginActivity
import com.prueba.pruebatecnica.databinding.ActivityComputacionBinding

class ComputacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComputacionBinding
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComputacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setText()

        showAlertDialog()

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

    private fun showAlertDialog() {
        binding.boton1.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Alerta")
                .setMessage("ISC")
                .setNeutralButton("Ok", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {

                    }
                })
                .show()
        }
    }

    private fun setText() {
        binding.boton2.setOnClickListener {
            if (binding.campoTexto1.text.isNotEmpty()) {
                binding.mensaje.text = binding.campoTexto1.text
                binding.campoTexto1.text = null
            } else {
                Toast.makeText(this, "No hay un mensaje introducido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}