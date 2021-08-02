import java.sql.*;

class ConnectionFactory {
    public Connection Connect(String user, String pasw) {
        try {
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/shoppinglist",
            user, pasw);

            return con;
        } catch (Exception e) {
            System.out.println("Conexão não pôde ser feita\n");
            System.out.println("Erro: " + e);
            return null;
        }
    }

    public Statement CreateStatement(Connection con) {
        try { return con.createStatement();}
        catch (Exception e) { return null; }
    }
}