package com.example.tubiblio

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.tubiblio.databinding.ActivityLibroPintarBinding
import kotlinx.coroutines.launch


class libroPintar : AppCompatActivity() {

    private lateinit var binding: ActivityLibroPintarBinding
    private lateinit var usuarioEmail: String
    private lateinit var usuario : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibroPintarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val libro = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("libro", Libro::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("libro") as? Libro
        }

        usuarioEmail = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("pref_usuario_email", "") ?: ""
        usuario = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("pref2_usuario_usuario", "") ?: ""

        Log.d("libroPintar", "Intent recibido: ${intent.extras}")

        if (libro == null) {
            Toast.makeText(this, "Error al cargar datos del libro", Toast.LENGTH_SHORT).show()
            Log.e("libroPintar", "Error: Libro es null")
            finish()
            return
        }
        mostrarDatos(libro)
        Log.d("libroPintar", "Libro recibido: ${libro.titulo}")

        binding.btnReservar.setOnClickListener {
            val lugarRecogida = "Ayuntamiento de vadocondes"
            reservarEnServidor(usuario, usuarioEmail, libro.titulo, lugarRecogida)
        }
    }

    //Asignamos los datos a los campos de nuestra pantalla libro.
    private fun mostrarDatos(libro: Libro) {
        binding.apply {
            Glide.with(this@libroPintar)
                .load(libro.portada)
                .into(portadaLibro)
            findViewById<TextView>(R.id.resumen).text = libro.descripcion
            findViewById<TextView>(R.id.titulo).text = libro.titulo
        }
    }
    private fun reservarEnServidor(usuario: String, email: String, libroTitulo: String, lugar: String) {
        lifecycleScope.launch {
            try {
                // hace loading
                binding.btnReservar.isEnabled = false
                binding.btnReservar.text = "Reservando..."

                val response = RetrofitClient.instance.reservarLibro(
                    usuarioUsuario = usuario,
                    usuarioEmail = email,
                    libroTitulo       = libroTitulo,
                    lugarRecogida = lugar
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        // recibimos fecha_devolucion
                        val fechaDevolucion = body.fecha_devolucion ?: "desconocida"
                        Toast.makeText(
                            this@libroPintar,
                            "Reserva enviada. Fecha de devolución: $fechaDevolucion",
                            Toast.LENGTH_LONG
                        ).show()
                        // volver a principal
                        val intent = Intent(this@libroPintar,principal::class.java)
                    } else {
                        Toast.makeText(
                            this@libroPintar,
                            "Error al reservar: ${body?.message ?: "Respuesta vacía"}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.btnReservar.isEnabled = true
                        binding.btnReservar.text = "Reservar"
                    }
                } else {
                    Toast.makeText(
                        this@libroPintar,
                        "Error en la petición: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.btnReservar.isEnabled = true
                    binding.btnReservar.text = "Reservar"
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@libroPintar,
                    "Excepción: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
                binding.btnReservar.isEnabled = true
                binding.btnReservar.text = "Reservar"
            }
        }
    }
}