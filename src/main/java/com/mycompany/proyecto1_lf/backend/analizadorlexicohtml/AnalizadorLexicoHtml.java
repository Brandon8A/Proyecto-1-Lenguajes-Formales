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
    private final String[] etiquetas = {"principal", "encabezado", "navegacion", "apartado", "listaordenada", 
        "listadesordenada", "itemlista", "anclaje", "contenedor", "seccion", "articulo", "titulo", "parrafo", "span",
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
                    avanzarEspaciosEnBlanco();
                    if (caracterActual == '>' ) {
                        tokens.add(new Token(">"));
                        System.out.println("Fin de la etiqueta de CIERRE");
                    }
                } else {
                    esEtiqueta = true;
                    tokens.add(new Token("<"));
                    avanzarCaracter();//avanza el caracter '<'
                    identificarTipoEtiqueta();//Identifica el tipo de etiqueta
                    avanzarEspaciosEnBlanco();
                    while (caracterActual != '>' && caracterActual != '\0') {
                        identificarPalabrasReservadas();
                    }
                    if (caracterActual == '>' ) {
                        tokens.add(new Token(">"));
                        System.out.println("Fin de la etiqueta de APERTURA");
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
                tokens.add(new Token(traducirEtiqueta(etiquetas[i])));
                tokens.add(new Token(" "));
                break;
            }
        }
    }

    /**
     * Funcion que permite la traduccion de las etiquetas del codigo fuente
     * @param etiquetaATraducir recibe el texto de la etiqueta que se desea traducir
     * @return devuelve en texto la etiqueta ya traducida
     */
    private String traducirEtiqueta(String etiquetaATraducir){
        String etiquetaTraducida = "";
        switch (etiquetaATraducir) {
            case "prinicipal":
                etiquetaTraducida = "main";
                break;
            case "encabezado":
                etiquetaTraducida = "header";
                break;
            case "navegacion":
                etiquetaTraducida = "nav";
                break;
            case "apartado":
                etiquetaTraducida = "aside";
                break;
            case "listaordenada":
                etiquetaTraducida = "ul";
                break;
            case "listadesordenada":
                etiquetaTraducida = "ol";
                break;
            case "itemlista":
                etiquetaTraducida = "li";
                break;
            case "anclaje":
                etiquetaTraducida = "a";
                break;
            case "contenedor":
                etiquetaTraducida = "div";
                break;
            case "seccion":
                etiquetaTraducida = "section";
                break;
            case "articulo":
                etiquetaTraducida = "article";
                break;
            case "titulo":
                etiquetaTraducida = "h";
                break;
            case "parrafo":
                etiquetaTraducida = "p";
                break;
            case "span":
                etiquetaTraducida = "span";
                break;
            case "entrada":
                etiquetaTraducida = "input";
                break;
            case "formulario":
                etiquetaTraducida = "form";
                break;
            case "label":
                etiquetaTraducida = "label";
                break;
            case "area":
                etiquetaTraducida = "textarea";
                break;
            case "boton":
                etiquetaTraducida = "button";
                break;
            case "piepagina":
                etiquetaTraducida = "footer";
                break;
            
        }
        return etiquetaTraducida;
    }
    
    /**
     * Metodo que avanza los todos los espacios en blanco hasta encontrar un caracter diferente de espacio en blanco
     */
    private void avanzarEspaciosEnBlanco() {
        while (caracterActual == ' ') {
            avanzarCaracter();
        }
    }

    /**
     * Metodo que permite identificar las palabras reservadas
     */
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
                    tokens.add(new Token(String.valueOf(caracterActual)));
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
        tokens.add(new Token(resultado.toString()));
        avanzarCaracter();//avanzar caracter '"'
        System.out.println("LA CADENA ES: " + resultado.toString());
        avanzarEspaciosEnBlanco();
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
