package com.prueba.pruebatecnica.carreras

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prueba.pruebatecnica.auth.LoginActivity
import com.prueba.pruebatecnica.databinding.ActivityQuimicaBinding

class QuimicaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuimicaBinding
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuimicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAlertDialog()

        showText()

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

    private fun showText() {
        binding.campoTexto2.addTextChangedListener { texto ->
            binding.mensaje.text = texto
        }
    }

    private fun showAlertDialog() {
        binding.boton.setOnClickListener {
            val text = binding.campoTexto1.text.toString()
            if(text.isNotEmpty()){
                MaterialAlertDialogBuilder(this)
                    .setTitle("Alert")
                    .setMessage("Ing.Quimica $text")
                    .setNeutralButton("Ok", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {

                        }

                    })
                    .show()
            }else{
                Toast.makeText(this, "No hay un texto introducido en el campo 1", Toast.LENGTH_SHORT).show()
            }
        }
    }
}