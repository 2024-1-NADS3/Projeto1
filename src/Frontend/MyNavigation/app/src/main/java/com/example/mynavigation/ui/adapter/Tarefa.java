package com.example.mynavigation.ui.adapter;

import android.widget.Button;
import android.widget.CheckBox;

public class Tarefa {

    private String nomeTarefa;
    private String dataHora;

    private Button btnFeito;

    public Button getBtnFeito() {
        return btnFeito;
    }

    public void setBtnFeito(Button btnFeito) {
        this.btnFeito = btnFeito;
    }


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