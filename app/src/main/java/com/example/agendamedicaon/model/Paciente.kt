package com.example.agendamedicaon.model

import com.google.gson.annotations.SerializedName

data class Paciente(
    @SerializedName("id") val id: Int,
    @SerializedName("nome_completo") val nomeCompleto: String,
    @SerializedName("email") val email: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("celular") val celular: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("data_nascimento") val dataNascimento: String,
    @SerializedName("cep") val cep: String,
    @SerializedName("rua") val rua: String,
    @SerializedName("numero") val numero: String,
    @SerializedName("bairro") val bairro: String,
    @SerializedName("cidade") val cidade: String,
    @SerializedName("complemento") val complemento: String?,
    @SerializedName("imagem_usuario") val imagemUsuario: String?
)
