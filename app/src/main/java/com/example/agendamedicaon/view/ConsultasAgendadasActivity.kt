package com.example.agendamedicaon.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Consulta
import com.example.agendamedicaon.repository.RetrofitClient
import com.example.agendamedicaon.repository.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultasAgendadasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultas_agendadas)

        recyclerView = findViewById(R.id.recyclerViewConsultas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        apiService = RetrofitClient.apiService

        getConsultasAgendadas()
    }

    private fun getConsultasAgendadas() {
        val call = apiService.getConsultasAgendadas()
        call.enqueue(object : Callback<List<Consulta>> {
            override fun onResponse(call: Call<List<Consulta>>, response: Response<List<Consulta>>) {
                if (response.isSuccessful) {
                    val consultasList = response.body()
                    recyclerView.adapter = consultasList?.let { ConsultasAdapter(it) }
                } else {
                    Toast.makeText(this@ConsultasAgendadasActivity, "Falha ao carregar consultas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Consulta>>, t: Throwable) {
                Toast.makeText(this@ConsultasAgendadasActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
