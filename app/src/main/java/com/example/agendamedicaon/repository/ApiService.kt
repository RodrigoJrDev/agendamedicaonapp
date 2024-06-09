package com.example.agendamedicaon.repository
import com.example.agendamedicaon.model.User
import com.example.agendamedicaon.model.LoginRequest
import com.example.agendamedicaon.model.AppointmentRequest
import com.example.agendamedicaon.model.Consulta
import com.example.agendamedicaon.model.Paciente
import com.example.agendamedicaon.model.RegisterRequest
import com.example.agendamedicaon.model.Especialidade
import com.example.agendamedicaon.model.Medico
import com.example.agendamedicaon.model.Horario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @POST("api/register")
    fun registerUser(@Body user: User): Call<Void>

    @POST("api/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<Paciente>

    @POST("api/requestAppointment")
    fun requestAppointment(@Body appointmentRequest: AppointmentRequest): Call<Void>

    @POST("register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<Void>

    @GET("api/especialidades")
    fun getEspecialidades(): Call<List<Especialidade>>

    @GET("api/medicos")
    fun getMedicosByEspecialidade(@Query("especialidadeId") especialidadeId: Int): Call<List<Medico>>

    @GET("api/horarios")
    fun getHorariosDisponiveis(@Query("medicoId") medicoId: Int): Call<List<Horario>>

    @POST("api/solicitar_agendamento")
    fun solicitarAgendamento(@Query("medicoId") medicoId: Int, @Query("horarioId") horarioId: Int): Call<Void>

    @GET("api/consultas_agendadas")
    fun getConsultasAgendadas(): Call<List<Consulta>>
}