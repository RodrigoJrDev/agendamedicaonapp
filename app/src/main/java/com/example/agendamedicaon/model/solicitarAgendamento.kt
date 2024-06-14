package com.example.agendamedicaon.model

data class AgendamentoRequest(
    val medicoId: Int,
    val horarioId: Int,
    val pacienteId: Int,
    val especialidadeNome: String
)
