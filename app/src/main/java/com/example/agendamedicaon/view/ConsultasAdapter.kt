package com.example.agendamedicaon.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Consulta

class ConsultasAdapter(private val consultasList: List<Consulta>) : RecyclerView.Adapter<ConsultasAdapter.ConsultasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_consulta, parent, false)
        return ConsultasViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultasViewHolder, position: Int) {
        val consulta = consultasList[position]
        holder.bind(consulta)
    }

    override fun getItemCount(): Int {
        return consultasList.size
    }

    class ConsultasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dataConsultaTextView: TextView = itemView.findViewById(R.id.textViewData)
        private val statusTextView: TextView = itemView.findViewById(R.id.textViewStatus)
        private val nomeMedicoTextView: TextView = itemView.findViewById(R.id.textViewNomeMedico)
        private val nomeEspecialidadeTextView: TextView = itemView.findViewById(R.id.textViewNomeEspecialidade)

        fun bind(consulta: Consulta) {
            Log.d("ConsultasAdapter", "Bind dataConsulta: ${consulta.dataConsulta}, status: ${consulta.status}, nomeMedico: ${consulta.nomeMedico}, nomeEspecialidade: ${consulta.nomeEspecialidade}")

            dataConsultaTextView.text = consulta.dataConsulta
            statusTextView.text = consulta.status
            nomeMedicoTextView.text = consulta.nomeMedico
            nomeEspecialidadeTextView.text = consulta.nomeEspecialidade
        }
    }
}
