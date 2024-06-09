package com.example.agendamedicaon.model

data class Consulta(
    val id: Int,
    val data: String,
    val hora: String,
    val medico: String,
    val especialidade: String
)