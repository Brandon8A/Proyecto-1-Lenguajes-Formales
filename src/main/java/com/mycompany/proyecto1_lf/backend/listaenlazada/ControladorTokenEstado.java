/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.listaenlazada;

import com.mycompany.proyecto1_lf.backend.ArchivoHTML;
import com.mycompany.proyecto1_lf.backend.analizadorlexicohtml.AnalizadorLexicoHtml;

/**
 *
 * @author brandon
 */
public class ControladorTokenEstado {
    private final String TOKEN_ESTADO_HTML="html";
    private final String TOKEN_ESTADO_CSS="css";
    private final String TOKEN_ESTADO_JAVASCRIPT="js";
    
    private String textoCodigo;
    private int posicion;
    private char caracterActual;
    private ListaEnlazada listaTokensHTML = new ListaEnlazada();
    private ListaEnlazada listaEnlazadaCSS = new ListaEnlazada();
    private ListaEnlazada listaEnlazadaJS = new ListaEnlazada();

    public ControladorTokenEstado(String texto) {
        this.textoCodigo = texto;
        this.posicion = 0;
        this.caracterActual = this.textoCodigo.charAt(posicion);
        analizarTokenEstado();
    }
    
    private void analizarTokenEstado(){
        String tokenEstado;
        while (caracterActual != '\0') {            
            if (Character.isWhitespace(caracterActual)) {
                avanzarCaracter();
            }else if (caracterActual == '>') {
                if (textoCodigo.startsWith(">>", posicion)) {
                    avanzarCaracter();//avanza caracter '>'
                    avanzarCaracter();//avanza caracter '>'
                    avanzarCaracter();//avanza caracter '['
                    tokenEstado = identificarTokenEstado();
                    if (tokenEstado.equals(TOKEN_ESTADO_HTML)) {
                        System.out.println("El token estado es: HTML");
                        AnalizadorLexicoHtml analizadorLexicoHtml = new AnalizadorLexicoHtml(textoCodigo, posicion, this);
                        analizadorLexicoHtml.esEtiqueta();
                        System.out.println("caracter actual: "+caracterActual);
                    }else if (tokenEstado.equals(TOKEN_ESTADO_CSS)) {
                        System.out.println("El token estado es: CSS");
                    } else {
                        System.out.println("El token estado es: JS");
                    }
//                    if (caracterActual == '\n') {
//                        System.out.println("Hay un salto de linea");
//                        for (int i = 0; i < 5; i++) {
//                            System.out.print(caracterActual + "HOLA");
//                        }
//                        avanzarCaracter();
//                        if (caracterActual == '\t') {
//                            int cantidadTabulaciones = 0;
//                            while (caracterActual != '\0') {                                
//                                if (caracterActual == '\t') {
//                                    cantidadTabulaciones++;
//                                    avanzarCaracter();
//                                }
//                            }
//                            System.out.println("Tabulaciones: "+cantidadTabulaciones);
//                        }
//                    }else{
//                        System.out.println("No hay salto de linea");
//                    }
                } else {
                    avanzarCaracter();
                    System.out.println("NO hay doble signo menor");
                }
            } else {
            }
        }
        ArchivoHTML archivoHTML = new ArchivoHTML(this);
        archivoHTML.escribirCodigoHTML(listaTokensHTML);
    }
    
    /**
     * Funcion que permite verificar el tipo de token de estado el cual se va a analizar para su traduccion
     * @return retorna el nombre del lenguaje a analizar
     */
    private String identificarTokenEstado(){
        StringBuilder texto = new StringBuilder();
        while (caracterActual != ']' && caracterActual != ' ' && caracterActual != '\0') {            
            texto.append(caracterActual);
            avanzarCaracter();
        }
        avanzarCaracter();//avanza el caracter ']'
        return texto.toString();
    }
    
    public void avanzarCaracter() {
        posicion++;
        if (posicion < textoCodigo.length()) {
            caracterActual = textoCodigo.charAt(posicion);
        } else {
            caracterActual = '\0'; //indicando final del texto
        }
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public char getCaracterActual() {
        return caracterActual;
    }

    public void setCaracterActual(char caracterActual) {
        this.caracterActual = caracterActual;
    }

    public ListaEnlazada getListaTokensHTML() {
        return listaTokensHTML;
    }

    public ListaEnlazada getListaEnlazadaCSS() {
        return listaEnlazadaCSS;
    }

    public ListaEnlazada getListaEnlazadaJS() {
        return listaEnlazadaJS;
    }
    
}
