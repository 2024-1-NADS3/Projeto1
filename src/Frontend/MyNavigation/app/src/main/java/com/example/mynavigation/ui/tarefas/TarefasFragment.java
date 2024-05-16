package com.example.mynavigation.ui.tarefas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.mynavigation.R;


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
    private TextView itemTarefas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);

        listaTarefas = view.findViewById(R.id.listaTarefas);
        buttonRecuperar = view.findViewById(R.id.buttonRecuperar);
        itemTarefas = view.findViewById(R.id.textView5);

        buttonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTasks task = new MyTasks();
                String urlApi = "https://vq4x7v-3000.csb.app/buscarTarefas";
                task.execute(urlApi);
            }
        });

            return view;
        }


    // Método para fazer o parse do JSON e extrair os nomes das tarefas
    private ArrayList<String> parseJSON(String json) {
        ArrayList<String> listaDeTarefas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nomeTarefa = jsonObject.getString("nome_tarefa");
                listaDeTarefas.add(nomeTarefa);
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
            ArrayList<String> tarefas = parseJSON(resultado);

            // Criação de um novo ArrayAdapter com os dados recebidos
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    tarefas
            );

            // Atualização da ListView com o novo adaptador
            listaTarefas.setAdapter(adaptador);
        }
    }



}



