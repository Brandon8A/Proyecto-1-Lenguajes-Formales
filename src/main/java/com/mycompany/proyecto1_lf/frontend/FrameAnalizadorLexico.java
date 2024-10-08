/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyecto1_lf.frontend;

import com.mycompany.proyecto1_lf.backend.analizadorlexicohtml.AnalizadorLexicoHtml;
import com.mycompany.proyecto1_lf.backend.listaenlazada.ControladorTokenEstado;

/**
 *
 * @author brandon
 */
public class FrameAnalizadorLexico extends javax.swing.JFrame {

    /**
     * Creates new form FrameAnalizadorLexico
     */
    public FrameAnalizadorLexico() {
        initComponents();
        btnGenerarHtml.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAnalizadorLexico = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaCodigoFuente = new javax.swing.JTextArea();
        btnAnalizar = new javax.swing.JButton();
        btnGenerarHtml = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtAreaCodigoFuente.setColumns(20);
        txtAreaCodigoFuente.setRows(5);
        jScrollPane1.setViewportView(txtAreaCodigoFuente);

        btnAnalizar.setText("Analizar Codigo");
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });

        btnGenerarHtml.setText("Generar Html");

        javax.swing.GroupLayout pnlAnalizadorLexicoLayout = new javax.swing.GroupLayout(pnlAnalizadorLexico);
        pnlAnalizadorLexico.setLayout(pnlAnalizadorLexicoLayout);
        pnlAnalizadorLexicoLayout.setHorizontalGroup(
            pnlAnalizadorLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnalizadorLexicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAnalizadorLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAnalizadorLexicoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(pnlAnalizadorLexicoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                        .addComponent(btnAnalizar)
                        .addGap(175, 175, 175)
                        .addComponent(btnGenerarHtml)
                        .addGap(197, 197, 197))))
        );
        pnlAnalizadorLexicoLayout.setVerticalGroup(
            pnlAnalizadorLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnalizadorLexicoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAnalizadorLexicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerarHtml)
                    .addComponent(btnAnalizar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAnalizadorLexico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAnalizadorLexico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
        // TODO add your handling code here:
        String texto = txtAreaCodigoFuente.getText();
        ControladorTokenEstado tokenEstado = new ControladorTokenEstado(texto);
//        AnalizadorLexicoHtml analizadorLexico = new AnalizadorLexicoHtml(texto);
//        analizadorLexico.esEtiqueta();
    }//GEN-LAST:event_btnAnalizarActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JButton btnGenerarHtml;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlAnalizadorLexico;
    private javax.swing.JTextArea txtAreaCodigoFuente;
    // End of variables declaration//GEN-END:variables
}
