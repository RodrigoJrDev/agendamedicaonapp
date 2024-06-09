package com.example.agendamedicaon.model

data class Horario(
    val id: Int,
    val data_disponivel: String,
    val hora_inicio: String,
    val hora_fim: String
)