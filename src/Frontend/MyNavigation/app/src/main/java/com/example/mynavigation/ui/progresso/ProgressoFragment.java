package com.example.mynavigation.ui.progresso;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mynavigation.ClasseUsuarioLogado;
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
import java.util.concurrent.CountDownLatch;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class ProgressoFragment extends Fragment {
    private ProgressBar progressBar;
    private int idUsuario = ClasseUsuarioLogado.getIdUsuarioLogado();
    private TextView textTotalTarefa, textPorcentagem;
    private ListView listaTarefasFeitas;

    private static final String BASE_URL = "https://vq4x7v-3000.csb.app";
    private int total;

    public ProgressoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progresso, container, false);

        // Inicialização das views
        progressBar = view.findViewById(R.id.progressBar2);
        textTotalTarefa = view.findViewById(R.id.textTotalTarefa);
        textPorcentagem = view.findViewById(R.id.textPorcentagem);
        listaTarefasFeitas = view.findViewById(R.id.listaTarefasFeitas);

      
        CountDownLatch latch = new CountDownLatch(1);

        getTotalTasks(idUsuario, latch);

        // Espera pela conclusão da obtenção do total de tarefas
        new Thread(() -> {
            try {
                latch.await();
                // Após obter o total de tarefas, buscar as tarefas concluídas
                getCompletedTasks(idUsuario);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

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

    // Método para obter tarefas concluídas
    private void getCompletedTasks(int idUsuario) {
        String urlApi = BASE_URL + "/obterTarefasFinalizadas/" + idUsuario;
        ProgressoFragment.MyTasks task = new ProgressoFragment.MyTasks();
        task.execute(urlApi);
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
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tarefas);
            listaTarefasFeitas.setAdapter(adaptador);

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

    private void getTotalTasks(int idUsuario, CountDownLatch latch) {
        String url = BASE_URL + "/quantidadeTarefas/" + idUsuario;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            total = response.getInt("total");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown(); // Reduz a contagem do latch
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        latch.countDown(); // Reduz a contagem do latch mesmo em caso de erro
                    }
                });

        Volley.newRequestQueue(getActivity().getApplicationContext()).add(request);
    }
}
