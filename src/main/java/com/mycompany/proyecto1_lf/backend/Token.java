/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend;

/**
 *
 * @author brandon
 */
public class Token {
    private String lexema;
    private String token;
    private String expresionRegular;
    private String lenguaje;
    private String tipo;

    public Token(String lexema, String token, String expresionRegular, String lenguaje, String tipo) {
        this.lexema = lexema;
        this.token = token;
        this.expresionRegular = expresionRegular;
        this.lenguaje = lenguaje;
        this.tipo = tipo;
    }

    
    
    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
