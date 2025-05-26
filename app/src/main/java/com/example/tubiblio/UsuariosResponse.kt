package com.example.tubiblio;

import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    val error: Boolean,
    val message: String?,
    val usuario: Usuario?
)

