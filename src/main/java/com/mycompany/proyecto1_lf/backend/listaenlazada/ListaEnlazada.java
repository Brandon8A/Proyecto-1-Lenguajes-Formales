/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.listaenlazada;

import com.mycompany.proyecto1_lf.backend.analizadorlexicohtml.Token;

/**
 *
 * @author brandon
 */
public class ListaEnlazada {
    private Nodo inicio;
    private Nodo fin;
    private int tamaño = 0;
    
    public void agregarElemento(Token elemento){
        Nodo nuevo = new Nodo(elemento);
        if (estaVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamaño++;
    }
    
    public boolean estaVacia(){
        return inicio == null;
    }
    
    public Token obtenerValor(int indice){
        return obtenerNodo(indice).getValor();
    }
    
    private Nodo obtenerNodo(int indice){
        Nodo actual = inicio;
        for (int i = 0; i < indice; i++) {
            Nodo siguiente = actual.getSiguiente();
            actual = siguiente;
        }
        return actual;
    }

    public Nodo getInicio() {
        return inicio;
    }

    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
    
}
