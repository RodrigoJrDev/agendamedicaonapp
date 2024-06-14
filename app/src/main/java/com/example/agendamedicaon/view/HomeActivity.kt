package com.example.agendamedicaon.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.*
import com.example.agendamedicaon.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var spinnerEspecialidades: Spinner
    private lateinit var spinnerMedicos: Spinner
    private lateinit var spinnerHorarios: Spinner
    private lateinit var buttonSolicitarAgendamento: Button
    private lateinit var buttonVerConsultas: Button
    private lateinit var selectedMedico: Medico
    private lateinit var selectedHorario: Horario
    private lateinit var selectedEspecialidade: Especialidade
    private var paciente: Paciente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        paciente = intent.getParcelableExtra("paciente")

        spinnerEspecialidades = findViewById(R.id.spinnerEspecialidades)
        spinnerMedicos = findViewById(R.id.spinnerMedicos)
        spinnerHorarios = findViewById(R.id.spinnerHorarios)
        buttonSolicitarAgendamento = findViewById(R.id.buttonSolicitarAgendamento)
        buttonVerConsultas = findViewById(R.id.buttonVerConsultas)

        loadEspecialidades()

        spinnerEspecialidades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedEspecialidade = parent?.getItemAtPosition(position) as Especialidade
                Log.d("Especialidade", selectedEspecialidade.nome)
                loadMedicos(selectedEspecialidade.id)
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
            solicitarAgendamento(selectedMedico.id, selectedHorario.id, selectedEspecialidade.nome)
        }

        buttonVerConsultas.setOnClickListener {
            val intent = Intent(this@HomeActivity, ConsultasAgendadasActivity::class.java)
            intent.putExtra("idPaciente", paciente?.id)
            startActivity(intent)
        }
    }

    private fun loadEspecialidades() {
        val call = RetrofitClient.apiService.getEspecialidades()

        call.enqueue(object : Callback<List<Especialidade>> {
            override fun onResponse(call: Call<List<Especialidade>>, response: Response<List<Especialidade>>) {
                if (response.isSuccessful) {
                    val especialidades = response.body()
                    Log.d("EspecialidadesResponse", "Recebido: $especialidades")
                    if (!especialidades.isNullOrEmpty()) {
                        val adapter = EspecialidadeAdapter(this@HomeActivity, R.layout.spinner_item, especialidades)
                        spinnerEspecialidades.adapter = adapter
                    } else {
                        Log.e("HomeActivity", "Nenhuma especialidade disponível")
                        Toast.makeText(this@HomeActivity, "Nenhuma especialidade disponível", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("HomeActivity", "Erro ao carregar especialidades: ${response.errorBody()?.string()}")
                    Toast.makeText(this@HomeActivity, "Erro ao carregar especialidades", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Especialidade>>, t: Throwable) {
                Log.e("HomeActivity", "Erro: ${t.message}", t)
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
                    Log.d("MedicosResponse", "Recebido: $medicos")
                    if (!medicos.isNullOrEmpty()) {
                        val adapter = MedicoAdapter(this@HomeActivity, R.layout.spinner_medico_item, medicos)
                        spinnerMedicos.adapter = adapter
                        spinnerMedicos.visibility = View.VISIBLE
                    } else {
                        Log.e("HomeActivity", "Nenhum médico disponível")
                        Toast.makeText(this@HomeActivity, "Nenhum médico disponível", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("HomeActivity", "Erro ao carregar médicos: ${response.errorBody()?.string()}")
                    Toast.makeText(this@HomeActivity, "Erro ao carregar médicos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Medico>>, t: Throwable) {
                Log.e("HomeActivity", "Erro: ${t.message}", t)
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
                    Log.d("HorariosResponse", "Recebido: $horarios")
                    if (!horarios.isNullOrEmpty()) {
                        val adapter = HorarioAdapter(this@HomeActivity, R.layout.spinner_horario_item, horarios)
                        spinnerHorarios.adapter = adapter
                        spinnerHorarios.visibility = View.VISIBLE
                        buttonSolicitarAgendamento.visibility = View.VISIBLE
                    } else {
                        Log.e("HomeActivity", "Nenhum horário disponível")
                        Toast.makeText(this@HomeActivity, "Nenhum horário disponível", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("HomeActivity", "Erro ao carregar horários: ${response.errorBody()?.string()}")
                    Toast.makeText(this@HomeActivity, "Erro ao carregar horários", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Horario>>, t: Throwable) {
                Log.e("HomeActivity", "Erro: ${t.message}", t)
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun solicitarAgendamento(medicoId: Int, horarioId: Int, especialidadeNome: String) {
        val paciente = intent.getParcelableExtra<Paciente>("paciente")
        if (paciente == null) {
            Toast.makeText(this, "Erro: Dados do paciente não encontrados.", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AgendamentoRequest(medicoId, horarioId, paciente.id, especialidadeNome)

        // Adicionando log para verificar o que está sendo enviado para o backend
        Log.d("AgendamentoRequest", "MedicoId: $medicoId, HorarioId: $horarioId, PacienteId: ${paciente.id}, EspecialidadeNome: $especialidadeNome")

        val call = RetrofitClient.apiService.solicitarAgendamento(request)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@HomeActivity, "Obrigado por fazer a solicitação, se o médico aceitar você irá receber um e-mail avisando", Toast.LENGTH_SHORT).show()
                    // Navegar para a Consultas Agendadas
                    val intent = Intent(this@HomeActivity, ConsultasAgendadasActivity::class.java)
                    intent.putExtra("idPaciente", paciente.id)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("HomeActivity", "Erro ao solicitar agendamento: ${response.errorBody()?.string()}")
                    Toast.makeText(this@HomeActivity, "Erro ao solicitar agendamento", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HomeActivity", "Erro: ${t.message}", t)
                Toast.makeText(this@HomeActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
