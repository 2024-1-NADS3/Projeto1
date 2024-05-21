package com.example.mynavigation.ui.progresso;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.ArrayAdapter;

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


public class ProgressoFragment extends Fragment {
    private ProgressBar progressBar;
    private Button buttonIncreaseProgress;
    private TextView textView9;
    private TextView textTotalTarefa, textPorcentagem;
    private ListView listaTarefasFeitas;

    private int idUsuario = 1;
    private int progress = 0;
    private int total = 5;


    public ProgressoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progresso, container, false);


        // Barra de progresso
        progressBar = view.findViewById(R.id.progressBar2);
        textTotalTarefa = view.findViewById(R.id.textTotalTarefa);
        textPorcentagem = view.findViewById(R.id.textPorcentagem);



        //Trazendo lista de tarefas
        listaTarefasFeitas = view.findViewById(R.id.listaTarefasFeitas);

        TextView textView9 = view.findViewById(R.id.textView9);
        ProgressoFragment.MyTasks task = new ProgressoFragment.MyTasks();
        String urlApi = "https://vq4x7v-3000.csb.app/obterTarefasAtivas/" + idUsuario;
        task.execute(urlApi);

        return view;
    }

    private void applyGradientToText(TextView textView, int startColor, int endColor) {
        Shader textShader = new LinearGradient(0, 0, 0, textView.getTextSize(),
                new int[]{startColor, endColor},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

        // Aplicando gradiente ao texto dos TextViews
        applyGradientToText(textView9, 0x206AC1, 0x43BC9F);}




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
            ArrayList<String> tarefas = parseJSON(resultado);
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1,
                    tarefas);

            // Atualização da ListView com adaptador
            listaTarefasFeitas.setAdapter(adaptador);

            //Definindo total de tarefas para o progresso e passando como parametro
            int feitas = adaptador.getCount();
            updateProgress(total, feitas);
        }


        private void updateProgress(int total, int feitas) {
            textTotalTarefa.setText(feitas + "/" + total);

            // Calcula a porcentagem
            int porcentagem = (int) ((feitas / (double) total) * 100);
            textPorcentagem.setText(porcentagem + "%");

            // Atualiza a barra de progresso
            progressBar.setProgress(porcentagem);
        }
    }
}