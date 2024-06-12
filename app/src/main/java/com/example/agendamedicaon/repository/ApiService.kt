package com.example.agendamedicaon.repository
import com.example.agendamedicaon.model.AgendamentoRequest
import com.example.agendamedicaon.model.User
import com.example.agendamedicaon.model.LoginRequest
import com.example.agendamedicaon.model.AppointmentRequest
import com.example.agendamedicaon.model.Consulta
import com.example.agendamedicaon.model.Paciente
import com.example.agendamedicaon.model.RegisterRequest
import com.example.agendamedicaon.model.Especialidade
import com.example.agendamedicaon.model.Medico
import com.example.agendamedicaon.model.Horario
import com.example.agendamedicaon.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @POST("api/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/requestAppointment")
    fun requestAppointment(@Body appointmentRequest: AppointmentRequest): Call<Void>

    @POST("api/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<Void>

    @GET("api/getEspecialidadesDisponiveis")
    fun getEspecialidades(): Call<List<Especialidade>>

    @GET("api/medicos")
    fun getMedicosByEspecialidade(@Query("especialidadeId") especialidadeId: Int): Call<List<Medico>>

    @GET("api/horarios")
    fun getHorariosDisponiveis(@Query("medicoId") medicoId: Int): Call<List<Horario>>

    @POST("api/solicitar_agendamento")
    fun solicitarAgendamento(@Body request: AgendamentoRequest): Call<Void>

    @GET("api/consultas_agendadas")
    fun getConsultasAgendadas(): Call<List<Consulta>>
}