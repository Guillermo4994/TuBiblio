package com.example.tubiblio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubiblio.databinding.ActivityRegistrarseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.simplifiedcoding.androidphpmysql.RetrofitClient


class Registrarse : AppCompatActivity(), View.OnClickListener {

    //private lateinit var adaptador: UsuarioAdaptador
    private lateinit var binding: ActivityRegistrarseBinding
    var listaUsuarios = arrayListOf<Usuario>()

    var usuario = Usuario((-1),"","","","","")

    var isEditando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iniciar.setOnClickListener(this)



    }
    override fun onClick(v: View?) {
        Toast.makeText(this, "Click detectado", Toast.LENGTH_SHORT).show()
        when(v?.id) {
            binding.iniciar.id -> {
                if (binding.usuario.text.isEmpty() || binding.edad.text.isEmpty() || binding.poblacion.text.isEmpty() || binding.contrasena.text.isEmpty() || binding.contrasena2.text.isEmpty() || binding.email.text.isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        "Te falta introducir algun campo",
                        Snackbar.LENGTH_LONG
                    ).show();
                }

                if (binding.contrasena.text.toString() != binding.contrasena2.text.toString()) {
                    Snackbar.make(
                        binding.root,
                        "Las contraseñas no coinciden",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                val prefs = getSharedPreferences("usuario_pref", Context.MODE_PRIVATE)
                prefs.edit().putString("email", binding.email.text.toString().trim()).apply()
                val prefs2 = getSharedPreferences("usuario_pref2", Context.MODE_PRIVATE)
                prefs.edit().putString("usuario", binding.usuario.text.toString().trim()).apply()

                agregarUsuario()

            }


        }
    }
    fun agregarUsuario() {
        val nombre = binding.usuario.text.toString().trim()
        val pass = binding.contrasena.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val fechaNacimiento = binding.edad.text.toString().trim()
        val poblacion = binding.poblacion.text.toString().trim()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.agregarUsuario(nombre, pass, email, fechaNacimiento, poblacion)
                runOnUiThread {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        Toast.makeText(this@Registrarse, body.message, Toast.LENGTH_LONG).show()
                        if (!body.error) {
                            val intent = Intent(this@Registrarse, principal::class.java)
                            startActivity(intent)
                            finish() // para no regresar a esta pantalla
                        }
                    } else {
                        Toast.makeText(this@Registrarse, "Error en el servidor: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Registrarse, "Excepción: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}