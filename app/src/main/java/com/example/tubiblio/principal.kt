package com.example.tubiblio

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tubiblio.databinding.ActivityInicioSesionBinding
import com.example.tubiblio.databinding.ActivityPrincipalBinding
import kotlinx.coroutines.launch

class principal : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var adapter: libroAdapter

            override fun onCreate(savedInstanceState: Bundle?) {
        val nombreUsuario = intent.getStringExtra("usuario_nombre")

        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)

        setContentView(binding.root)
        adapter = libroAdapter(mutableListOf()) { libroSeleccionado ->
            val intent = Intent(this@principal, libroPintar::class.java)
            intent.putExtra("libro", libroSeleccionado)
            startActivity(intent)

        }
        binding.listLibros.adapter = adapter
        binding.btnMenu.setOnClickListener {
            val popup = PopupMenu(this, binding.btnMenu)
            popup.menuInflater.inflate(R.menu.menu_opciones, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.opcion1 -> {
                        Toast.makeText(this, "Opción 1", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,perfil::class.java)
                        intent.putExtra("usuario_nombre", nombreUsuario)
                        startActivity(intent)
                        true
                    }
                    R.id.opcion2 -> {
                        Toast.makeText(this, "Opción 2", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.opcion3 -> {
                        Toast.makeText(this, "Opción 3", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }
        binding.busqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    buscarLibros(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }
    private fun buscarLibros(query: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.buscarLibros(query)
                if (response.isSuccessful && response.body()?.error == false) {
                    val libros = response.body()?.libros ?: emptyList()
                    adapter.actualizarDatos(libros)

                    Toast.makeText(this@principal, "Tamaño de la lista: ${libros.size}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@principal, "Sin resultados", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@principal, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
