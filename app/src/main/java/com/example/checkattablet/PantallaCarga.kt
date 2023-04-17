package com.example.checkattablet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.Navigation.findNavController

class PantallaCarga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)

        Handler(Looper.myLooper()!!).postDelayed({

            val intent = Intent(this, Login::class.java)
            this.startActivity(intent)
            this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }, 2500)


    }
}