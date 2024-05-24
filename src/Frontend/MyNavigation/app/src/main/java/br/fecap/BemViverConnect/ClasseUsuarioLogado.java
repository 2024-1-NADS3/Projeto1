package br.fecap.BemViverConnect;

import java.io.Serializable;

public class ClasseUsuarioLogado implements Serializable {

    protected static int idUsuarioLogado;

    public ClasseUsuarioLogado(int idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    static public void setIdUsuarioLogado(int IdUsuarioLogado) {
        idUsuarioLogado = IdUsuarioLogado;
    }

    public static int getIdUsuarioLogado() {
        return idUsuarioLogado;
    }
}
