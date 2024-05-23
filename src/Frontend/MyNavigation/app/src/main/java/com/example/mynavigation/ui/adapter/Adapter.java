package com.example.mynavigation.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynavigation.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Tarefa> listaTarefas;

    public Adapter(ArrayList<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = listaTarefas.get(position);
        holder.textNomeTarefa.setText(tarefa.getNomeTarefa());
        holder.textDataHora.setText(tarefa.getDataHora());
        // Configurar o CheckBox, se necessário
    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textDataHora, textNomeTarefa;
        private CheckBox checkTarefa;
        private Button btnFeito;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textNomeTarefa = itemView.findViewById(R.id.textNomeTarefa);
            textDataHora = itemView.findViewById(R.id.textDataHora);
            btnFeito = itemView.findViewById(R.id.btnFeito);
        }
    }
}