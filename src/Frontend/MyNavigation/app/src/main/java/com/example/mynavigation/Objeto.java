package com.example.mynavigation;

import java.io.Serializable;

public class Objeto implements Serializable {

    public String nomeObj;
    public String emailObj;
    public String senhaObj;


    public Objeto()
    {
        this.nomeObj = "teste";
        this.emailObj = "teste";
        this.senhaObj = "teste";
    }

}
