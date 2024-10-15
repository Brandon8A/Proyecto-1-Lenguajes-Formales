/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend.analizadorlexicocss;

import com.mycompany.proyecto1_lf.backend.Token;
import com.mycompany.proyecto1_lf.backend.listaenlazada.ControladorTokenEstado;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author brandon
 */
public class AnalizadorLexicoCss {

    private final String EXPRESION_REGULAR_SELECTOR_CLASE = "^\\.[a-z][a-z0-9-]*$";
    private final String EXPRESION_REGULAR_SELECTOR_ID = "^\\#[a-z][a-z0-9-]*$";
    private final String EXPRESION_REGULAR_COLOR_HEXADECIMAL = "#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})";
    private final String EXPRESION_REGULAR_IDENTIFICADOR = "[a-z]+[0-9]*(-([a-z]|[0-9])+)*";

    private String textoCodigo;
    private int posicion;
    private char caracterActual;
    private ControladorTokenEstado controladorTokenEstado;
    private final String[] selectores = {
        "body", "header", "main", "nav", "aside", "div", "ul", "ol", "li", "a", "p", "span", "label", "textarea", "button",
        "section", "article", "footer"
    };
    private final String[] combinadores = {
        ">", "+", "~", " "
    };
    private final String[] reglas = {
        "color", "background-color", "background", "font-size", "font-weight", "font-family", "font-align", "width",
        "height", "min-width", "min-height", "max-width", "max-height", "display", "inline", "block", "inline-block",
        "flex", "grid", "header", "none", "margin", "border", "padding", "content", "border-color", "border-style",
        "border-width", "border-top", "border-bottom", "border-left", "border-right", "box-sizing", "border-box",
        "position", "static", "relative", "absolute", "sticky", "fixed", "top", "bottom", "left", "right", "z-index",
        "justify-content", "align-items", "border-radius", "auto", "float", "list-style", "text-align", "box-shadow"
    };
    private final String[] otros = {
        "px", "v", "rem", "em", "vw", "vh", ":hover", ":active", ":not()", ":nth-child()", "odd", "even", "::before",
        "::after", ":", ";", ",", "'[A-Za-z]'", "(", ")",};

    public AnalizadorLexicoCss(String textoCodigo, int posicion, ControladorTokenEstado controladorTokenEstado) {
        this.textoCodigo = textoCodigo;
        this.posicion = posicion;
        this.controladorTokenEstado = controladorTokenEstado;
        this.caracterActual = this.textoCodigo.charAt(posicion);
    }

