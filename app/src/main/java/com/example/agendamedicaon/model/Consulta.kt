package com.example.agendamedicaon.model

import com.google.gson.annotations.SerializedName

data class Consulta(
    val id: Int,
    @SerializedName("id_medico") val idMedico: Int,
    @SerializedName("id_paciente") val idPaciente: Int,
    @SerializedName("id_status") val idStatus: Int,
    @SerializedName("id_especialidade") val idEspecialidade: Int,
    @SerializedName("data_consulta") val dataConsulta: String,
    val observacoes: String?,
    val status: String,
    @SerializedName("nome_medico") val nomeMedico: String,
    @SerializedName("nome_especialidade") val nomeEspecialidade: String
)
