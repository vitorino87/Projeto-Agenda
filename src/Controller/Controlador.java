/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Factory;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tiago.lucas
 */
public class Controlador {

    /**
     * @param args the command line arguments
     */
    Factory factory;
    private static String usuario, senha;
    public static void main(String[] args) {
        // TODO code application logic here     
        
        View.Login login = new View.Login();
        login.setTitle("Login");
        login.setVisible(true);
        //factory.fecharConexao();
    }
    public void carregarDriver(){
        factory = new Factory();
        factory.carregarDriver();
    }
    
    public void conectar(String usuario, String senha) throws SQLException{
        carregarDriver();
        Controlador.senha=senha;
        Controlador.usuario=usuario;
        factory.conectar(usuario,senha);
        if(factory.createTable()){
            System.out.println("Deu certo!");
            View.Agenda agenda = new View.Agenda();
            agenda.setTitle("Agenda");
            factory.fecharConexao();
            agenda.setVisible(true);
        }else{
            System.out.println("Erro!");
            factory.fecharConexao();
        }
        
    }
    
    public void inserirContato(String nome, int idade, String endereco, String telefone, String celular) throws SQLException{
        carregarDriver();
        factory.conectar(usuario, senha);
        factory.inserirContato(nome, idade, endereco, telefone, celular);
        factory.fecharConexao();
    }
    
    public ResultSet consultarContato(String nome){
        ResultSet rs;
        factory = new Factory();
        rs = factory.consultarContato(usuario, senha, nome);
        return rs;
    }
    
    public void alterarContato(String nome, String idade, String endereco, String telefone, String celular, String nomePesquisado){
        factory = new Factory();
        factory.alterarContato(usuario, senha, nome, idade, endereco, telefone, celular, nomePesquisado);
    }
    
    public void deletarContato(String nome, String telefone){
        factory = new Factory();
        factory.deletarContato(usuario, senha, nome, telefone);
    }
}
