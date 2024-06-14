package com.example.agendamedicaon.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Consulta
import com.example.agendamedicaon.repository.ApiService
import com.example.agendamedicaon.repository.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultasAgendadasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ApiService
    private var idPaciente: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultas_agendadas)

        Log.d("ConsultasAgendadas", "onCreate chamado")

        recyclerView = findViewById(R.id.recyclerViewConsultas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        apiService = RetrofitClient.apiService

        idPaciente = intent.getIntExtra("idPaciente", -1)
        if (idPaciente == -1) {
            Toast.makeText(this, "Erro: ID do paciente não encontrado.", Toast.LENGTH_SHORT).show()
            return
        }

        getConsultasAgendadas()
    }

    private fun getConsultasAgendadas() {
        val call = apiService.getConsultasAgendadas(idPaciente!!)
        Log.d("ConsultasAgendadas", "Chamando API para obter consultas agendadas")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.string()?.let { jsonResponse ->
                        Log.d("ConsultasAgendadas", "Resposta da API recebida com sucesso: $jsonResponse")
                        val jsonObject = JSONObject(jsonResponse)
                        val status = jsonObject.getString("status")

                        if (status == "success") {
                            val consultasJsonArray = jsonObject.getJSONArray("data")
                            val gson = Gson()
                            val consultasListType = object : TypeToken<List<Consulta>>() {}.type
                            val consultasList: List<Consulta> = gson.fromJson(consultasJsonArray.toString(), consultasListType)
                            recyclerView.adapter = ConsultasAdapter(consultasList)
                        } else {
                            val message = jsonObject.getString("message")
                            Log.d("ConsultasAgendadas", "Falha ao carregar consultas: $message")
                            Toast.makeText(this@ConsultasAgendadasActivity, "Falha ao carregar consultas: $message", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.d("ConsultasAgendadas", "Falha ao carregar consultas: ${response.errorBody()}")
                    Toast.makeText(this@ConsultasAgendadasActivity, "Falha ao carregar consultas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ConsultasAgendadas", "Erro ao chamar API: ${t.message}")
                Toast.makeText(this@ConsultasAgendadasActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
