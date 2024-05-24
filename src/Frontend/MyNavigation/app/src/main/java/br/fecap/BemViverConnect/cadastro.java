package br.fecap.BemViverConnect;

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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.mynavigation.R;

public class cadastro extends AppCompatActivity {

    private EditText dadoNome;
    private EditText dadoEmail;
    private EditText dadoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dadoNome = findViewById(R.id.nome);
        dadoEmail = findViewById(R.id.emailCadastro);
        dadoSenha = findViewById(R.id.senhaCadastro);
    }

    public void BotaoLogin (View view)
    {
        Intent mudarTela = new Intent(getApplicationContext(), login.class);
        startActivity(mudarTela);
    }

    public void Cadastrar(View view){
        String nome = dadoNome.getText().toString();
        String email = dadoEmail.getText().toString();
        String senha = dadoSenha.getText().toString();
        Integer humor = 1;

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        registrarUsuarioNoServidor(nome, email, senha, humor);
    }

    @SuppressLint("StaticFieldLeak")
    private void registrarUsuarioNoServidor(String nome, String email, String senha, Integer humor) {
        String url = "https://qy5dwc-3000.csb.app/cadastrarUsuario";
        JSONObject jsonParams = new JSONObject();
        try {
            Log.d("CLIENTE", "Nome: " + nome);
            Log.d("CLIENTE", "Email: " + email);
            Log.d("CLIENTE", "Senha: " + senha);
            Log.d("CLIENTE", "Humor: " + humor);
            jsonParams.put("nome", nome);
            jsonParams.put("email", email);
            jsonParams.put("senha", senha);
            jsonParams.put("humor", humor);
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
                            Toast.makeText(cadastro.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            dadoNome.setText("");
                            dadoEmail.setText("");
                            dadoSenha.setText("");
                        });
                        Intent mudarTela = new Intent(getApplicationContext(), login.class);
                        startActivity(mudarTela);
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        runOnUiThread(() -> Toast.makeText(cadastro.this, "Erro: Este e-mail já está registrado.", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(cadastro.this, "Erro ao registrar usuário", Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(cadastro.this, "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
                return null;
            }
        }.execute();
    }
}