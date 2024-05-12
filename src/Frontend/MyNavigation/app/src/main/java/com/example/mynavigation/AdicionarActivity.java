package com.example.mynavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.text.SimpleDateFormat;
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


public class AdicionarActivity extends AppCompatActivity {
    private EditText nomeTarefa;
    private Button selectDateButton, adicionarTarefa, definirHorarioButton;
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

        //BOTÃO QUE IRÁ ENVIAR PARA TABELA DO SQLlITE
        adicionarTarefa.setOnClickListener(v -> {
            try {
                // Abre ou cria o banco de dados
                SQLiteDatabase bancoDados = openOrCreateDatabase("CheckList", MODE_PRIVATE, null);
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS minhasTarefas2 (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR, data_hora DATETIME)");

                // nome da tarefa
                String novaTarefa = nomeTarefa.getText().toString();
                // data e horário
                String dataHoraTarefa = calendarioTextView.getText().toString() + " " + horarioTextView.getText().toString();

                // Insere na tabela minhasTarefas
                bancoDados.execSQL("INSERT INTO minhasTarefas (tarefa, data_hora) VALUES('" + novaTarefa + "', '" + dataHoraTarefa + "')");

                // Exibindo no log com comando SELECT sql
                Cursor cursor = bancoDados.rawQuery("SELECT * FROM minhasTarefas", null);
                int indiceColunaID = cursor.getColumnIndex("id");
                int indiceColunaTarefa = cursor.getColumnIndex("tarefa");
                int indiceColunaDataHora = cursor.getColumnIndex("data_hora");

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Log.i("Logx", "ID: " + cursor.getString(indiceColunaID) +
                            " - Tarefa: " + cursor.getString(indiceColunaTarefa) +
                            " - Data e Horário: " + cursor.getString(indiceColunaDataHora));
                    cursor.moveToNext();
                }

                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
