package com.example.agendamedicaon.model

data class LoginResponse(
    val status: String,
    val data: Paciente?,
    val message: String?
)
