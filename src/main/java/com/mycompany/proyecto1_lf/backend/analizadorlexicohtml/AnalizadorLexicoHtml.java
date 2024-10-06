/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.analizadorlexicohtml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brandon
 */
public class AnalizadorLexicoHtml {

    private List<Token> tokens = new ArrayList<>();
    ;
    private String textoCodigo;
    private int posicion;
    private char caracterActual;
    private final String[] etiquetas = {"principal", "encabezado", "navegacion", "apartado", "listaordenada", "listadesordenada",
        "itemlista", "anclaje", "contenedor", "seccion", "articulo", "titulo", "parrafo", "span",
        "entrada", "formulario", "label", "area", "boton", "piepagina"};
    private final String[] palabrasReservadas = {"class", "=", "href", "onClick", "id", "style", "type",
        "placeholder", "required", "name"};

    public AnalizadorLexicoHtml(String textoCodigo) {
        this.textoCodigo = textoCodigo;
        posicion = 0;
        this.caracterActual = this.textoCodigo.charAt(posicion);//obteniendo caracter de la cadena en la posicion 0
    }

    /**
     * Funcion que permite verificar si el caracter a evaluar es una etiqueta
     * html
     *
     * @return verdadero si es etiqueta de lo contrario devuelve falso(no es
     * etiqueta)
     */
    public boolean esEtiqueta() {
        boolean esEtiqueta = false;
        while (caracterActual != '\0') {
            if (Character.isWhitespace(caracterActual)) {
                avanzarCaracter();
            } else if (caracterActual == '<') {
                if (textoCodigo.startsWith("</", posicion)) {//condicion que verifica si la cadena comienza con el prefijo </
                    esEtiqueta = true;
                    tokens.add(new Token("</"));
                    avanzarCaracter();//avanza el caracter '<'
                    avanzarCaracter();//avanza el caracter '/'
                    identificarTipoEtiqueta();//Identifica el tipo de etiqueta
                } else {
                    esEtiqueta = true;
                    tokens.add(new Token("<"));
                    avanzarCaracter();//avanza el caracter '<'
                    identificarTipoEtiqueta();//Identifica el tipo de etiqueta
                    avanzarEspaciosEnBlanco();
                    while (caracterActual != '>' && caracterActual != '\0') {
                        identificarPalabrasReservadas();
                    }
                }
            } else {
                System.out.println("No es etiqueta");
            }
        }
        return esEtiqueta;
    }

    /**
     * Metodo que verifica el tipo de etiqueta
     */
    private void identificarTipoEtiqueta() {
        StringBuilder resultado = new StringBuilder();
        while (caracterActual != '>' && caracterActual != ' ' && caracterActual != '\0') {
            resultado.append(caracterActual);
            avanzarCaracter();
        }
        for (int i = 0; i < etiquetas.length; i++) {
            if (resultado.toString().equals(etiquetas[i])) {
                System.out.println("La etiqueta es: " + etiquetas[i]);
                break;
            }
        }
    }

    /**
     * Metodo que avanza los todos los espacios en blanco hasta encontrar un caracter diferente de espacio en blanco
     */
    private void avanzarEspaciosEnBlanco() {
        while (caracterActual == ' ') {
            avanzarCaracter();
        }
    }

    private void identificarPalabrasReservadas() {
        boolean saltarSignoIgual = true;
        StringBuilder resultado = new StringBuilder();
        avanzarEspaciosEnBlanco();
        if (caracterActual == '=') {
            saltarSignoIgual = false;
        }
        while (caracterActual != '=' && caracterActual != ' ' && caracterActual != '\0' && caracterActual != '>') {
            resultado.append(caracterActual);
            avanzarCaracter();
        }
        for (int i = 0; i < palabrasReservadas.length; i++) {
            if (resultado.toString().equals(palabrasReservadas[i])) {
                System.out.println("Palabra reservada: " + palabrasReservadas[i]);
                tokens.add(new Token(palabrasReservadas[i]));
                break;
            }
        }
        if (!saltarSignoIgual) {
            if (caracterActual == '=') {
                tokens.add(new Token("="));
                System.out.println("Palabra reservada es: '='");
                avanzarCaracter();
                avanzarEspaciosEnBlanco();
                if (caracterActual == '"') {
                    avanzarCaracter();
                    almacenarCadena();
                }
            }
        }
    }

    private void almacenarCadena() {
        StringBuilder resultado = new StringBuilder();
        while (caracterActual != '"' && caracterActual != '\0') {
            resultado.append(caracterActual);
            avanzarCaracter();
        }
        avanzarCaracter();//avanzar caracter '"'
        System.out.println("La cadena es: " + resultado.toString());
        avanzarEspaciosEnBlanco();
    }

    public boolean hayMasPalabrasReservadas() {
        StringBuilder resultado = new StringBuilder();
        int posicionAntesDeAnalizar = posicion;
        boolean existenPalabrasReservadas = false;
        while (caracterActual != '=' && caracterActual != ' ' && caracterActual != '\0') {
            resultado.append(caracterActual);
            avanzarCaracter();
        }
        for (int i = 0; i < palabrasReservadas.length; i++) {
            if (resultado.toString().equals(palabrasReservadas[i])) {
                System.out.println("Palabra reservada: " + palabrasReservadas[i]);
                existenPalabrasReservadas = true;
                posicion = posicionAntesDeAnalizar;
                break;
            } else {
                existenPalabrasReservadas = false;
            }
        }
        return existenPalabrasReservadas;
    }

    /**
     * Metodo que permite avanzar de caracter en caracter de un texto
     */
    public void avanzarCaracter() {
        posicion++;
        if (posicion < textoCodigo.length()) {
            caracterActual = textoCodigo.charAt(posicion);
        } else {
            caracterActual = '\0'; //indicando final del texto
        }
    }
}
