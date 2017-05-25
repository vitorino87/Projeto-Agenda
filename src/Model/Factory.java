/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;
import java.util.Collection;

/**
 * Na verdade não se trata de uma fábrica de conexões, nesta classe estarei
 * repetindo várias vezes a criação de algumas variáveis, para aprendê-las.
 *
 * @author tiago.lucas
 */
/**
 *
 *
 * @author tiago.lucas
 */
public class Factory {

    Connection dbConnection;
    Statement stmt;

    /**
     * Carregar driver
     */
    public void carregarDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Não foi possível carregar o driver");
        }
    }

    /**
     * Conectar ao banco de dados
     *
     * @param usuario
     * @param senha
     */
    public void conectar(String usuario, String senha) {
        String url = "jdbc:mysql://localhost:3306/tiago";
        try {
            dbConnection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println("Não foi possível estabelecer a conexão");
        }
    }

    /**
     * Fechar a conexão com o banco de dados
     *
     * @throws SQLException
     */
    public void fecharConexao() throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.close();
        dbConnection.close();
        //System.out.println("Não foi possível fechar a conexão");       
    }

    /**
     * Cria a tabela Agenda
     *
     * @return
     */
    public boolean createTable() {
        boolean verificador = false;
        String create = "create table if not exists agenda(nome varchar(50), "
                + "idade int(2), endereco varchar(100), telefone varchar(15),"
                + "celular varchar(15));";
        try {
            stmt = dbConnection.createStatement();
            stmt.execute(create);
            stmt.close();
            verificador = true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return verificador;
    }

    /**
     * Inserir um contato no banco de dados
     *
     * @param nome
     * @param idade
     * @param endereco
     * @param telefone
     * @param celular
     */
    public void inserirContato(String nome, int idade, String endereco, String telefone, String celular) {
        try {
            String sql = "insert into agenda values(?,?,?,?,?)";
            try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
                ps.setString(1, nome);
                ps.setInt(2, idade);
                ps.setString(3, endereco);
                ps.setString(4, telefone);
                ps.setString(5, celular);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("não deu certo!");
        }
    }

    /**
     * Cria o banco caso não exista
     *
     * @return
     */
    public boolean createSchema() {
        boolean verificador = false;
        String sql = "create schema if not exists tiago;";
        try {
            stmt = dbConnection.createStatement();
            stmt.execute(sql);
            stmt.close();
            verificador = true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return verificador;
    }

    public ResultSet consultarContato(String usuario, String senha, String nome) {
        //criando uma variável 
        Connection conexao;
        String sql;
        try {
            //passo 1: carregando a classe
            Class.forName("com.mysql.jdbc.Driver");
            //passo 2: conectando ao banco
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiago", usuario, senha);
            //passo 3: gerando a query
            sql = "select * from agenda where nome=?;";
            //passo 4: criando o PreparedStatement
            PreparedStatement ps = conexao.prepareStatement(sql);
            //passo 5: adicionando os valores a busca
            ps.setString(1, nome);
            /*ps.setString(2, telefone);*/
            //passo 6: executando a query e armazenando o resultado no ResultSet
            ResultSet rs = ps.executeQuery();
            //passo 7: fechando a conexão do PreparedStatement
            //ps.close();
            //passo 8: retorna o ResultSet 
            return rs;

        } catch (ClassNotFoundException | SQLException ex) {
            return null;
        }
    }

    public void alterarContato(String usuario, String senha, String nome, String idade, String endereco, String telefone, String celular, String nomePesquisado) {

        try {
            //1º passo: carregar o driver
            Class.forName("com.mysql.jdbc.Driver");
            //2º passo: fazer a conexão
            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiago", usuario, senha);
            //3º passo: gerar a query
            String sql = "update agenda set nome=?, idade=?, endereco=?, telefone=?, celular=? where nome=?";
            //4º passo: criar o PreparedStatement
            PreparedStatement ps = conexao.prepareStatement(sql);
            //5º passo: adicionar os valores no PreparedStatement
            ps.setString(1, nome);
            if (idade.equals("")) {
                ps.setInt(2, 0);
            } else {
                ps.setInt(2, Integer.parseInt(idade));
            }
            ps.setString(3, endereco);
            ps.setString(4, telefone);
            ps.setString(5, celular);
            ps.setString(6, nomePesquisado);
            //6º passo: executar o PreparedStatement
            ps.executeUpdate();
            //7º passo: fechar o PreparedStatement
            ps.close();
            //8º passo: fechar a conexão
            conexao.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro: " + ex);
        }

    }
    
    public void deletarContato(String usuario, String senha, String nome, String telefone){
        try{
            //1º passo: carregar a classe JDBC
        Class.forName("com.mysql.jdbc.Driver");
        //2º passo: conexao
        Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiago", usuario, senha);
        //3º passo: preparar a query
        String sql = "delete from agenda where nome=? and telefone=?";
        //4º passo: criar o PreparedStatement
        PreparedStatement ps = conexao.prepareStatement(sql);
        //5ª passo: adicionar parâmetros no PreparedStatement
        ps.setString(1, nome);
        ps.setString(2, telefone);
        //6ª passo: executar o PreparedStatement
        ps.executeUpdate();
        //7ª passo: fechar PreparedStatement
        ps.close();
        //8ª passo: fechar conexão
        conexao.close();
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println("Erro: "+ex);
        }
        
    }
}
