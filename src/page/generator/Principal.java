/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.generator;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edu
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form principal
     */
    public Principal() throws IOException {
        initComponents();
        //setExtendedState(MAXIMIZED_BOTH);
        atalhoTeclado();
        GerarCodigo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sair(){
        System.exit(0);
    }

    private void atalhoTeclado(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent event) {
                    if(event.getID() == KeyEvent.KEY_RELEASED 
                       && event.getKeyCode() == KeyEvent.VK_ESCAPE){
                            sair();
                            return true;
                    }
                    return false;
                }
        });
    }
    
    public void GerarCodigo() throws IOException {
        List<Modelo> modelos = new ArrayList<>();
        Map<String, String> atributos = new HashMap<String, String>();

        FileReader arquivo = new FileReader(new File("mapa.txt"));
        BufferedReader ler_arquivo = new BufferedReader(arquivo);
        String linha = ler_arquivo.readLine();
        Modelo modelo = null;
        while(linha != null) 
        {
                if(linha.contains("-"))
                        linha = ler_arquivo.readLine();
                if(linha.contains("{"))
                {
                        modelo = new Modelo();
                        linha = ler_arquivo.readLine();
                        modelo.setModelo(linha);
                        System.out.println(linha);
                        linha = ler_arquivo.readLine();

                        modelos.add(modelo);
                }
                if(linha.contains("["))
                        linha = ler_arquivo.readLine();

                if(!linha.equals("[")) {
                        String[] divideLinha = linha.split(":");
                        atributos.put(divideLinha[0], divideLinha[1]);
                        System.out.println(divideLinha[0]+":"+divideLinha[1]);
                }
                linha = ler_arquivo.readLine();
                //iniciarGeracao(modelo,atributos);
        }
        ler_arquivo.close();

        //gerarDependencias(modelos);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
