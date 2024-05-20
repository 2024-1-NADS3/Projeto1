package com.example.mynavigation.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mynavigation.R;
import com.example.mynavigation.databinding.FragmentHomeBinding;
import com.example.mynavigation.humor.AppPreferences;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



    public class HomeFragment extends Fragment {

        private FragmentHomeBinding binding;
        private ListView listaTarefas;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

            listaTarefas = root.findViewById(R.id.listaTarefas);

            // Carregar os dados ao entrar na tela
            MyTasks task = new MyTasks();
            String urlApi = "https://vq4x7v-3000.csb.app/buscarTarefas";
            task.execute(urlApi);

            // Configurando a data atual
            TextView dataHoje = binding.dataHoje;
            SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
            String dataAtualText = sdf.format(Calendar.getInstance().getTime());
            dataHoje.setText(dataAtualText);

            binding.malButton.setOnClickListener(v -> opcaoSelecionada("bad"));

            binding.normalButton.setOnClickListener(v -> opcaoSelecionada("normal"));

            binding.felizButton.setOnClickListener(v -> opcaoSelecionada("happy"));

            ImageView imagemResultado = binding.imagemResultado;
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

                // Atualização da ListView com o novo adaptador
                listaTarefas.setAdapter(adaptador);
            }
        }
    }
