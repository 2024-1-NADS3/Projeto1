package com.example.mynavigation.ui.perfil;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynavigation.ClasseUsuarioLogado;
import com.example.mynavigation.R;
import com.example.mynavigation.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PerfilFragment extends Fragment {

    public PerfilFragment() {
        // Required empty public constructor
    }

    private int idUsuario = ClasseUsuarioLogado.getIdUsuarioLogado();
    private TextView nome;
    private TextView email;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        nome = view.findViewById(R.id.nomeText);
        email = view.findViewById(R.id.emailText);

        buscarDados(11);

        return view;
    }

    public void buscarDados(Integer id) {

        String urlString = "https://vq4x7v-3000.csb.app/dadosUsuario/" + id;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        inputStream.close();

                        JSONArray jsonArray = new JSONArray(response.toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0); // Assume que há apenas um objeto no array
                        String nomeUsuario = jsonObject.getString("nome");
                        String emailUsuario = jsonObject.getString("email");

// Atualizar as TextViews com os dados do usuário
                        runOnUiThread(() -> {
                            nome.setText(nomeUsuario);
                            email.setText(emailUsuario);
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao buscar dados", Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(getContext(), "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    Log.e(TAG, "Erro: " + e.getMessage());
                }

                return null;
            }

            private void runOnUiThread(Runnable runnable) {
                getActivity().runOnUiThread(runnable);
            }
        }.execute();
    }

}
