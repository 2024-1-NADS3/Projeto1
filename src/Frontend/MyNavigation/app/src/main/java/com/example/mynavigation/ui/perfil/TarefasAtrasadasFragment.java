package com.example.mynavigation.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mynavigation.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TarefasAtrasadasFragment extends Fragment {

    private static final String CITY = "Sao Paulo,br";
    private static final String API = "06c921750b9a82d8f5d1294e1586276f";
    private ProgressBar progressBar;
    private RelativeLayout mainContainer;
    private TextView errorText;

    public TarefasAtrasadasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefasatrasadas, container, false);

        progressBar = view.findViewById(R.id.loader);
        errorText = view.findViewById(R.id.errorText);

        new WeatherTask().execute();

        return view;
    }

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
