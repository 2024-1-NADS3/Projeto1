package com.example.mynavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarActivity extends AppCompatActivity {
    private EditText nomeTarefa;
    private Button selectDateButton, definirHorarioButton, adicionarTarefa;
    private Calendar calendar;

    //horario
    private TextView horarioTextView, calendarioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeTarefa = findViewById(R.id.nomeTarefa);
        selectDateButton = findViewById(R.id.selecionarData);
        definirHorarioButton = findViewById(R.id.definirHorario);
        adicionarTarefa = findViewById(R.id.adicionaTarefa);
        calendarioTextView = findViewById(R.id.calendarioTextView);
        horarioTextView = findViewById(R.id.horarioTextView);

        calendar = Calendar.getInstance();

        // um dialog de data limite
        selectDateButton.setOnClickListener(v -> {
            //captura os dados selecionados de forma separada
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AdicionarActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year1);
                        calendar.set(Calendar.MONTH, month1);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());
                        Toast.makeText(AdicionarActivity.this, "Data selecionada: " + selectedDate, Toast.LENGTH_SHORT).show();
                        calendarioTextView.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // um dialog de horário
        definirHorarioButton.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AdicionarActivity.this,
                    (view, hourOfDay, minute1) -> {
                        // Aqui você pode lidar com o horário definido pelo usuário
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute1);

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String selectedTime = sdf.format(calendar.getTime());
                        Toast.makeText(AdicionarActivity.this, "Horário selecionado: " + selectedTime, Toast.LENGTH_SHORT).show();
                        horarioTextView.setText(selectedTime);
                    },
                    hour,
                    minute,
                    true
            );

            timePickerDialog.show();
        });
    }

        public void Cadastrar(View view){
            String nome = nomeTarefa.getText().toString();
            Integer idUsuario = 1;

            // Verificar se o campo de nome da tarefa está vazio
            if (nome.isEmpty()) {
                Toast.makeText(this, "O campo de nome da tarefa não pode estar vazio.", Toast.LENGTH_SHORT).show();
                return; // Interrompe o processo de cadastro se o campo estiver vazio
            }

            //captura os dados selecionados de forma separada
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String dataHorario = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";

            registrarTarefaNoServidor(nome, idUsuario, dataHorario);
        }

    @SuppressLint("StaticFieldLeak")
    private void registrarTarefaNoServidor(String nome, Integer idUsuario, String dataHorario) {
        String url = "https://vq4x7v-3000.csb.app/cadastrarTarefa";
        JSONObject jsonParams = new JSONObject();
        try {
            Log.d("CLIENTE", "Nome: " + nome);
            Log.d("CLIENTE", "ID do usuário logado: " + idUsuario);
            Log.d("CLIENTE", "Data e horário: " + dataHorario);
            jsonParams.put("nome_tarefa", nome);
            jsonParams.put("id_usuario", idUsuario);
            jsonParams.put("data_hora", dataHorario);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(jsonParams.toString().getBytes());

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        runOnUiThread(() -> {
                            Toast.makeText(AdicionarActivity.this, "Tarefa registrada com sucesso!", Toast.LENGTH_SHORT).show();
                            nomeTarefa.setText("");
                            selectDateButton.setText("");
                            definirHorarioButton.setText("");
                            adicionarTarefa.setText("");
                            horarioTextView.setText("");
                            calendarioTextView.setText("");
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        runOnUiThread(() -> Toast.makeText(AdicionarActivity.this, "Erro: Esta tarefa já está registrada.", Toast.LENGTH_SHORT).show());
                    } else {
                        runOnUiThread(() -> Toast.makeText(AdicionarActivity.this, "Erro ao registrar tarefa. " + nome + idUsuario + dataHorario, Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(AdicionarActivity.this, "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
                return null;
            }
        }.execute();

        Intent mudarTela = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mudarTela);
    }

}
