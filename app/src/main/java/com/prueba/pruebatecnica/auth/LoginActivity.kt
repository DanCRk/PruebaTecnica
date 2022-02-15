package com.prueba.pruebatecnica.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prueba.pruebatecnica.carreras.NoneActivity
import com.prueba.pruebatecnica.carreras.ComputacionActivity
import com.prueba.pruebatecnica.carreras.QuimicaActivity
import com.prueba.pruebatecnica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val db = FirebaseFirestore.getInstance()
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openRegsiter()

        loginUser()
    }

    private fun loginUser(){

        binding.loginButton.setOnClickListener {

            val mail = binding.mailET.text.toString()
            val password = binding.passwordET.text.toString()

            if(mail.isNotEmpty() && password.isNotEmpty()){
                 FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            db.collection("users").document(mail).get().addOnSuccessListener {response ->
                                val carrera = response.get("carrera") as String?
                                if (carrera.equals("computacion")){
                                    openComputacionActivity()
                                    finish()
                                }else if (carrera.equals("quimica")){
                                    openQuimicaActivity()
                                    finish()
                                }else{
                                    openNoneActivity()
                                    finish()
                                }
                            }

                            saveUserData(mail, password)

                            Toast.makeText(this, "Logueado con exito!", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(this, "Datos introducidos incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Campos sin llenar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(mail:String, password:String) {
        val prefs = getSharedPreferences(prefs_file,Context.MODE_PRIVATE).edit()
        prefs.putString("mail", mail)
        prefs.putString("password", password)
        prefs.apply()
    }

    private fun openQuimicaActivity(){
        startActivity(Intent(this, QuimicaActivity::class.java))
    }

    private fun openComputacionActivity(){
        startActivity(Intent(this, ComputacionActivity::class.java))
    }

    private fun openNoneActivity(){
        startActivity(Intent(this, NoneActivity::class.java))
    }

    private fun openRegsiter(){
        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}