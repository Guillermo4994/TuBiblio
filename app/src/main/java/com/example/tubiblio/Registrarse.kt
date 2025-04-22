package com.example.tubiblio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tubiblio.databinding.ActivityInicioSesionBinding
import com.example.tubiblio.databinding.ActivityRegistrarseBinding

class Registrarse : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}