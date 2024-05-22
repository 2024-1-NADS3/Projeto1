package com.example.mynavigation.ui.home;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynavigation.ClasseUsuarioLogado;
import com.example.mynavigation.R;
import com.example.mynavigation.databinding.FragmentHomeBinding;
import com.example.mynavigation.humor.AppPreferences;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    private int humorUsuario;
    private int idUsuario;
    private FragmentHomeBinding binding;
    private ListView listaTarefas;


    private static final String CITY = "Sao Paulo,br";
    private static final String API = "06c921750b9a82d8f5d1294e1586276f";
    private ProgressBar progressBar;
    private RelativeLayout mainContainer;
    private TextView errorText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBar = root.findViewById(R.id.loader);
        errorText = root.findViewById(R.id.errorText);

        new WeatherTask().execute();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        idUsuario = ClasseUsuarioLogado.getIdUsuarioLogado();
        // Carregar os dados ao entrar na tela
        MyTasks task = new MyTasks();
        String urlApi = "https://vq4x7v-3000.csb.app/obterTarefasAtivas/" + idUsuario;
        task.execute(urlApi);
        buscarHumor(idUsuario);

        listaTarefas = root.findViewById(R.id.listaTarefas);
        ImageView imagemResultado = binding.imagemResultado;

        // Configurando a data atual
        TextView dataHoje = binding.dataHoje;
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String dataAtualText = sdf.format(Calendar.getInstance().getTime());
        dataHoje.setText(dataAtualText);


        //Humor
        binding.malButton.setOnClickListener(v -> opcaoSelecionada("bad"));

        binding.normalButton.setOnClickListener(v -> opcaoSelecionada("normal"));

        binding.felizButton.setOnClickListener(v -> opcaoSelecionada("happy"));

        String estadoAtual = AppPreferences.getInstance(requireContext()).getSelectedOption();
        switch (estadoAtual) {
            case "bad":
                imagemResultado.setImageResource(R.drawable.mal);
                break;
            case "normal":
                imagemResultado.setImageResource(R.drawable.normal);
                break;
            case "happy":
                imagemResultado.setImageResource(R.drawable.feliz);
                break;
        }



        return root;
    }

    public void buscarHumor(int userId) {
        String urlString = "https://vq4x7v-3000.csb.app/buscarHumor";

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                int userHumor = -1;
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Enviando o ID do usuário no corpo da requisição
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", userId);
                    Log.e(TAG, "Erro na solicitação0: " + userId);
                    OutputStream os = connection.getOutputStream();
                    os.write(jsonParams.toString().getBytes("UTF-8"));
                    os.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();

                        // Parse the response to get the humor
                        JSONObject jsonResponse = new JSONObject(content.toString());
                        userHumor = jsonResponse.getInt("humor");
                    } else {
                        Log.e(TAG, "Erro na solicitação1: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Erro na solicitação2: " + e.getMessage());
                }
                return userHumor;
            }

            @Override
            protected void onPostExecute(Integer userHumor) {
                super.onPostExecute(userHumor);
                humorUsuario = userHumor;
            }
        }.execute();
    }

    // Listener para tratar o humor buscado
    public interface OnHumorFetchedListener {
        void onHumorFetched(int humor);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void opcaoSelecionada(String opcaoSelecionada) {
        ImageView imagemResultado = binding.imagemResultado;

        switch (opcaoSelecionada) {
            case "bad":
                imagemResultado.setImageResource(R.drawable.mal);
                break;
            case "normal":
                imagemResultado.setImageResource(R.drawable.normal);
                break;
            case "happy":
                imagemResultado.setImageResource(R.drawable.feliz);
                break;
        }
        AppPreferences.getInstance(requireContext()).setSelectedOption(opcaoSelecionada);
    }

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
            listaTarefas.setAdapter(adaptador);
        }
    }



    //Tempo
    private class WeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            if (mainContainer != null) {
                mainContainer.setVisibility(View.GONE);
            }
            if (errorText != null) {
                errorText.setVisibility(View.GONE);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject main = jsonObj.getJSONObject("main");
                    JSONObject sys = jsonObj.getJSONObject("sys");
                    JSONObject wind = jsonObj.getJSONObject("wind");
                    JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                    String temp = main.getString("temp") + "°C";
                    String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                    String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                    String weatherDescription = weather.getString("description");

                    TextView statusTextView = getView().findViewById(R.id.status);
                    TextView tempTextView = getView().findViewById(R.id.temp);
                    TextView tempMinTextView = getView().findViewById(R.id.temp_min);
                    TextView tempMaxTextView = getView().findViewById(R.id.temp_max);

                    if (statusTextView != null) {
                        statusTextView.setText(capitalize(weatherDescription));
                    }
                    if (tempTextView != null) {
                        tempTextView.setText(temp);
                    }
                    if (tempMinTextView != null) {
                        tempMinTextView.setText(tempMin);
                    }
                    if (tempMaxTextView != null) {
                        tempMaxTextView.setText(tempMax);
                    }

                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    if (mainContainer != null) {
                        mainContainer.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    if (errorText != null) {
                        errorText.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (errorText != null) {
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        }

        private String capitalize(String str) {
            if (str == null || str.length() == 0) {
                return str;
            }
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }


}
