package com.example.mynavigation.ui.tarefas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynavigation.R;
import com.example.mynavigation.ui.adapter.Adapter;
import com.example.mynavigation.ui.adapter.Tarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TarefasFragment extends Fragment {

    private ListView listaTarefas;
    private Button buttonRecuperar;

    private Adapter adapter;

    private RecyclerView listaTarefas2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);

        listaTarefas2 = view.findViewById(R.id.listaTarefas2);


        adapter = new Adapter(new ArrayList<>());
        listaTarefas2.setAdapter(adapter);

        //Configurando p RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listaTarefas2.setLayoutManager(layoutManager);
        listaTarefas2.setHasFixedSize(true);




        // Carregar o ao entrar na tela
        MyTasks task = new MyTasks();
        String urlApi = "https://vq4x7v-3000.csb.app/buscarTarefas";
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
                Tarefa tarefa = new Tarefa();
                tarefa.setNomeTarefa(nomeTarefa);
                tarefa.setDataHora(dataHora);
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




        }
    }
}
