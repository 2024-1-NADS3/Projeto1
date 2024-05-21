package com.example.mynavigation.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

    private int idUsuario = ClasseUsuarioLogado.getIdUsuarioLogado();
    private FragmentHomeBinding binding;
    private ListView listaTarefas;
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    String CHANNEL_ID = "meu_canal";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        listaTarefas = root.findViewById(R.id.listaTarefas);
        ImageView imagemResultado = binding.imagemResultado;

        // Carregar os dados ao entrar na tela
        MyTasks task = new MyTasks();
        String urlApi = "https://vq4x7v-3000.csb.app/obterTarefasAtivas/" + idUsuario;
        task.execute(urlApi);

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

        Button btnNoti = root.findViewById(R.id.btnNot);
        btnNoti.setOnClickListener(v -> {
            // Verificar permissão antes de criar a notificação
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
            } else {
                // Cria a notificação
                makeNotification();
            }
        });

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

            // Atualização da ListView com adaptador
            listaTarefas.setAdapter(adaptador);
        }
    }

    public void makeNotification() {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.alert_tarefa_24)
                .setContentTitle("Lembre-se de fazer suas tarefas")
                .setContentText("Isso é uma notificação de exemplo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Criação do canal de notificação
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Task Notification";
            String description = "Channel for task notifications";
            int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_MIN);
            channel.setDescription(description);

            // Registra o canal no sistema; não pode ser alterado depois que está registrado
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
