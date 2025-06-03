package com.example.tubiblio

import android.content.Intent
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tubiblio.databinding.ItemLibroBinding

class libroAdapter(
    private val libros: MutableList<Libro>,
    private val onItemClick: (Libro) -> Unit
) : RecyclerView.Adapter<libroAdapter.LibroViewHolder>() {

    inner class LibroViewHolder(val binding: ItemLibroBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding = ItemLibroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.binding.titulo.text = libro.titulo
        holder.binding.root.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, libroPintar::class.java)
            intent.putExtra("libro", libro)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = libros.size

    fun actualizarDatos(nuevaLista: List<Libro>) {
        libros.clear()
        libros.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}


