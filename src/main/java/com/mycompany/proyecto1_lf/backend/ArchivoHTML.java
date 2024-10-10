/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1_lf.backend;

import com.mycompany.proyecto1_lf.backend.listaenlazada.ControladorTokenEstado;
import com.mycompany.proyecto1_lf.backend.listaenlazada.ListaEnlazada;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author brandon
 */
public class ArchivoHTML {
    
    private File archivoHTML = new File("archivo.html");
    private ControladorTokenEstado controladorTokenEstado;
    private String codigo;
    
    public ArchivoHTML(ControladorTokenEstado controladorTokenEstado) {
        this.controladorTokenEstado = controladorTokenEstado;
    }
    
    public void escribirCodigo(ListaEnlazada listaEnlazadaCSS){
        codigo = obtenerDataActual();
        for (int i = 0; i < controladorTokenEstado.getListaEnlazadaCSS().getTamaño(); i++) {
            codigo = codigo + String.format("%s", controladorTokenEstado.getListaEnlazadaCSS().obtenerValor(i).getLexema());
        }
        agregarCodigoJS(controladorTokenEstado.getListaEnlazadaJS());
    }
    
    private String obtenerDataActual() {
        if (!archivoHTML.exists()) {
            return """
                   <!Doctype html>
                   <html lang="en">
                   <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                   	<title>Document</title>
                        <style>
                   """;
        }
        return leerArchivo();
    }
    
    private void agregarCodigoJS(ListaEnlazada listaEnlazadaJS){
        codigo = codigo + "\n</style>\n<script>";
        for (int i = 0; i < listaEnlazadaJS.getTamaño(); i++) {
            codigo = codigo + String.format("%s", controladorTokenEstado.getListaEnlazadaCSS().obtenerValor(i).getLexema());
        }
        agregarCodigoHTML(controladorTokenEstado.getListaTokensHTML());
    }
    
    public void agregarCodigoHTML(ListaEnlazada listaEnlazadaHtml){
        codigo = codigo + "\n</script>\n</head>\n<body>";
        for (int i = 0; i < listaEnlazadaHtml.getTamaño(); i++) {
            codigo = codigo + String.format("%s", listaEnlazadaHtml.obtenerValor(i).getLexema());
        }
        escribirEnArchivo(codigo);
    }
    
    
    
    private String leerArchivo() {
        String contenido = "";
        try (FileReader fileReader = new FileReader(archivoHTML);
                BufferedReader reader = new BufferedReader(fileReader)) {
            String linea = reader.readLine();
            while (linea != null) {
                contenido = contenido + linea + "\n";
                linea = reader.readLine();
            }
            contenido = contenido.substring(0, contenido.lastIndexOf("\n"));
            contenido = contenido.substring(0, contenido.lastIndexOf("\n"));
            return contenido.substring(0, contenido.lastIndexOf("\n"));
            
        }  catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    private void escribirEnArchivo(String contenido) {
        try (FileWriter fileWriter = new FileWriter(archivoHTML);
                BufferedWriter writer = new BufferedWriter(fileWriter);) {
            writer.append(contenido);
            writer.append("\n</body>");
            writer.append("\n</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
