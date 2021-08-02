import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class CommandsDAO {
    static Statement s;
    private String nick;

    public void Run (String user, String password) {
        ConnectionFactory conFac = new ConnectionFactory();
        Connection con = conFac.Connect(user, password);

        if (con == null) {
            return;}

        s = conFac.CreateStatement(con);

        GUI G = new GUI();
        G.buildGUI();
    }

    public String Login(JTextField userFi, JTextField paswFi, JTable T) {
        nick = userFi.getText();
        String pasw = paswFi.getText();

        try {
            ResultSet rs = s.executeQuery(String.format
            ("Select * From user Where Nick = '%s'", nick));
            
            if (!rs.next()) {
                return "This user doesn't exist";}

            else if (!pasw.equals(rs.getString("Password"))) {
                return "Incorrect password";}

            LoadTable(T);
            return "";
    
        } catch (Exception e) {
            System.out.println(e);
            return "Login error";}
    }

    public String Register(JTextField userFi, JTextField dateFi, JTextField paswFi, JTextField cPswFi, JComboBox<String> gendCo) {
        String nick = userFi.getText();
        String date = dateFi.getText().replace("/", "-");
        String pasw = paswFi.getText();
        String cpsw = cPswFi.getText();
        String gend = gendCo.getSelectedItem().toString();

        //checks if the nick meets all the requirements
        Boolean checkNi = CheckNick(nick);
        Boolean checkDa = CheckDate(date);

        if (checkNi == null) {
            return "Error checking nicks";
        }

        else if (!checkNi) {
            return "This nick already exists";
        }

        else if (nick.length() < 3 || nick.length() > 10) {
            return "Nick 3 to 10 characters";
        }

        else if (date.length() != 10) {
            return "Date must be yyyy/mm/dd";
        }

        else if (!checkDa) {
            return "Invalid date";
        }

        else if (pasw.length() < 3 || pasw.length() > 10) {
            return "Password 3 to 10 characters";
        }

        else if (!pasw.equals(cpsw)) {
            return "Passwords don't match";
        }

        else if (!gend.equals("Null")) {
            gend = "'" + gend + "'";
        }

        //actual record
        try {
            s.executeUpdate(String.format("Insert Into user "
            + "(Nick, Password, Birth, Gender) Values ('%s', '%s', '%s', %s)"
            , nick, pasw, date, gend));

            return "Successfully registered!";

        } catch (Exception e) {
            System.out.println(e);
            return "Record couldn't be done";}
    }

    private Boolean CheckNick(String nick) {
        try { 
            ResultSet rs = s.executeQuery(String.format
            ("Select * From user Where Nick = '%s'", nick));

            if (rs.next()) {
                return false;}

        } catch (Exception e) {
            System.out.println(e);
            return null;}

        return true;
    }

    private Boolean CheckDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date.trim());

        } catch (ParseException e) {
            System.out.println(e);
            return false;}
        
        return true;
    }

    public void LoadTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        model.addColumn("Item");
        model.addColumn("Price");
        model.setRowCount(0);

        try {
            List<ItemsModel> items = GetTableRows();

            for (ItemsModel item : items) {
                String name = item.getName();
                String price = item.getPrice();

                if (price == null || price.equals("0.00")) {
                    price = "Free";
                }

                model.addRow(new Object[] { 
                    name,
                    price
                });
            }

        } catch (Exception e) {
            System.out.println(e);}
    }

    private List<ItemsModel> GetTableRows () {
        List<ItemsModel> itemsList = new ArrayList<>();
        
        try {
            ResultSet rs = s.executeQuery(String.format(
            "Select * From items Where Owner = '%s'", nick));

            while (rs.next()) {
                ItemsModel cont = new ItemsModel();

                cont.setOwner(nick);
                cont.setName(rs.getString("Name"));
                cont.setPrice(rs.getString("Price"));

                itemsList.add(cont);
            }

            return itemsList;

        } catch (Exception e) {
            System.out.println(e);
            return null;}
    }

    public String Add(JTextField nameFi, JTextField priceFi) {
        String name = nameFi.getText();
        String price = priceFi.getText();

        price = "'" + price + "'";
        price = price.replace(",", ".");
        price = price.toLowerCase();

        if (name.length() == 0) {
            return "Empty field";
        }

        if (name.length() > 20) {
            return "Name up to 20 characters";
        }

        else if (price.length() > 18) {
            return "Price up to 18 characters";
        }

        else if (price.equals("'free'")) {
            price = "'0.00'";
        }

        else if (price.length() == 2) {
            price = "NULL";
        }

        try {
            s.executeUpdate(String.format("Insert Into items "
            + "(Owner, Name, Price) Values ('%s', '%s', %s);", 
            nick, name, price));

            return "Item added!";

        } catch (Exception e) {
            System.out.println(e);
            return "Adding error";}
    }

    public String Alter(JTextField priceFi, JLabel nameLa, JTable T) {
        String price = priceFi.getText();
        String name = nameLa.getText();

        if (price.toLowerCase().equals("free")) {
            price = "0.00";
        }

        try {
            s.executeUpdate(String.format("Update items Set "
            + "Price = '%s' Where Name = '%s' and Owner = '%s'",
            price, name, nick));
            
            LoadTable(T);

            return "Price Changed!";

        } catch (Exception e) {
            System.out.println(e);
            return "Error";}
    }

    public String Del(JLabel nameLa, JTable T) {
        String name = nameLa.getText();

        try {
            s.executeUpdate(String.format("Delete From" +
            " items Where Name = '%s' and Owner = '%s'",
            name, nick));
            
            nameLa.setText("");
            LoadTable(T);

            return "Item deleted";

        } catch (Exception e) {
            System.out.println(e);
            return "Error";}
    }

}