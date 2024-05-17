package com.example.mynavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class login extends AppCompatActivity {
    private EditText dadoEmail;
    private EditText dadoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dadoEmail = findViewById(R.id.emailCadastro);
        dadoSenha = findViewById(R.id.senhaCadastro);
    }

    public void Login(View view){
        String email = dadoEmail.getText().toString();
        String senha = dadoSenha.getText().toString();
        Integer humor = 1;

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        fazerLogin(email, senha);
    }

    @SuppressLint("StaticFieldLeak")
    private void fazerLogin(String email, String senha) {
        String url = "https://vq4x7v-3000.csb.app/login";
        JSONObject jsonParams = new JSONObject();
        try {
            Log.d("CLIENTE", "Email: " + email);
            Log.d("CLIENTE", "Senha: " + senha);
            jsonParams.put("email", email);
            jsonParams.put("senha", senha);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(jsonParams.toString().getBytes());

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        runOnUiThread(() -> {
                            Toast.makeText(login.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            dadoEmail.setText("");
                            dadoSenha.setText("");
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        runOnUiThread(() -> Toast.makeText(login.this, "Erro: Este e-mail já está registrado.", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(login.this, "Erro ao registrar usuário", Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(login.this, "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
                return null;
            }
        }.execute();
    }


    public void BotaoCadastro(View view) {
        Intent mudarTela = new Intent(getApplicationContext(), cadastro.class);
        startActivity(mudarTela);
    }
}
