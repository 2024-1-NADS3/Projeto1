package com.example.mynavigation.ui.adapter;

import android.widget.CheckBox;

public class Tarefa {

    private String nomeTarefa;
    private String dataHora;

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public CheckBox getCheckTarefa() {
        return checkTarefa;
    }

    public void setCheckTarefa(CheckBox checkTarefa) {
        this.checkTarefa = checkTarefa;
    }

    private CheckBox checkTarefa;
}