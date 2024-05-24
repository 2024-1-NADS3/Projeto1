package br.fecap.BemViverConnect;

import java.io.Serializable;

public class ClasseLogin implements Serializable {

    protected String email;
    protected String senha;

    public ClasseLogin(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
