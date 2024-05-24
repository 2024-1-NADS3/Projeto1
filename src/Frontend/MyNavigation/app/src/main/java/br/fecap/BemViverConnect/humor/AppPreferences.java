package br.fecap.BemViverConnect.humor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppPreferences {
    private int humor;
    private static final String PREFS_NAME = "MyAppPrefs"; // Nome do arquivo de preferências
    private static final String KEY_SELECTED_OPTION = "selected_option"; // Chave para a opção selecionada

    private static AppPreferences instance;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context.getApplicationContext());
        }
        return instance;
    }

    public void setSelectedOption(String option) {
                sharedPreferences.edit().putString(KEY_SELECTED_OPTION, option).apply();
                if (option == "bad"){
                    humor = 0;
                }
                else if (option == "normal") {
                    humor = 1;
                }
                else {
                    humor = 2;
        }
        updateHumorInDatabase(11, humor);
    }

    private void updateHumorInDatabase(int userId, int humor) {
        String urlString = "https://qy5dwc-3000.csb.app/atualizarUsuario/" + userId + "/" + humor;

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
                        runOnUiThread(() -> Toast.makeText(context, "Humor atualizado com sucesso", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(context, "Erro ao atualizar humor", Toast.LENGTH_SHORT).show());
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

    public String getSelectedOption() {
        return sharedPreferences.getString(KEY_SELECTED_OPTION, "normal");
    }
}

