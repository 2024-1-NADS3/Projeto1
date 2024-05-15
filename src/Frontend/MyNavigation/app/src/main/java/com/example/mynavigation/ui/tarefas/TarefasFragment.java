package com.example.mynavigation.ui.tarefas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.mynavigation.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TarefasFragment extends Fragment {

    private ListView listaTarefas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);

        listaTarefas = view.findViewById(R.id.listaTarefas);

        // Parse do JSON e extrair as tarefas
        String json = "{ \"tasksList\": [ { \"_id\": \"663d090d6f0b63e80fc58903\", \"task\": \"Estudar Muito Node\", \"date\": \"2024-05-09T17:34:05.226Z\", \"__v\": 0 }," +
                " { \"_id\": \"663d21c7207389c71e3c6ff8\", \"task\": \"Ir para a academia\", \"date\": \"2024-05-09T19:19:35.647Z\", \"__v\": 0 }," +
                " { \"_id\": \"663d21dd207389c71e3c6ffb\", \"task\": \"Comprar comida para o gato\", \"date\": \"2024-05-09T19:19:57.861Z\", \"__v\": 0 }," +
                " { \"_id\": \"663d23820a799d815e71a318\", \"task\": \"TESTE\", \"date\": \"2024-05-09T19:26:58.345Z\", \"__v\": 0 }, " +
                "{ \"_id\": \"663d29058165bf19dbbbbc31\", \"task\": \"TESTE deletar 2\", \"date\": \"2024-05-09T19:50:29.862Z\", \"__v\": 0 }, " +
                "{ \"_id\": \"663d5dc3b84c0514bdbf997e\", \"task\": \"TESTE deletar 10\", \"date\": \"2024-05-09T23:35:31.167Z\", \"__v\": 0 } ] }";
        ArrayList<String> tarefas = parseJSON(json);

        // Atualizar o ArrayAdapter
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tarefas);
        listaTarefas.setAdapter(adaptador);

        return view;
    }

    // Método para analisar o JSON e extrair as tarefas
    private ArrayList<String> parseJSON(String json) {
        ArrayList<String> tarefas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray tasksList = jsonObject.getJSONArray("tasksList");
            for (int i = 0; i < tasksList.length(); i++) {
                JSONObject taskObj = tasksList.getJSONObject(i);
                String task = taskObj.getString("task");
                tarefas.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tarefas;
    }


    /*
    new Thread(new Runnable() {
        @Override
        public void run() {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://todolistmongodb-1.onrender.com");
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                String json = readStream(in); // Implemente este método para converter o InputStream em String
                ArrayList<String> tarefas = parseJSON(json); // Use o método parseJSON que você já tem

                // Atualize a ListView na thread principal
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tarefas);
                        listaTarefas.setAdapter(adaptador);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }).start(); */
}
