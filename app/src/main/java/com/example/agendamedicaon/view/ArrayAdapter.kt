package com.example.agendamedicaon.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Especialidade

class EspecialidadeAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val especialidades: List<Especialidade>
) : ArrayAdapter<Especialidade>(context, layoutResource, especialidades) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, position, parent)
    }

    private fun createViewFromResource(convertView: View?, position: Int, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(layoutResource, parent, false)
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        textView?.text = especialidades[position].nome
        view.tag = especialidades[position].id
        return view
    }
}
