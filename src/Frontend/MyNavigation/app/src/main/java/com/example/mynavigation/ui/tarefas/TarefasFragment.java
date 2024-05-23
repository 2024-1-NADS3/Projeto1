package com.example.mynavigation.ui.tarefas;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynavigation.ClasseUsuarioLogado;
import com.example.mynavigation.R;
import com.example.mynavigation.humor.AppPreferences;
import com.example.mynavigation.ui.adapter.Adapter;
import com.example.mynavigation.ui.adapter.Tarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TarefasFragment extends Fragment {

    private ListView listaTarefas;
    private int idUsuario = ClasseUsuarioLogado.getIdUsuarioLogado();
    private Adapter adapter;
    private RecyclerView listaTarefas2;
    private ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        listaTarefas2 = view.findViewById(R.id.listaTarefas2);

        adapter = new Adapter(new ArrayList<>());
        listaTarefas2.setAdapter(adapter);

        //Configurando p RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listaTarefas2.setLayoutManager(layoutManager);
        listaTarefas2.setHasFixedSize(true);

        // Carregar o ao entrar na tela
        MyTasks task = new MyTasks();
        String urlApi = "https://vq4x7v-3000.csb.app/obterTarefasAtivas/" + idUsuario;
        task.execute(urlApi);

        return view;
    }

    //recycler
    private ArrayList<Tarefa> parseJSON(String json) {
        ArrayList<Tarefa> listaDeTarefas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nomeTarefa = jsonObject.getString("nome_tarefa");
                String dataHora = jsonObject.getString("data_hora");
                Integer id = jsonObject.getInt("id");
                String dataHoraSubstituido = dataHora.replace("-", "/");
                Tarefa tarefa = new Tarefa();
                tarefa.setNomeTarefa(nomeTarefa);
                tarefa.setDataHora(dataHoraSubstituido);
                tarefa.setId(id);
                listaDeTarefas.add(tarefa);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaDeTarefas;
    }

    class MyTasks extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conexao.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            // Parse do resultado JSON para uma lista de tarefas
            ArrayList<Tarefa> tarefas2 = parseJSON(resultado);

            // Parse do resultado JSON para uma lista de tarefas
            //ArrayList<String> tarefas = parseJSON(resultado);

            Adapter adapter = new Adapter(tarefas2);
            listaTarefas2.setAdapter(adapter);

            //Fim do carregamento
            progressBar.setVisibility(View.GONE);


        }
    }

    private void mudarStatus() {
        String urlString = "https://vq4x7v-3000.csb.app/atualizarStatus/" + 5 + "/" + 0;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = null;
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");

                    OutputStream os = connection.getOutputStream();
                    os.flush();
                    os.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(() -> Toast.makeText(context, "Status atualizado com sucesso", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(context, "Erro ao atualizar status", Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(context, "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
                return null;
            }

            private void runOnUiThread(Runnable runnable) {
            }
        }.execute();
    }
}