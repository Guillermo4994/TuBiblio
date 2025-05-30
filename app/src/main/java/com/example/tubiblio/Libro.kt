package com.example.tubiblio

import java.io.Serializable


data class Libro(
    val id: Int,
    val isbn: String,
    val titulo: String,
    val anio: String,
    val n_pag: String,
    val autor_id: Int,
    val genero_id: Int,
    val descripcion: String
): Serializable

