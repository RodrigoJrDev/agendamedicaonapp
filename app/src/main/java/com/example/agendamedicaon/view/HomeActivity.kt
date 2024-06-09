// HomeActivity.kt
package com.example.agendamedicaon.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.Especialidade
import com.example.agendamedicaon.model.Medico
import com.example.agendamedicaon.model.Horario
import com.example.agendamedicaon.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var spinnerEspecialidades: Spinner
    private lateinit var spinnerMedicos: Spinner
    private lateinit var spinnerHorarios: Spinner
    private lateinit var buttonSolicitarAgendamento: Button
    private lateinit var selectedMedico: Medico
    private lateinit var selectedHorario: Horario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        spinnerEspecialidades = findViewById(R.id.spinnerEspecialidades)
        spinnerMedicos = findViewById(R.id.spinnerMedicos)
        spinnerHorarios = findViewById(R.id.spinnerHorarios)
        buttonSolicitarAgendamento = findViewById(R.id.buttonSolicitarAgendamento)

        loadEspecialidades()

        spinnerEspecialidades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val especialidade = parent?.getItemAtPosition(position) as Especialidade
                loadMedicos(especialidade.id)
            }
        }

        spinnerMedicos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMedico = parent?.getItemAtPosition(position) as Medico
                loadHorarios(selectedMedico.id)
            }
        }

        spinnerHorarios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedHorario = parent?.getItemAtPosition(position) as Horario
            }
        }

        buttonSolicitarAgendamento.setOnClickListener {
            solicitarAgendamento(selectedMedico.id, selectedHorario.id)
        }
    }

    private fun loadEspecialidades() {
        val call = RetrofitClient.apiService.getEspecialidades()

        call.enqueue(object : Callback<List<Especialidade>> {
            override fun onResponse(call: Call<List<Especialidade>>, response: Response<List<Especialidade>>) {
                if (response.isSuccessful) {
                    val especialidades = response.body()
                    val adapter = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_item, especialidades!!)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEspecialidades.adapter = adapter
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao carregar especialidades", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Especialidade>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadMedicos(especialidadeId: Int) {
        val call = RetrofitClient.apiService.getMedicosByEspecialidade(especialidadeId)

        call.enqueue(object : Callback<List<Medico>> {
            override fun onResponse(call: Call<List<Medico>>, response: Response<List<Medico>>) {
                if (response.isSuccessful) {
                    val medicos = response.body()
                    val adapter = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_item, medicos!!)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMedicos.adapter = adapter
                    spinnerMedicos.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao carregar médicos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Medico>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadHorarios(medicoId: Int) {
        val call = RetrofitClient.apiService.getHorariosDisponiveis(medicoId)

        call.enqueue(object : Callback<List<Horario>> {
            override fun onResponse(call: Call<List<Horario>>, response: Response<List<Horario>>) {
                if (response.isSuccessful) {
                    val horarios = response.body()
                    val adapter = ArrayAdapter(this@HomeActivity, android.R.layout.simple_spinner_item, horarios!!)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerHorarios.adapter = adapter
                    spinnerHorarios.visibility = View.VISIBLE
                    buttonSolicitarAgendamento.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao carregar horários", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Horario>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun solicitarAgendamento(medicoId: Int, horarioId: Int) {
        val call = RetrofitClient.apiService.solicitarAgendamento(medicoId, horarioId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@HomeActivity, "Obrigado por fazer a solicitação, se o médico aceitar você irá receber um e-mail avisando", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HomeActivity, "Erro ao solicitar agendamento", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
