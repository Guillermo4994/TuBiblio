package com.example.tubiblio

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tubiblio.databinding.ActivityBienvenidaBinding


class bienvenida : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityBienvenidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.iniciar.setOnClickListener (this)
        binding.registrarse.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            binding.iniciar.id -> {
                val intent = Intent(this, InicioSesion::class.java)
                startActivity(intent)

            }
            binding.registrarse.id ->{
                val intent = Intent(this, Registrarse::class.java)
                startActivity(intent)
            }
        }
    }
}
