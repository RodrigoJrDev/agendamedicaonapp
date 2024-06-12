package com.example.agendamedicaon.model

data class RegisterRequest(
    val nomeCompleto: String,
    val email: String,
    val cpf: String,
    val celular: String,
    val genero: String,
    val dataNascimento: String,
    val cep: String,
    val rua: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val complemento: String?,
    val senha: String
)
