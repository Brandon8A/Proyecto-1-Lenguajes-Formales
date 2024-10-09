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
    
    public ArchivoHTML(ControladorTokenEstado controladorTokenEstado) {
        this.controladorTokenEstado = controladorTokenEstado;
    }
    
    public void escribirCodigoHTML(ListaEnlazada listaEnlazadaHtml){
        String data = obtenerDataActual();
        for (int i = 0; i < controladorTokenEstado.getListaTokensHTML().getTamaño(); i++) {
            data = data + String.format("%s", controladorTokenEstado.getListaTokensHTML().obtenerValor(i).getLexema());
        }
        escribirEnArchivo(data);
    }
    
    private String obtenerDataActual() {
        if (!archivoHTML.exists()) {
            return """
                   <html>
                   <head>
                   	<title>Document</title>
                   </head>
                   <body>
                   """;
        }
        return leerArchivo();
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
