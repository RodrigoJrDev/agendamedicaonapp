// Especialidade.kt
package com.example.agendamedicaon.model

data class Especialidade(
    val id: Int,
    val nome: String,
    val id_medico: Int
) {
    override fun toString(): String {
        return nome
    }
}
