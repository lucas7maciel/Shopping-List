import java.sql.*;

class ConnectionFactory {
    public Connection Connect(String user, String pasw) {
        try {
            Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/shoppinglist",
            user, pasw);

            return con;
        } catch (Exception e) {
            System.out.println("Connection error\n");
            System.out.println(e);
            return null;
        }
    }

    public Statement CreateStatement(Connection con) {
        try { return con.createStatement();}
        catch (Exception e) { return null; }
    }
}
