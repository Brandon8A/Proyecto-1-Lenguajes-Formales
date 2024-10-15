/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.analizadorlexicojavascript;

import com.mycompany.proyecto1_lf.backend.listaenlazada.ControladorTokenEstado;

/**
 *
 * @author brandon
 */
public class AnalizadorLexicoJs {
    private String textoCodigo;
    private boolean etiquetaDeUnaLinea = false;
    private int posicion;
    private char caracterActual;
    private ControladorTokenEstado controladorTokenEstado;

    public AnalizadorLexicoJs(String textoCodigo, int posicion, ControladorTokenEstado controladorTokenEstado) {
        this.textoCodigo = textoCodigo;
        this.posicion = posicion;
        this.controladorTokenEstado = controladorTokenEstado;
    }
    
    public void analizarCodigoJs(){
        while (caracterActual != '\0' && !cambiarTokenEstado()) {
            avanzarCaracter();
        }
    }
    
    private boolean cambiarTokenEstado(){
        if (textoCodigo.startsWith(">>", posicion)) {
            System.out.println("Cambiar token estado");
            return true;
        } else {
            return false;
        }
    }
    
    public void avanzarCaracter() {
        posicion++;
        if (posicion < textoCodigo.length()) {
            caracterActual = textoCodigo.charAt(posicion);
        } else {
            caracterActual = '\0'; //indicando final del texto
        }
    }
}
