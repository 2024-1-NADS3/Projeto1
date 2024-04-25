package com.example.mynavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login (View view)
    {
        TextView campoEmail;
        TextView campoSenha;
        String email;
        String senha;

        campoEmail = findViewById(R.id.emailCadastro);
        campoSenha = findViewById(R.id.senhaCadastro);
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();

        campoEmail = findViewById(R.id.emailCadastro);
        campoSenha = findViewById(R.id.senhaCadastro);
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();

        ClasseLogin login1 = new ClasseLogin(email, senha);

        Intent mudarTela = new Intent(getApplicationContext(), MainActivity.class);
        mudarTela.putExtra("objeto", login1);
        startActivity(mudarTela);
    }

    public void BotaoCadastro (View view)
    {
        Intent mudarTela = new Intent(getApplicationContext(), cadastro.class);
        startActivity(mudarTela);
    }
}