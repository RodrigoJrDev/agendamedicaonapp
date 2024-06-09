// ConsultasAdapter.kt
package com.example.agendamedicaon.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Consulta

class ConsultasAdapter(private val consultasList: List<Consulta>) :
    RecyclerView.Adapter<ConsultasAdapter.ConsultaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consulta, parent, false)
        return ConsultaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val consulta = consultasList[position]
        holder.bind(consulta)
    }

    override fun getItemCount(): Int = consultasList.size

    class ConsultaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewData: TextView = itemView.findViewById(R.id.textViewData)
        private val textViewHora: TextView = itemView.findViewById(R.id.textViewHora)
        private val textViewMedico: TextView = itemView.findViewById(R.id.textViewMedico)
        private val textViewEspecialidade: TextView = itemView.findViewById(R.id.textViewEspecialidade)

        fun bind(consulta: Consulta) {
            textViewData.text = consulta.data
            textViewHora.text = consulta.hora
            textViewMedico.text = consulta.medico
            textViewEspecialidade.text = consulta.especialidade
        }
    }
}
