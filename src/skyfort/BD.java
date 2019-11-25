/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyfort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jeronimo
 */
public class BD extends JPanel {

    /**
     * Pega todas as mensagens geradas pelos m�todos ou pelos erros de SQL.
     *
     * @return o conte�do da mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String mensagem = "";

    public boolean conectar() {
        //String servidor = "jdbc:mysql://localhost:3306/skyfort"; //Versao antiga
        String servidor = "jdbc:mysql://127.0.0.1:3306/skyfort?useSSL=false&useTimezone=true&serverTimezone=UTC";//versao nova
        String usuario = "root";
        String senha = "";
        String driver = "com.mysql.cj.jdbc.Driver"; //Vers�o antiga (5.1.37)
        //String driver = "com.mysql.cj.jdbc.Driver";//versao nova
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(servidor, usuario, senha);
            this.mensagem = "Conectado ao banco de dados!";
            System.out.println("Conectado ao banco de dados! ");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
            return false;
        }
    }

    public boolean conectado() {
        return this.connection != null;
    }

    /**
     * Lista todas as linhas de uma tabela, de acordo com a query SQL
     * especificada.
     *
     * @param model uma tabela modelo do tipo DefaultTableModel para ser usada
     * em conjunto com o componente JTable
     */
    public ResultSet listarRefrigeradores(){
         try {
            String query = "SELECT modelo,consumo FROM skyfort.televisores ORDER BY modelo";
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
        return this.resultSet;
    }
    
    public ResultSet listarTelevisores(){
              try {
            String query = "SELECT modelo,consumo FROM skyfort.televisores ORDER BY modelo";
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
        return this.resultSet;
    }

    public ResultSet listarGeladeiras() {

        try {
            String query = "SELECT modelo,consumo FROM skyfort.geladeiras ORDER BY modelo";
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
        return this.resultSet;
    }

    public void listar() {
        try {
            String query = "SELECT * FROM contato ORDER BY nome";
            this.resultSet = this.statement.executeQuery(query);
            this.statement = this.connection.createStatement();
            while (this.resultSet.next()) {
                System.out.println("ID: " + this.resultSet.getString("id") + " - Nome: " + this.resultSet.getString("nome") + " - Telefone: " + this.resultSet.getString("telefone"));
            }
            this.mensagem = "dados ordenados pelo campo 'Nome'";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
    }

    public void inserir(String nome, String telefone) {
        try {
            String query = "INSERT INTO contato(nome, telefone) VALUES('" + nome + "','" + telefone + "');";
            this.statement.executeUpdate(query);
            this.mensagem = "Dados inseridos!";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
    }

    public void atualizar(String id, String nome, String telefone) {
        try {
            String query = "UPDATE contato SET nome = '" + nome + "', telefone = '" + telefone + "' WHERE id ='" + id + "' ";
            this.statement.executeUpdate(query);
            this.mensagem = "Dados atualizados!";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }

    }

    public void pesquisar(DefaultTableModel model, String palavra) {
        try {
            String query = "SELECT * FROM contato WHERE nome LIKE '%" + palavra + "%'";
            this.resultSet = this.statement.executeQuery(query);
            this.statement = this.connection.createStatement();
            model.setNumRows(0);
            while (this.resultSet.next()) {
                model.addRow(new Object[]{this.resultSet.getString("id"), this.resultSet.getString("nome"), this.resultSet.getString("telefone")});
            }
            this.mensagem = "Dados listados para a pesquisa (" + palavra + ")";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }

    }

    public void apagar(String id) {
        try {
            String query = "DELETE FROM contato WHERE id= " + id + ";";
            this.statement.executeUpdate(query);
            this.mensagem = "Dados apagados!";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
    }

    public void desconectar() {
        try {
            this.connection.close();
            this.mensagem = "Desconectado do banco de dados";
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            this.mensagem = "Erro: " + e.getMessage();
        }
    }
}
