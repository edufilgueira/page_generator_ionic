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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class Principal extends javax.swing.JFrame {

    public Principal() throws IOException {
        initComponents();
        //setExtendedState(MAXIMIZED_BOTH);
        atalhoTeclado();
        carregarTexto();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        Texto = new javax.swing.JTextArea();
        Gerar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Texto.setColumns(20);
        Texto.setRows(5);
        jScrollPane2.setViewportView(Texto);

        Gerar.setText("Gerar");
        Gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Gerar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Gerar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GerarActionPerformed
        try {
            GerarCodigo();
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GerarActionPerformed

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
    
    private void carregarTexto() throws FileNotFoundException, IOException{
        FileReader arquivo = new FileReader(new File("mapa.txt"));
        BufferedReader ler_arquivo = new BufferedReader(arquivo);
        String linha = ler_arquivo.readLine();
        
        while(linha != null) 
        {
            Texto.setText( Texto.getText() + "\n" + linha);
            linha = ler_arquivo.readLine();
        }
        ler_arquivo.close();
    }
    
    private void novoDiretorio(String caminho) {
        File diretorio = new File(caminho);
        diretorio.mkdir();
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
            }
            if(linha.contains("["))
                linha = ler_arquivo.readLine();

            if(!linha.equals("[") && !linha.equals("]")) {
                String[] divideLinha = linha.split(":");
                atributos.put(divideLinha[0], divideLinha[1]);
                System.out.println(divideLinha[0]+":"+divideLinha[1]);
            } 
            else if(linha.equals("]")){
                modelo.setAtributos(atributos);
                modelos.add(modelo); 
                modelo = null;
                atributos = null;
                atributos = new HashMap<String, String>();
            }


            linha = ler_arquivo.readLine();
            //iniciarGeracao(modelo);
        }
        ler_arquivo.close();

        iniciarGeracao(modelos);
    }
    
    private void iniciarGeracao(List<Modelo> modelos) throws IOException {
        novoDiretorio("codefont");
        gerarApp(modelos);
        gerarProviders(modelos);
        gerarModels(modelos);
        services(modelos);
    }
    
    
    private void gerarApp(List<Modelo> modelos) throws IOException{
        novoDiretorio("codefont\\src");
        novoDiretorio("codefont\\src\\app");
        FileWriter arquivo;
        
        //**************app.component.ts************************
        arquivo = new FileWriter("codefont/src/app/"+new File("app.component.ts"));
	BufferedWriter novaLinha = new BufferedWriter(arquivo);
        
        novaLinha.write("import { Component } from '@angular/core';\n");
        novaLinha.write("import { Platform } from 'ionic-angular';\n");
        novaLinha.write("import { StatusBar } from '@ionic-native/status-bar';\n");
        novaLinha.write("import { SplashScreen } from '@ionic-native/splash-screen';\n");
        novaLinha.newLine();
        novaLinha.write("import { TabsPage } from '../pages/tabs/tabs';\n");
        novaLinha.newLine();
        novaLinha.write("import { DatabaseProvider } from '../providers/database/database'\n");
        novaLinha.newLine();
        novaLinha.write("@Component({\n");
        novaLinha.write("  templateUrl: 'app.html'\n");
        novaLinha.write("})\n");
        novaLinha.write("export class MyApp {\n");
        novaLinha.write("  rootPage:any = null //TabsPage;\n");
        novaLinha.newLine();
        novaLinha.write("  constructor(\n");
        novaLinha.write("    platform: Platform, \n");
        novaLinha.write("    statusBar: StatusBar, \n");
        novaLinha.write("    splashScreen: SplashScreen,\n");
        novaLinha.write("    databaseProvider: DatabaseProvider) {\n");
        novaLinha.write("    platform.ready().then(() => {\n");
        novaLinha.write("      // Okay, so the platform is ready and our plugins are available.\n");
        novaLinha.write("      // Here you can do any higher level native things you might need.\n");
        novaLinha.write("      databaseProvider.createDatabase().then(() =>{\n");
        novaLinha.write("        this.openTabsPage(splashScreen);\n");
        novaLinha.write("        alert('deu tud certo. banco criado');\n");
        novaLinha.write("      })\n");
        novaLinha.write("      .catch(e => {\n");
        novaLinha.write("        console.error(e)\n");
        novaLinha.write("        alert('banco nao foi criado');\n");
        novaLinha.write("        this.openTabsPage(splashScreen);\n");
        novaLinha.write("      });\n");
        novaLinha.newLine();
        novaLinha.write("      statusBar.styleDefault();\n");
        novaLinha.newLine();
        novaLinha.write("    });\n");
        novaLinha.write("  }\n");
        novaLinha.newLine();
        novaLinha.write("  public openTabsPage(splashScreen: SplashScreen){\n");
        novaLinha.write("    splashScreen.hide();\n");
        novaLinha.write("    this.rootPage = TabsPage;\n");
        novaLinha.write("  }\n");
        novaLinha.write("}\n");
        novaLinha.close();
        
        //**************app.html************************
        arquivo = new FileWriter("codefont/src/app/"+new File("app.html"));
	BufferedWriter app_html = new BufferedWriter(arquivo);
        app_html.write("<ion-nav [root]=\"rootPage\"></ion-nav>");
        app_html.close();
        
        //**************app.module.ts************************
        arquivo = new FileWriter("codefont/src/app/"+new File("app.module.ts"));
	BufferedWriter app_module = new BufferedWriter(arquivo);
        app_module.write("import { NgModule, ErrorHandler } from '@angular/core';\n");
        app_module.write("import { BrowserModule } from '@angular/platform-browser';\n");
        app_module.write("import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';\n");
        app_module.write("import { MyApp } from './app.component';\n");
        app_module.newLine();
        app_module.write("import { SQLite } from '@ionic-native/sqlite';\n");
        app_module.newLine();
        app_module.write("import { AboutPage } from '../pages/about/about';\n");
        app_module.write("import { ContactPage } from '../pages/contact/contact';\n");
        app_module.write("import { HomePage } from '../pages/home/home';\n");
        app_module.write("import { TabsPage } from '../pages/tabs/tabs';\n");
        app_module.newLine();
        app_module.write("import { StatusBar } from '@ionic-native/status-bar';\n");
        app_module.write("import { SplashScreen } from '@ionic-native/splash-screen';\n");
        app_module.newLine();
        app_module.write("//para executar recurso tipo AJAX\n");
        app_module.write("import 'rxjs/add/operator/map';\n");
        app_module.write("import 'rxjs/add/operator/toPromise';\n");
        app_module.write("import { HttpModule } from '@angular/http';\n");
        app_module.write("import { DatabaseProvider } from '../providers/database/database';\n");
        app_module.newLine();
        app_module.write("@NgModule({\n");
        app_module.write("  declarations: [\n");
        app_module.write("    MyApp,\n");
        app_module.write("    AboutPage,\n");
        app_module.write("    ContactPage,\n");
        app_module.write("    HomePage,\n");
        app_module.write("    TabsPage\n");
        app_module.write("  ],\n");
        app_module.write("  imports: [\n");
        app_module.write("    BrowserModule,\n");
        app_module.write("    HttpModule,\n");
        app_module.write("    IonicModule.forRoot(MyApp)\n");
        app_module.write("  ],\n");
        app_module.write("  bootstrap: [IonicApp],\n");
        app_module.write("  entryComponents: [\n");
        app_module.write("    MyApp,\n");
        app_module.write("    AboutPage,\n");
        app_module.write("    ContactPage,\n");
        app_module.write("    HomePage,\n");
        app_module.write("    TabsPage\n");
        app_module.write("  ],\n");
        app_module.write("  providers: [\n");
        app_module.write("    StatusBar,\n");
        app_module.write("    SplashScreen,\n");
        app_module.write("    SQLite,\n");
        app_module.write("    {provide: ErrorHandler, useClass: IonicErrorHandler},\n");
        app_module.write("    DatabaseProvider");
        app_module.write("  ]\n");
        app_module.write("})\n");
        app_module.write("export class AppModule {}\n");
        app_module.close();
    }
    
    private void gerarProviders(List<Modelo> modelos) throws IOException{

        novoDiretorio("codefont\\src");
        novoDiretorio("codefont\\src\\providers");
        novoDiretorio("codefont\\src\\providers\\database");
        
        FileWriter arquivo;
        arquivo = new FileWriter("codefont/src/providers/database/"+new File("database.ts"));
	BufferedWriter novaLinha = new BufferedWriter(arquivo);
        novaLinha.write("import { Injectable } from '@angular/core';\n");
        novaLinha.write("import { SQLite, SQLiteObject } from '@ionic-native/sqlite';\n");
        novaLinha.newLine();
        novaLinha.write("/*\n");
        novaLinha.write("  Generated class for the DatabaseProvider provider.\n");
        novaLinha.newLine();
        novaLinha.write("  See https://angular.io/guide/dependency-injection for more info on providers\n");
        novaLinha.write("  and Angular DI.\n");
        novaLinha.write("*/\n");
        novaLinha.write("@Injectable()\n");
        novaLinha.write("export class DatabaseProvider {\n");
        novaLinha.newLine();
        novaLinha.write("  constructor(public sqlite: SQLite) {  }\n");
        novaLinha.newLine();
        novaLinha.write("  public getDB(){\n");
        novaLinha.write("    return this.sqlite.create({ \n");
        novaLinha.write("      name:\"DB\",\n");
        novaLinha.write("      location: \"default\"\n");
        novaLinha.write("    });\n");
        novaLinha.write("  }\n");
        novaLinha.newLine();
        novaLinha.write("  public createDatabase(){\n");
        novaLinha.write("    return this.getDB()\n");
        novaLinha.write("    .then((db: SQLiteObject) => {\n");
        novaLinha.newLine();  
        novaLinha.write("      this.createTables(db);\n");
        //novaLinha.write("      this.insertDefaultItems(db);\n");
        novaLinha.newLine();
        novaLinha.write("    })\n");
        novaLinha.write("    .catch(e => console.error(e));\n");
        novaLinha.write("  }\n");
        novaLinha.newLine();
        novaLinha.write("  private createTables(db: SQLiteObject) {\n");
        novaLinha.write("    // Criando as tabelas\n");
        novaLinha.write("    db.sqlBatch([\n");
        String frase = "";
        
        String[] modelosSerializado;
        String modeloSerializadoSingular;
        String modeloSerializadoPlural;
            
        for(Modelo modelo : modelos){
            String createTable = "";
            String FOREIGN_KEY = "";
            for (String key : modelo.getAtributos().keySet()) {
                String value =  modelo.getAtributos().get(key);
                //value:tipo//key:campo
                FOREIGN_KEY = "";
                if(key.contains("(") && key.contains(")")){
                    key = key.replace("(", "");
                    key = key.replace(")", "");
                    //value = value + "_id";
                    key = key + "_id";
                    value = "integer";
                    
                    FOREIGN_KEY = "FOREIGN KEY("+modelo.getModelo()+"_id) REFERENCES Tcategories(id)";
                }

                createTable = createTable + key + " " + value + ", ";
            }
            modelosSerializado =  modelo.getModelo().split(":");
            modeloSerializadoSingular = modelosSerializado[0];
            modeloSerializadoPlural = modelosSerializado[1];
                
            createTable = createTable.substring(0, createTable.length()-2);
            frase = frase + "      ['CREATE TABLE IF NOT EXISTS "+modeloSerializadoPlural.toLowerCase()+" (id integer primary key AUTOINCREMENT NOT NULL, "+createTable.toLowerCase()+")'],\n";
            
        }
        frase = frase.substring(0, frase.length()-2);
        novaLinha.write(frase);
        novaLinha.newLine();
        //novaLinha.write("      ['CREATE TABLE IF NOT EXISTS categories (id integer primary key AUTOINCREMENT NOT NULL, name TEXT)'],\n");
        //novaLinha.write("      ['CREATE TABLE IF NOT EXISTS products (id integer primary key AUTOINCREMENT NOT NULL, name TEXT, price REAL, duedate DATE, active integer, category_id integer, FOREIGN KEY(category_id) REFERENCES categories(id))']\n");
        novaLinha.write("    ])\n");
        novaLinha.write("      .then(() => console.log('Tabelas criadas'))\n");
        novaLinha.write("      .catch(e => console.error('Erro ao criar as tabelas', e));\n");
        novaLinha.write("  }\n");
        novaLinha.newLine();
        novaLinha.write("  private insertDefaultItems(db: SQLiteObject) {\n");
        novaLinha.write("    db.executeSql('select COUNT(id) as qtd from categories')\n");
        novaLinha.write("    .then((data: any) => {\n");
        novaLinha.write("      //Se não existe nenhum registro\n");
        novaLinha.write("      if (data.rows.item(0).qtd == 0) {\n");
        novaLinha.newLine();
        novaLinha.write("        // Criando as tabelas\n");
        novaLinha.write("        db.sqlBatch([\n");
        novaLinha.write("          ['insert into categories (name) values (?)', ['Hambúrgueres']],\n");
        novaLinha.write("          ['insert into categories (name) values (?)', ['Bebidas']],\n");
        novaLinha.write("          ['insert into categories (name) values (?)', ['Sobremesas']]\n");
        novaLinha.write("        ])\n");
        novaLinha.write("          .then(() => console.log('Dados padrões incluídos'))\n");
        novaLinha.write("          .catch(e => console.error('Erro ao incluir dados padrões', e));\n");
        novaLinha.newLine(); 
        novaLinha.write("      }\n");
        novaLinha.write("    })\n");
        novaLinha.write("    .catch(e => console.error('Erro ao consultar a qtd de categorias', e));\n");
        novaLinha.write("  }\n");
        novaLinha.newLine();
        novaLinha.write("}\n");
        novaLinha.close();

    }
    
    
    private void gerarModels(List<Modelo> modelos) throws IOException
    {
        novoDiretorio("codefont\\src");
        novoDiretorio("codefont\\src\\model");
        for(Modelo modelo : modelos){

            
            String[] modelosSerializado = modelo.getModelo().split(":");
            String modeloSerializadoSingular = modelosSerializado[0];
            String modeloSerializadoPlural = modelosSerializado[1];
            
            String nome_do_arquivo = this.converterFormatoPublic(modeloSerializadoSingular);
            FileWriter arquivo;
            arquivo = new FileWriter("codefont/src/model/"+new File(nome_do_arquivo+".ts"));
            BufferedWriter novaLinha = new BufferedWriter(arquivo);
            
            for (String key : modelo.getAtributos().keySet()) {
                String value =  modelo.getAtributos().get(key).toLowerCase();
                //value:tipo//key:campo
                if(key.contains("(") && key.contains(")")){
                    key = key.replace("(", "");
                    key = key.replace(")", "");
                    value = this.converterFormatoPublic(key);
                    novaLinha.write("import { "+value+" } from './"+value+"'\n");
                }
            }
            
            novaLinha.newLine();
            novaLinha.write("export class "+nome_do_arquivo+"{\n");
            novaLinha.newLine();
            novaLinha.write("    private id: number;\n");
            
            for (String key : modelo.getAtributos().keySet()) {
                String value =  modelo.getAtributos().get(key).toLowerCase();
                //value:tipo//key:campo
                value = conversaoTipos(value);
                
                if(key.contains("(") && key.contains(")")){
                    key = key.replace("(", "");
                    key = key.replace(")", "");
                    value = converterFormatoPublic(key);
                }
                //key = key.substring(0,1).toUpperCase()+key.substring(1, key.length()).toLowerCase();
                novaLinha.write("    private "+converterFormatoPrivate(key)+": "+value+";\n"); 
            }

            novaLinha.newLine();
            novaLinha.write("    public setId(id: number):void{\n");
            novaLinha.write("        this.id = id;\n");
            novaLinha.write("    }\n");
            novaLinha.newLine();
            novaLinha.write("    public getId():number{\n");
            novaLinha.write("        return this.id;\n");
            novaLinha.write("    }\n");
            novaLinha.newLine();
            
            for (String key : modelo.getAtributos().keySet()) {
                String value =  modelo.getAtributos().get(key).toLowerCase();
                //value:tipo//key:campo
                value = conversaoTipos(value);
                
                if(key.contains("(") && key.contains(")")){
                    key = key.replace("(", "");
                    key = key.replace(")", "");
                    value = key;
                }
                //key = key.substring(0,1).toUpperCase()+key.substring(1, key.length()).toLowerCase();
                novaLinha.write("    public set"+converterFormatoPublic(key)+"("+converterFormatoPrivate(key)+": "+converterFormatoPublic(value)+"):void{\n");
                novaLinha.write("        this."+converterFormatoPrivate(key)+" = "+converterFormatoPrivate(key)+";\n");
                novaLinha.write("    }\n");
                novaLinha.newLine();
                novaLinha.write("    public get"+converterFormatoPublic(key)+"():"+converterFormatoPublic(value)+"{\n");
                novaLinha.write("        return this."+converterFormatoPrivate(key)+";\n");
                novaLinha.write("    }\n");
                novaLinha.newLine();
            }
            novaLinha.newLine();
            novaLinha.write("}\n");        
            novaLinha.close();
        }
    }
    
    private void services(List<Modelo> modelos) throws IOException{
        novoDiretorio("codefont\\src");
        
        for(Modelo modelo : modelos){

            String[] modelosSerializado = modelo.getModelo().split(":");
            String modeloSerializadoSingular = modelosSerializado[0];
            String modeloSerializadoPlural = modelosSerializado[1];
            
            String nome_da_pasta = converterFormatoPublic(modeloSerializadoSingular)+"Service";
            novoDiretorio("codefont\\src\\providers\\"+nome_da_pasta);
            
            String nome_do_arquivo = this.converterFormatoPublic(modeloSerializadoSingular);
            FileWriter arquivo;
            arquivo = new FileWriter("codefont/src/providers/"+nome_da_pasta+"/"+new File(nome_do_arquivo+"Servico.ts"));
            BufferedWriter novaLinha = new BufferedWriter(arquivo);
            
novaLinha.write("import { Injectable } from '@angular/core';\n");
novaLinha.write("import { SQLiteObject } from '@ionic-native/sqlite';\n");
novaLinha.write("import { DatabaseProvider } from '../database/database'\n");
novaLinha.write("import { Cliente } from '../../model/Cliente'\n");
novaLinha.newLine();
novaLinha.newLine();
novaLinha.write("/*\n");
novaLinha.write("  Generated class for the ClienteServicoProvider provider.\n");
novaLinha.newLine();
novaLinha.write("  See https://angular.io/guide/dependency-injection for more info on providers\n");
novaLinha.write("  and Angular DI.\n");
novaLinha.write("*/\n");
novaLinha.write("@Injectable()\n");
novaLinha.write("export class ClienteServicoProvider {\n");
novaLinha.newLine();
novaLinha.write("  constructor(public databaseProvider: DatabaseProvider) { }\n");
novaLinha.newLine();
novaLinha.write("  public insert(cliente: Cliente):void{\n");
novaLinha.write("    this.databaseProvider.getDB()\n");
novaLinha.write("    .then((db: SQLiteObject) => {\n");
novaLinha.write("        let sql = 'insert into movie (id) values (?)';\n");
novaLinha.write("        let data = [cliente.getId];\n");
novaLinha.newLine();
novaLinha.write("        db.executeSql(sql,data).then(() => {\n");
novaLinha.write("          console.log('Registro inserido');\n");
novaLinha.write("        })\n");
novaLinha.write("        .catch(e => console.error(e));\n");
novaLinha.write("    })\n");
novaLinha.write("    .catch(e => console.error(e));\n");
novaLinha.write("  }\n");
novaLinha.newLine();
novaLinha.write("  public remove(id: number):void{\n");
novaLinha.write("    this.databaseProvider.getDB()\n");
novaLinha.write("    .then((db: SQLiteObject) => {\n");
novaLinha.write("        let sql = 'delete from movie where id = ?';\n");
novaLinha.write("        let data = [id];\n");
novaLinha.newLine();
novaLinha.write("        db.executeSql(sql,data).then(() => {\n");
novaLinha.write("          console.log('Registro apagado');\n");
novaLinha.write("        })\n");
novaLinha.write("        .catch(e => console.error(e));\n");
novaLinha.write("    })\n");
novaLinha.write("    .catch(e => console.error(e));\n");
novaLinha.write("  }\n");
novaLinha.newLine();
novaLinha.write("  public update(cliente: Cliente):void{\n");
novaLinha.write("    this.databaseProvider.getDB()\n");
novaLinha.write("    .then((db: SQLiteObject) => {\n");
novaLinha.write("        let sql = 'update movie set id = ?, nome = ? where i = ?';\n");
novaLinha.write("        let data = [cliente.getId];\n");
novaLinha.newLine();
novaLinha.write("        db.executeSql(sql,data).then(() => {\n");
novaLinha.write("          console.log('Registro atualizado');\n");
novaLinha.write("        })\n");
novaLinha.write("        .catch(e => console.error(e));\n");
novaLinha.write("    })\n");
novaLinha.write("    .catch(e => console.error(e));\n");
novaLinha.write("  }\n");
novaLinha.newLine();
novaLinha.write("  public listAll():Array<Cliente>{\n");
novaLinha.write("    this.databaseProvider.getDB()\n");
novaLinha.write("    .then((db: SQLiteObject) => {\n");
novaLinha.write("        let sql = 'SELECT * FROM cliente';\n");
novaLinha.write("        let data: any[];\n");
novaLinha.newLine();        
novaLinha.write("        db.executeSql(sql).then((data: any) => {\n");
novaLinha.write("          if(data.rows.length > 0){\n");
novaLinha.write("            let clientes = new Array<Cliente>();\n");
novaLinha.write("            for(var i = 0; i < data.rows.length; i++){\n");
novaLinha.write("              let tmp = data.rows.item(i);\n");
novaLinha.write("              var cliente = new Cliente();\n");
novaLinha.write("              cliente.getId = tmp.id\n");
novaLinha.newLine();
novaLinha.write("              clientes.push(cliente);\n");
novaLinha.write("            }\n");
novaLinha.newLine();
novaLinha.write("            return clientes;\n");
novaLinha.write("          }\n");
novaLinha.write("          else\n");
novaLinha.write("            return new Array<Cliente>();\n");
novaLinha.write("        })\n");
novaLinha.write("        .catch(e => console.error(e));\n");
novaLinha.newLine();
novaLinha.write("    })\n");
novaLinha.write("    .catch(e => console.error(e));\n");
novaLinha.write("    return new Array<Cliente>();\n");
novaLinha.write("  }\n");
novaLinha.write("}");
    
            novaLinha.close();
        }
    }
    
    private String conversaoTipos(String value){
        if(value.toLowerCase().equals("integer"))
            value = "number";
        else if(value.toLowerCase().equals("text"))
            value = "string";
        else if(value.toLowerCase().equals("real"))
            value = "number";
        
        return value;
    }
    
    private String converterFormatoPublic(String nome){
        String[] valor = nome.split("_");
        String classe = "";
        for (String item : valor) {
            classe = classe + item.substring(0, 1).toUpperCase() + item.substring(1, item.length()).toLowerCase();
        }
        return classe;
    }
    
    private String converterFormatoPrivate(String nome){
        String[] valor = nome.split("_");
        String classe = "";
        for (int i = 0; i < valor.length; i++) {
            if(i==0)
                classe = classe + valor[i].substring(0, 1).toLowerCase() + valor[i].substring(1, valor[i].length()).toLowerCase();
            else
                classe = classe + valor[i].substring(0, 1).toUpperCase() + valor[i].substring(1, valor[i].length()).toLowerCase();
        }
        return classe;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Gerar;
    private javax.swing.JTextArea Texto;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}


 /* mensagens = [
    {id: '1', categoria: 'GRAÇA', titulo: "A ultima palavra vem de Deus", img:'img/capa01.jpg', audio:"vh4-prod.mp3", descricao: 'Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor '},
    {id: '2', categoria: 'GRAÇA', titulo: 'Justiça de Deus', img:'img/capa01.jpg', audio:"dvh4-prod.mp3", descricao: 'Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor '},
    {id: '3', categoria: 'GRAÇA', titulo: 'Deus corrige a quem ama', img:'img/capa01.jpg', audio:"dvh4-prod.mp3", descricao: 'Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor Lorem impsu dollor '}
  ];

  response = '[{"Language":"jQuery","ID":"1"},{"Language":"C#","ID":"2"},{"Language":"PHP","ID":"3"},{"Language":"Java","ID":"4"}, {"Language":"Python","ID":"5"},{"Language":"Perl","ID":"6"}, {"Language":"C++","ID":"7"},{"Language":"ASP","ID":"8"}, {"Language":"Ruby","ID":"9"}]';


  json_obj = JSON.parse(this.response);*/

https://www.youtube.com/watch?v=yWs2xceNCh0

//ionic cordova plugin add cordova-sqlite-storage
//npm install --save @ionic-native/sqlite
