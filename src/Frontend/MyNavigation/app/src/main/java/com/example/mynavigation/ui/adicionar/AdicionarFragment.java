package com.example.mynavigation.ui.adicionar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynavigation.R;

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
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AdicionarFragment extends Fragment {
    //Calendario
    private EditText nomeTarefa;
    private Button selectDateButton, adicionarTarefa, definirHorarioButton;
    private Calendar calendar;

    //horario
    private TextView horarioTextView , calendarioTextView;
    public AdicionarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_adicionar, container, false);
        View view = inflater.inflate(R.layout.fragment_adicionar, container, false);


        nomeTarefa = view.findViewById(R.id.nomeTarefa);
        selectDateButton = view.findViewById(R.id.selecionarData);
        definirHorarioButton = view.findViewById(R.id.definirHorario);
        adicionarTarefa = view.findViewById(R.id.adicionaTarefa);
        calendarioTextView = view.findViewById(R.id.calendarioTextView);
        horarioTextView = view.findViewById(R.id.horarioTextView);

        calendar = Calendar.getInstance();

        // um dialog de data limite
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //captura os dados selecionados de forma separada
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog( requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String selectedDate = sdf.format(calendar.getTime());
                                Toast.makeText(getActivity(),"Data selecionada:" + selectedDate,Toast.LENGTH_SHORT).show();
                                calendarioTextView.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        // um dialog de horário
        definirHorarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Aqui você pode lidar com o horário definido pelo usuário
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                String selectedTime = sdf.format(calendar.getTime());
                                Toast.makeText(getActivity(), "Horário selecionado: " + selectedTime, Toast.LENGTH_SHORT).show();
                                horarioTextView.setText(selectedTime);
                            }
                        },
                        hour,
                        minute,
                        true
                );

                timePickerDialog.show();
            }
        });




        //BOTÃO QUE IRÁ ENVIAR PARA TABELA DO SQLlITE
        adicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // fragmento context para abrir database
                    SQLiteDatabase bancoDados = getActivity().openOrCreateDatabase("CheckList", getActivity().MODE_PRIVATE, null);
                    bancoDados.execSQL("CREATE TABLE IF NOT EXISTS minhasTarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR, data VARCHAR, horario VARCHAR)");

                    // nome da tarefa
                    String novaTarefa = nomeTarefa.getText().toString();
                    // data
                    String dataTarefa = calendarioTextView.getText().toString();
                    // tempo
                    String horarioTarefa = horarioTextView.getText().toString();

                    // Insert into the minhasTarefas table
                    bancoDados.execSQL("INSERT INTO minhasTarefas (tarefa, data, horario) VALUES('" + novaTarefa + "', '" + dataTarefa + "', '" + horarioTarefa + "')");

                    // Exibindo no log com comando SELECT sql
                    Cursor cursor = bancoDados.rawQuery("SELECT * FROM minhasTarefas", null);
                    int indiceColunaID = cursor.getColumnIndex("id");
                    int indiceColunaTarefa = cursor.getColumnIndex("tarefa");
                    int indiceColunaData = cursor.getColumnIndex("data");
                    int indiceColunaHorario = cursor.getColumnIndex("horario");

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        Log.i("Logx", "ID: " + cursor.getString(indiceColunaID) +
                                " - Tarefa: " + cursor.getString(indiceColunaTarefa) +
                                " - Data: " + cursor.getString(indiceColunaData) +
                                " - Horário: " + cursor.getString(indiceColunaHorario));
                        cursor.moveToNext();
                    }

                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return view;


    }




}