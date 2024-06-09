package com.example.agendamedicaon.model

data class AppointmentRequest(
    val id_medico: Int,
    val id_paciente: Int,
    val data_consulta: String,
    val observacoes: String
)