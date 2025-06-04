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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.simplifiedcoding.androidphpmysql.RetrofitClient

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
                val usuarioText = binding.usuario.text.toString().trim()
                val passText    = binding.contrasenia.text.toString().trim()

                if (usuarioText.isEmpty() || passText.isEmpty()){
                    Snackbar.make(binding.root,"Te falta introducir algun campo", Snackbar.LENGTH_LONG).show()
                }
                loginConApi(usuarioText, passText)

            }
            binding.btnOlvidar.id->{
                val intent = Intent(this, RecuperarContrasena::class.java)
                startActivity(intent)
            }
        }
    }
    private fun loginConApi(usuario: String, password: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.loginUsuario(usuario, password)
                runOnUiThread {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        if (!body.error && body.usuario != null) {

                            val intent = Intent(this@InicioSesion, principal::class.java)

                            intent.putExtra("usuario_nombre", body.usuario.usuario)
                            startActivity(intent)
                            finish()
                        } else {
                            // error=true: mostramos el mensaje
                            val msg = body.message ?: "Error desconocido"
                            Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        // Error de HTTP (ej. 500, 404)
                        Snackbar.make(binding.root, "Error en el servidor: ${response.code()}", Snackbar.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Snackbar.make(binding.root, "Excepción: ${e.localizedMessage}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}

