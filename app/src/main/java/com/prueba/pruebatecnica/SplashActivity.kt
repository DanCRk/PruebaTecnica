package com.prueba.pruebatecnica

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.prueba.pruebatecnica.auth.LoginActivity
import com.prueba.pruebatecnica.carreras.ComputacionActivity
import com.prueba.pruebatecnica.carreras.NoneActivity
import com.prueba.pruebatecnica.carreras.QuimicaActivity
import com.prueba.pruebatecnica.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val db = FirebaseFirestore.getInstance()
    private val prefs_file = "PREFRERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Poner transparente el status bar y la barra de acciones
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        setContentView(R.layout.activity_splash)
        login()

    }

    private fun login(){
        val prefs = getSharedPreferences(prefs_file, Context.MODE_PRIVATE)
        val mail = prefs.getString("mail",null)
        val password = prefs.getString("password",null)

        if(mail!=null && password!=null){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        db.collection("users").document(mail).get().addOnSuccessListener {response ->
                            val carrera = response.get("carrera") as String?
                            if (carrera.equals("computacion")){
                                startActivity(Intent(this, ComputacionActivity::class.java))
                                finish()
                            }else if (carrera.equals("quimica")){
                                startActivity(Intent(this, QuimicaActivity::class.java))
                                finish()
                            }else{
                                startActivity(Intent(this, NoneActivity::class.java))
                                finish()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Problema con el login", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

}