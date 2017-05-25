import java.sql.*;

class TesteSQL{
	public static void main(String args[]){
		try{
			//passo 1: carregar o Driver
			Class.forName("com.mysql.jdbc.Driver");
			//passo 2: conexao
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiago","tiago","6sfm3jms");
		}catch(ClassNotFoundException | SQLException ex){
			System.out.println("Erro: "+ex);
		}
		
		
	}
}