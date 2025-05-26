package com.example.tubiblio

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tubiblio.databinding.ActivityInicioSesionBinding
import com.example.tubiblio.databinding.ActivityPrincipalBinding

class principal : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val nombreUsuario = intent.getStringExtra("usuario_nombre")

        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)

        setContentView(binding.root)

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

    }
}
