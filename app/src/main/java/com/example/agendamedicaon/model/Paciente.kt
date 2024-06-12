package com.example.agendamedicaon.model

import android.os.Parcel
import android.os.Parcelable

data class Paciente(
    val id: Int,
    val nome: String,
    val email: String,
    val genero: String,
    val data_nascimento: String,
    val imagem_usuario: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nome)
        parcel.writeString(email)
        parcel.writeString(genero)
        parcel.writeString(data_nascimento)
        parcel.writeString(imagem_usuario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Paciente> {
        override fun createFromParcel(parcel: Parcel): Paciente {
            return Paciente(parcel)
        }

        override fun newArray(size: Int): Array<Paciente?> {
            return arrayOfNulls(size)
        }
    }
}
