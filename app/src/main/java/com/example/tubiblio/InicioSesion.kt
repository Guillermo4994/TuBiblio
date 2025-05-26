package com.example.tubiblio

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tubiblio.databinding.ActivityInicioSesionBinding
import com.google.android.material.snackbar.Snackbar

class InicioSesion : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityInicioSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnIniciar.setOnClickListener(this)
        binding.btnOlvidar.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnIniciar.id ->{
                if (binding.usuario.text.isEmpty() || binding.contrasenia.text.isEmpty()){
                    Snackbar.make(binding.root,"Te falta introducir algun campo", Snackbar.LENGTH_LONG).show()
                }else{
                    val intent = Intent(this, principal::class.java)
                    val nombreUsuario = binding.usuario.text.toString()
                    intent.putExtra("usuario_nombre", nombreUsuario)
                    startActivity(intent)
                }
            }
            binding.btnOlvidar.id->{
                val intent = Intent(this, RecuperarContrasena::class.java)
                startActivity(intent)
            }
        }
    }
}
