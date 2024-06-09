package com.example.agendamedicaon.model

data class RegisterRequest(
    val nome_completo: String,
    val email: String,
    val cpf: String,
    val celular: String,
    val genero: String,
    val data_nascimento: String,
    val cep: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val complemento: String?
)
