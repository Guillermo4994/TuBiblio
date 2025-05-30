package com.example.tubiblio

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tubiblio.databinding.ItemLibroBinding

class libroAdapter
    (
        private val libros: MutableList<Libro>,
        private val onItemClick: (Libro) -> Unit
    ) : RecyclerView.Adapter<libroAdapter.LibroViewHolder>(), ListAdapter {

        inner class LibroViewHolder(val binding: ItemLibroBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
            val binding = ItemLibroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LibroViewHolder(binding)
        }

        override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
            val libro = libros[position]
            holder.binding.titulo.text = libro.titulo
            holder.binding.root.setOnClickListener { onItemClick(libro) }
        }

        override fun getItemCount(): Int = libros.size

    fun actualizarDatos(nuevaLista: List<Libro>) {
        libros.clear()
        libros.addAll(nuevaLista)
        notifyDataSetChanged()
    }
    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun areAllItemsEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(position: Int): Boolean {
        TODO("Not yet implemented")
    }
}

