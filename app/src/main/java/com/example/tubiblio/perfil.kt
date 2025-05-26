package com.example.tubiblio

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tubiblio.databinding.ActivityPerfilBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.simplifiedcoding.androidphpmysql.WebService
import android.util.Log

class perfil : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPerfilBinding
    private  var  id= -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nombreUsuario = intent.getStringExtra("usuario_nombre")
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.modificar.setOnClickListener{
            actualizarUsuario()
        }
        binding.eliminar.setOnClickListener{
            Log.d("Perfil", "Eliminar usuario con id: $id")
            eliminarUsuario(id)
        }

        if (nombreUsuario.isNullOrBlank()) {
            Toast.makeText(this, "Usuario no especificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        cargarUsuario(nombreUsuario.toString())
    }

    private fun cargarUsuario(usuarioNombre: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerUsuario(usuarioNombre)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.error == false && body.usuario != null) {

                        val u = body.usuario

                        id = u.idUsuario
                        Log.d("Perfil", "ID usuario cargado: $id")
                        binding.usuario.setText(u.usuario)
                        binding.contrasenia.setText(u.pass)
                        binding.edad.setText(u.fecha_nacimiento)
                        binding.poblacion.setText(u.poblacion)
                        binding.email.setText(u.email)
                    } else {
                        Toast.makeText(
                            this@perfil,
                            body?.message ?: "Respuesta inesperada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@perfil,
                        "Error HTTP ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@perfil,
                    "Error de red: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    fun actualizarUsuario() {
        val nombre = binding.usuario.text.toString().trim()
        val pass = binding.contrasenia.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val fecha_nacimiento = binding.edad.text.toString().trim()
        val poblacion = binding.poblacion.text.toString().trim()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.modificarUsuario(id,nombre, pass, email, fecha_nacimiento, poblacion)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        Toast.makeText(this@perfil, body.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@perfil, "Error en el servidor", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@perfil, "Error de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun eliminarUsuario(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.eliminarUsuario(id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(this@perfil, response.body()!!.message, Toast.LENGTH_LONG).show()
                        if (!response.body()!!.error) {
                            finish()
                        }
                    } else {
                        Toast.makeText(this@perfil, "Error en el servidor", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@perfil, "Error de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}


