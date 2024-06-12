package com.example.agendamedicaon.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Medico

class MedicoAdapter(
    context: Context,
    private val resource: Int,
    private val medicos: List<Medico>
) : ArrayAdapter<Medico>(context, resource, medicos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    private fun createViewFromResource(convertView: View?, position: Int, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.textViewMedicoItem)
        textView.text = medicos[position].nome_completo
        return view
    }
}
