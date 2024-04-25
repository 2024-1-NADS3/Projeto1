package com.example.mynavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.Response;
//import com.android.volley.Request;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;

public class cadastro extends AppCompatActivity {

    String url="https://9nflln-3000.csb.app/tudo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
//        filaRequest = Volley.newRequestQueue(this);
//        dadoNome = findViewById(R.id.nome);
//        dadoEmail = findViewById(R.id.emailCadastro);
//        dadoSenha = findViewById(R.id.senhaCadastro);
//        Intent intent = getIntent();
//        String dadoCadastro = intent.getStringExtra("dados");
//        dadoNome.setText(dadoCadastro);
//        dadoEmail.setText(dadoCadastro);
//        dadoSenha.setText(dadoCadastro);
//        Objeto obj = (Objeto)intent.getSerializableExtra("obj");
//        System.out.println("Aqui1: "+obj.nomeObj);
//        System.out.println("Aqui1: "+obj.emailObj);
//        System.out.println("Aqui1: "+obj.senhaObj);
    }

//    public void GetData(View view){
//        System.out.println("Entrei Aqui!");
//        nome = findViewById(R.id.nome);
//        email = findViewById(R.id.emailCadastro);
//        senha = findViewById(R.id.senhaCadastro);
//
//        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if (response.length() > 0) {
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject jsonObj = response.getJSONObject(i);
//                                    String nomeJ = jsonObj.get("nome").toString();
//                                    String emailJ = jsonObj.get("email").toString();
//                                    String senhaJ = jsonObj.get("senha").toString();
//                                    Log.d("Dados ","Nome: " +nomeJ +" E-mail: "+emailJ+" Senha: "+senhaJ);
//                                    nome.setText(nomeJ);
//                                    email.setText(emailJ);
//                                    senha.setText(senhaJ);
//                                } catch (JSONException e) {
//                                    Log.e("Volley", "Erro no JSON");
//                                    //System.out.println("Erro no JSON");
//                                }
//
//                            }
//                        } else {
//                            Log.e("Data","Sem Dados");
//                            //System.out.println("Sem Dados");
//                        }
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley", error.toString());
//                        //System.out.println(error.toString());
//                    }
//                });
//        filaRequest.add(arrReq);
//    }

    public void Cadastrar(View view){
        TextView campoNome;
        TextView campoEmail;
        TextView campoSenha;
        String nome;
        String email;
        String senha;

        campoNome = findViewById(R.id.nome);
        campoEmail = findViewById(R.id.emailCadastro);
        campoSenha = findViewById(R.id.senhaCadastro);
        nome = campoNome.getText().toString();
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();

        campoNome = findViewById(R.id.nome);
        campoEmail = findViewById(R.id.emailCadastro);
        campoSenha = findViewById(R.id.senhaCadastro);
        nome = campoNome.getText().toString();
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();

        ClasseCadastro cadastro1 = new ClasseCadastro(nome, email, senha);

        Intent mudarTela2 = new Intent(getApplicationContext(), login.class);
        mudarTela2.putExtra("objeto1", (CharSequence) cadastro1);
        startActivity(mudarTela2);

    }

    public void BotaoLogin (View view)
    {
        Intent mudarTela = new Intent(getApplicationContext(), login.class);
        startActivity(mudarTela);
    }
}