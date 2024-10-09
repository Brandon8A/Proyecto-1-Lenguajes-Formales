/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.listaenlazada;

import com.mycompany.proyecto1_lf.backend.Token;

/**
 *
 * @author brandon
 */
public class Nodo {
    private Nodo anterior;
    private Nodo siguiente;
    private Token valor;

    public Nodo(Token valor) {
        this.valor = valor;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Token getValor() {
        return valor;
    }

    public void setValor(Token valor) {
        this.valor = valor;
    }
    
    
}
