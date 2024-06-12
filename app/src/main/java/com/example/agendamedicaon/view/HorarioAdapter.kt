package com.example.agendamedicaon.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Horario

class HorarioAdapter(
    context: Context,
    private val resource: Int,
    private val horarios: List<Horario>
) : ArrayAdapter<Horario>(context, resource, horarios) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    private fun createViewFromResource(convertView: View?, position: Int, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.textViewHorarioItem)
        val horario = horarios[position]
        textView.text = "${horario.data_disponivel} - ${horario.hora_inicio} às ${horario.hora_fim}"
        return view
    }
}
