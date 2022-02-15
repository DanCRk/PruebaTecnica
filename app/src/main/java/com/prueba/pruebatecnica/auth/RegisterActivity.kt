package com.prueba.pruebatecnica.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prueba.pruebatecnica.carreras.NoneActivity
import com.prueba.pruebatecnica.carreras.ComputacionActivity
import com.prueba.pruebatecnica.carreras.QuimicaActivity
import com.prueba.pruebatecnica.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val db = FirebaseFirestore.getInstance()
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createUser()

        openLogin()
    }

    private fun createUser() {

        binding.registerButton.setOnClickListener {

            val mail = binding.mailET.text.toString()
            val password = binding.passwordET.text.toString()

            if (mail.isNotEmpty() && password.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (binding.radioButtonComputacion.isChecked) {
                                openComputacionActivity()
                                finish()
                            } else if (binding.radioButtonQuimica.isChecked) {
                                openQuimicaActivity()
                                finish()
                            } else {
                                openNoneActivity()
                                finish()
                            }
                            Toast.makeText(this, "Registrado con exito!", Toast.LENGTH_SHORT).show()
                            saveData()
                            saveUserData(mail, password)
                        } else {
                            Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Campos sin llenar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(mail:String, password:String) {
        val prefs = getSharedPreferences(prefs_file, Context.MODE_PRIVATE).edit()
        prefs.putString("mail", mail)
        prefs.putString("password", password)
        prefs.apply()
    }

    private fun openQuimicaActivity() {
        startActivity(Intent(this, QuimicaActivity::class.java))
    }

    private fun openComputacionActivity() {
        startActivity(Intent(this, ComputacionActivity::class.java))
    }

    private fun openNoneActivity() {
        startActivity(Intent(this, NoneActivity::class.java))
    }

    private fun openLogin() {
        binding.textLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun saveData() {
        var carrera = "None"
        val mail = binding.mailET.text.toString()

        if (binding.radioButtonComputacion.isChecked) {
            carrera = "computacion"
        } else if (binding.radioButtonQuimica.isChecked) {
            carrera = "quimica"
        }
        db.collection("users").document(mail)
            .set(hashMapOf("carrera" to carrera))
    }
}