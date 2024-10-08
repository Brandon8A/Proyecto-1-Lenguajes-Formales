/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.analizadorlexicohtml;

import com.mycompany.proyecto1_lf.backend.listaenlazada.ListaEnlazada;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brandon
 */
public class AnalizadorLexicoHtml {

    private ListaEnlazada listaTokens = new ListaEnlazada();
    private String textoCodigo;
    private boolean etiquetaDeUnaLinea = false;
    private int posicion;
    private char caracterActual;
    private final String[] etiquetas = {"principal", "encabezado", "navegacion", "apartado", "listaordenada",
        "listadesordenada", "itemlista", "anclaje", "contenedor", "seccion", "articulo", "titulo", "parrafo", "span",
        "entrada", "formulario", "label", "area", "boton", "piepagina"};
    private final String[] palabrasReservadas = {"class", "=", "href", "onClick", "id", "style", "type",
        "placeholder", "required", "name"};

    public AnalizadorLexicoHtml(String textoCodigo, int posicion) {
        this.textoCodigo = textoCodigo;
        this.posicion = posicion;
        this.caracterActual = this.textoCodigo.charAt(posicion);//obteniendo caracter de la cadena en la posicion madada por parametro
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
                    listaTokens.agregarElemento(new Token("</"));
                    avanzarCaracter();//avanza el caracter '<'
                    avanzarCaracter();//avanza el caracter '/'
                    identificarTipoEtiqueta();//Identifica el tipo de etiqueta
                    avanzarEspaciosEnBlanco();
                    if (caracterActual == '>') {
                        listaTokens.agregarElemento(new Token(">"));
                        System.out.println("Fin de la etiqueta de CIERRE");
                        avanzarEspaciosEnBlanco();
                        avanzarCaracter();
                    }
                } else {
                    esEtiqueta = true;
                    listaTokens.agregarElemento(new Token("<"));
                    avanzarCaracter();//avanza el caracter '<'
                    identificarTipoEtiqueta();//Identifica el tipo de etiqueta
                    avanzarEspaciosEnBlanco();
                    while (caracterActual != '>' && caracterActual != '\0' && caracterActual != '/') {
                        identificarPalabrasReservadas();
                    }
                    if (!etiquetaDeUnaLinea) {
                        if (caracterActual == '>') {
                            listaTokens.agregarElemento(new Token(">"));
                            System.out.println("Fin de la etiqueta de APERTURA");
                            avanzarEspaciosEnBlanco();
                            avanzarCaracter();
                        }
                        avanzarEspaciosEnBlanco();
                        identificarTexto();
                    } else {
                        if (caracterActual ==  '/') {
                            listaTokens.agregarElemento(new Token("/>"));
                            avanzarCaracter();//avanza el caracter '/'
                            avanzarCaracter();//avanza el caracter '>'
                        }
                    }
                }
            } else {
                System.out.println("No es etiqueta");
            }
        }
        System.out.println("El tamaño de la lista de tokens es: " + listaTokens.getTamaño());
        for (int i = 0; i < listaTokens.getTamaño(); i++) {
            System.out.print(listaTokens.obtenerValor(i).getLexema());
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
                listaTokens.agregarElemento(new Token(traducirEtiqueta(etiquetas[i])));
                if (resultado.toString().equals("area") || resultado.toString().equals("entrada")) {
                    etiquetaDeUnaLinea = true;
                } else {
                    etiquetaDeUnaLinea = false;
                }
                if (caracterActual == ' ') {
                    listaTokens.agregarElemento(new Token(" "));
                }
                break;
            }
        }
    }

    /**
     * Funcion que permite la traduccion de las etiquetas del codigo fuente
     *
     * @param etiquetaATraducir recibe el texto de la etiqueta que se desea
     * traducir
     * @return devuelve en texto la etiqueta ya traducida
     */
    private String traducirEtiqueta(String etiquetaATraducir) {
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
     * Metodo que avanza los todos los espacios en blanco hasta encontrar un
     * caracter diferente de espacio en blanco
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
                listaTokens.agregarElemento(new Token(palabrasReservadas[i]));
                break;
            }
        }
        if (!saltarSignoIgual) {
            if (caracterActual == '=') {
                listaTokens.agregarElemento(new Token("="));
                System.out.println("Palabra reservada es: '='");
                avanzarCaracter();
                avanzarEspaciosEnBlanco();
                if (caracterActual == '"') {
                    listaTokens.agregarElemento(new Token(String.valueOf(caracterActual)));
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
        listaTokens.agregarElemento(new Token(resultado.toString()));
        listaTokens.agregarElemento(new Token(String.valueOf(caracterActual)));
        avanzarCaracter();//avanzar caracter '"'
        avanzarEspaciosEnBlanco();
        if (caracterActual != '>') {
            listaTokens.agregarElemento(new Token(" "));
        }
        System.out.println("LA CADENA ES: " + resultado.toString());
        avanzarEspaciosEnBlanco();
    }

    /**
     * Identifica el texto que se encuentra entre una etiqueta de apertura,
     * afuera de las propiedades de las propiedades de esta etiqueta de
     * apertura, y afuera de una etiqueta de cierre
     */
    private void identificarTexto() {
        StringBuilder texto = new StringBuilder();
        while (caracterActual != '<' && caracterActual != '\0') {
            texto.append(caracterActual);
            avanzarCaracter();
        }
        listaTokens.agregarElemento(new Token(texto.toString()));
        System.out.println("El texto entre ambas etiquetas (apertura y cierre) es: " + texto.toString());
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
