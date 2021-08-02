public class Main {
    //If you haven't run this code before, execute 'createDB.sql' once
    public static void main (String[] args) {
        String user = "Your_MySQL_User";       //usually 'root'
        String pasw = "Your_MySQL_Password";

        CommandsDAO Com = new CommandsDAO();
        Com.Run(user, pasw);
    }
}