    public void analizarCodigoCss() {
        while (caracterActual != '\0' && !cambiarTokenEstado()) {
            if (Character.isWhitespace(caracterActual)) {
                controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                avanzarCaracter();
            } else if (caracterActual == '{') {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token("{", "{", "{", "CSS", " " ));
                avanzarCaracter();
            } else if (caracterActual == '}') {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token("}", "}", "}", "CSS", " "));
                avanzarCaracter();
            } else if (caracterActual == '.') {
                identificadorSelectorClase();
            } else if (caracterActual == '#') {
                identificadorSelectorID();
            } else if (caracterActual == '\'') {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token("'", "'", "'", "CSS", " "));
                leerCadena();
            } else if (Character.isDigit(caracterActual)) {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(String.valueOf(caracterActual), String.valueOf(caracterActual),String.valueOf(caracterActual),"CSS" , " "));
                avanzarCaracter();
            } else {
                identificarSelectorEtiqueta();
            }
        }
        for (int i = 0; i < controladorTokenEstado.getListaEnlazadaCSS().getTamaño(); i++) {
            System.out.print(controladorTokenEstado.getListaEnlazadaCSS().obtenerValor(i).getLexema());
        }
        controladorTokenEstado.setPosicion(posicion);
        controladorTokenEstado.setCaracterActual(caracterActual);
    }

    private void identificarSelectorEtiqueta() {
        boolean buscarSelectorEnReglas = true;
        StringBuilder texto = new StringBuilder();
        while (caracterActual != '\0' && caracterActual != ' ' && caracterActual != '{' && caracterActual != ':'
                && caracterActual != ';' && caracterActual != '\n') {
            texto.append(caracterActual);
            avanzarCaracter();
        }
        for (int i = 0; i < selectores.length; i++) {
            if (texto.toString().equals(selectores[i])) {
                buscarSelectorEnReglas = false;
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(texto.toString(), texto.toString(), texto.toString(), "CSS", "Etiqueta"));
                System.out.println("El selector ETIQUETA es: " + selectores[i]);
                if (caracterActual == ' ') {
                    avanzarCaracter();
                    controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                    avanzarEspaciosEnBlanco();
                    identificarSelectorCombinador();
                }
                break;
            }
        }
        if (buscarSelectorEnReglas) {
            if (caracterActual == ';' && texto.isEmpty()) {
                texto.append(caracterActual);
            }
            identificarSelectorReglas(texto.toString());
        }
    }

    private void identificarSelectorReglas(String texto) {
        boolean buscarSelectorEnOtros = true;
        for (int i = 0; i < reglas.length; i++) {
            if (texto.equals(reglas[i])) {
                buscarSelectorEnOtros = false;
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(reglas[i],reglas[i],reglas[i],"CSS","Regla"));
                System.out.println("El selector REGLAS es: " + texto);
                if (caracterActual == ' ') {
                    avanzarCaracter();
                    controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                    avanzarEspaciosEnBlanco();
                    identificarSelectorCombinador();
                }
                break;
            }
        }
        if (buscarSelectorEnOtros) {
            switch (caracterActual) {
                case ':':
                    texto = String.valueOf(caracterActual);
                    break;
            }
            identificarSelectorOtros(texto);
        }
    }

    private void identificarSelectorOtros(String texto) {
        boolean buscarSelectorEnIdentificadores = true;
        for (int i = 0; i < otros.length; i++) {
            if (texto.equals(otros[i])) {
                buscarSelectorEnIdentificadores = false;
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(texto,texto,texto,"CSS","Otros"));
                System.out.println("El selector OTROS es: " + texto);
                avanzarCaracter();//avanza el ultimo caracter de los selectores otros
                if (caracterActual == ' ') {
                    avanzarCaracter();
                    controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                    avanzarEspaciosEnBlanco();
                    identificarSelectorCombinador();
                } else if (caracterActual == '\n') {
                    controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token("\n", "\n", "\n"," ", " "));
                    avanzarCaracter();//avanza el caracter '\n'
                }
                break;
            }
        }
        if (buscarSelectorEnIdentificadores) {
            Pattern patron = Pattern.compile(EXPRESION_REGULAR_IDENTIFICADOR);
            Matcher igualador = patron.matcher(texto.toString());
            if (igualador.matches()) {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(texto, texto, "[a-z]+[0-9]*(-([a-z]|[0-9])+)*", "CSS", "Identificador"));
                System.out.println("El selector (tipo identificador) es: " + texto);
                if (caracterActual == ' ') {
                    avanzarCaracter();
                   controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                    avanzarEspaciosEnBlanco();
                    identificarSelectorCombinador();
                }
            }
        }
    }

    private void identificadorSelectorClase() {
        StringBuilder texto = new StringBuilder();
        while (posicion < textoCodigo.length() && (Character.isLetterOrDigit(textoCodigo.charAt(posicion))
                || textoCodigo.charAt(posicion) == '-' || textoCodigo.charAt(posicion) == '.'
                || textoCodigo.charAt(posicion) != ' ')) {
            texto.append(caracterActual);
            avanzarCaracter();
        }
        Pattern patron = Pattern.compile(EXPRESION_REGULAR_SELECTOR_CLASE);
        Matcher igualador = patron.matcher(texto.toString());
        if (igualador.matches()) {
            controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(texto.toString(),texto.toString(),"^\\.[a-z][a-z0-9-]*$","CSS","Selector Clase"));
            System.out.println("El selector CLASE es: " + texto.toString());
            if (caracterActual == ' ') {
                avanzarCaracter();
                controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                avanzarEspaciosEnBlanco();
                identificarSelectorCombinador();
            }
        }
    }

    private void identificadorSelectorID() {
        boolean buscarEnColores = true;
        StringBuilder texto = new StringBuilder();
        while (posicion < textoCodigo.length() && caracterActual != ';' && (Character.isLetterOrDigit(textoCodigo.charAt(posicion))
                || textoCodigo.charAt(posicion) == '-' || textoCodigo.charAt(posicion) == '#'
                || textoCodigo.charAt(posicion) != ' ')) {
            texto.append(caracterActual);
            avanzarCaracter();
        }
        Pattern patron = Pattern.compile(EXPRESION_REGULAR_SELECTOR_ID);
        Matcher igualador = patron.matcher(texto.toString());
        if (igualador.matches()) {
            buscarEnColores = false;
            controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(texto.toString(),texto.toString(),"^\\#[a-z][a-z0-9-]*$","CSS","Selector ID"));
            System.out.println("El selector ID es: " + texto.toString());
            if (caracterActual == ' ') {
                avanzarCaracter();
                controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                avanzarEspaciosEnBlanco();
                identificarSelectorCombinador();
            }
        }
        if (buscarEnColores) {
            identificarColorHexadecimal(texto.toString());
        }
    }

    private void identificarSelectorCombinador() {
        for (int i = 0; i < combinadores.length; i++) {
            if (combinadores[i].equals(String.valueOf(caracterActual))) {
                controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(combinadores[i], combinadores[i],combinadores[i],"CSS", "Combinador"));
                System.out.println("El selector COMBINADOR es: " + combinadores[i]);
                if (caracterActual == ' ') {
                    avanzarCaracter();
                    controladorTokenEstado.getListaTokensHTML().agregarElemento(new Token(" ", "Espacio_en_blanco", " ", "CSS", " "));
                    avanzarEspaciosEnBlanco();
                }
                break;
            }
        }
    }

    private void leerCadena() {
        StringBuilder cadena = new StringBuilder();
        avanzarCaracter();//avanza el caracter "'"
        while (caracterActual != '\'' && caracterActual != '\0') {
            cadena.append(caracterActual);
            avanzarCaracter();
        }
        controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token(cadena.toString(),"Cadena","Cadena", "CSS", "Cadena"));
        System.out.println("La cadena almacenada es: " + cadena.toString());
        if (caracterActual == '\'') {
            controladorTokenEstado.getListaEnlazadaCSS().agregarElemento(new Token("'", "'", "'", "CSS", "Cadena"));
            avanzarCaracter();//avanza el caracter "'"
        }
    }

    private void identificarColorHexadecimal(String texto) {
        Pattern pattern = Pattern.compile(EXPRESION_REGULAR_COLOR_HEXADECIMAL);
        Matcher matcher = pattern.matcher(texto);
        StringBuilder color = new StringBuilder();
        while (matcher.find()) {
            color.append(matcher.group()).append("\n");
        }
        System.out.println("Color encontrado: " + color.toString());
    }

    private boolean cambiarTokenEstado() {
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

    private void avanzarEspaciosEnBlanco() {
        while (caracterActual == ' ') {
            avanzarCaracter();
        }
    }
}
/*
* >>[html]
*<contenedor class="container" id="container-top">aqui texto</contenedor>
*>>[css]
*.danger-white1
 */
//texto	StringBuilder	ObjectVariable 	
