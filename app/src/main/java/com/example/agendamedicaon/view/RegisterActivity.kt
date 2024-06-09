package com.example.agendamedicaon.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agendamedicaon.R
import com.example.agendamedicaon.model.RegisterRequest
import com.example.agendamedicaon.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextNomeCompleto: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextCpf: EditText
    private lateinit var editTextCelular: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var editTextCep: EditText
    private lateinit var editTextRua: EditText
    private lateinit var editTextNumero: EditText
    private lateinit var editTextBairro: EditText
    private lateinit var editTextCidade: EditText
    private lateinit var editTextComplemento: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextNomeCompleto = findViewById(R.id.editTextNomeCompleto)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextCpf = findViewById(R.id.editTextCpf)
        editTextCelular = findViewById(R.id.editTextCelular)
        editTextGenero = findViewById(R.id.editTextGenero)
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento)
        editTextCep = findViewById(R.id.editTextCep)
        editTextRua = findViewById(R.id.editTextRua)
        editTextNumero = findViewById(R.id.editTextNumero)
        editTextBairro = findViewById(R.id.editTextBairro)
        editTextCidade = findViewById(R.id.editTextCidade)
        editTextComplemento = findViewById(R.id.editTextComplemento)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val nomeCompleto = editTextNomeCompleto.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val cpf = editTextCpf.text.toString().trim()
            val celular = editTextCelular.text.toString().trim()
            val genero = editTextGenero.text.toString().trim()
            val dataNascimento = editTextDataNascimento.text.toString().trim()
            val cep = editTextCep.text.toString().trim()
            val rua = editTextRua.text.toString().trim()
            val numero = editTextNumero.text.toString().trim()
            val bairro = editTextBairro.text.toString().trim()
            val cidade = editTextCidade.text.toString().trim()
            val complemento = editTextComplemento.text.toString().trim()

            if (nomeCompleto.isNotEmpty() && email.isNotEmpty() && cpf.isNotEmpty() && celular.isNotEmpty() && genero.isNotEmpty() &&
                dataNascimento.isNotEmpty() && cep.isNotEmpty() && rua.isNotEmpty() && numero.isNotEmpty() && bairro.isNotEmpty() &&
                cidade.isNotEmpty()
            ) {
                registerUser(
                    nomeCompleto, email, cpf, celular, genero, dataNascimento, cep, rua, numero, bairro, cidade, complemento
                )
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(
        nomeCompleto: String, email: String, cpf: String, celular: String, genero: String,
        dataNascimento: String, cep: String, rua: String, numero: String, bairro: String,
        cidade: String, complemento: String?
    ) {
        val registerRequest = RegisterRequest(
            nomeCompleto, email, cpf, celular, genero, dataNascimento, cep, rua, numero, bairro, cidade, complemento
        )
        val call = RetrofitClient.apiService.registerUser(registerRequest)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
                    // TODO: Navegar para a próxima tela ou realizar login automático
                } else {
                    Toast.makeText(this@RegisterActivity, "Falha no cadastro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
