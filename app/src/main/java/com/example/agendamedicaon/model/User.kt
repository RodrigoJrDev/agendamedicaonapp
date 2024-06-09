package com.example.agendamedicaon.model

data class User(
    val nome_completo: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val celular: String,
    val genero: String,
    val data_nascimento: String
)