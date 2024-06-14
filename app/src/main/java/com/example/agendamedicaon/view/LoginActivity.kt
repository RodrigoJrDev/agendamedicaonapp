package com.example.agendamedicaon.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.LoginRequest
import com.example.agendamedicaon.model.LoginResponse
import com.example.agendamedicaon.repository.RetrofitClient
import com.example.agendamedicaon.repository.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Utilizando a instância apiService do RetrofitClient
        apiService = RetrofitClient.apiService

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        Log.d("LoginActivity", "LoginRequest: email = $email, password = $password")

        val call = apiService.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginActivity", "Response code: ${response.code()}")
                Log.d("LoginActivity", "Response body: ${response.body()?.toString()}")
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.status == "success") {
                        val paciente = loginResponse.data
                        Toast.makeText(this@LoginActivity, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                        // Navegar para a HomeActivity
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("paciente", paciente)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Falha no login: ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Falha no login", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
