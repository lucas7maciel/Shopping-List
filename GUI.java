import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class GUI {
    static JPanel logP, regP, acAP, acBP;
	public JTable table;

    static GridBagConstraints g;

    public void buildGUI() {
        GUI gui = new GUI();
        CommandsDAO C = new CommandsDAO();
        g = new GridBagConstraints();

        JFrame frame = new JFrame("Shopping List");
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Color.decode("#6E749C"));

        logP = gui.loginPanel(C, frame);
        regP = gui.regstPanel(C, frame);
		acAP = gui.accAPanel(C, frame);
		acBP = gui.accBPanel(C, frame);

        frame.setSize(500, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(logP);
    }

    private void Switch(JFrame frame, JPanel panel1, JPanel panel2) {
        frame.remove(panel1);
    	frame.add(panel2);

		frame.validate();
		frame.repaint();
    }

	private GridBagConstraints Pos(int x, int y, int width) {
		g.gridx = x;
		g.gridy = y;
		g.gridwidth = width;
		
		return g;
	}

	private JButton setButton(String title) {
		JButton button = new JButton(title);

		button.setBackground(Color.decode("#BCC2E8"));
		button.setFocusPainted(false);

		return button;
	}

    private JPanel loginPanel(CommandsDAO C, JFrame F) {
        JPanel loginP = new JPanel();
		loginP.setLayout(new GridBagLayout());
		loginP.setBackground(Color.WHITE);

		//fill the panel
		JLabel titleLa = new JLabel("Login");
		titleLa.setFont(new Font("Verdana", Font.PLAIN, 45));
		g.insets = new Insets(10, 5, 30, 5);
		loginP.add(titleLa, Pos(0, 0, 2));

		JLabel userLa = new JLabel("Username");
		g.insets = new Insets(1, 2, 1, 2);
		loginP.add(userLa, Pos(0, 1, 1));

		JTextField userFi = new JTextField(10);
		loginP.add(userFi, Pos(1, 1, 1));

		JLabel paswLa = new JLabel("Password");
		loginP.add(paswLa, Pos(0, 2, 1));

		JPasswordField paswFi = new JPasswordField(10);
		loginP.add(paswFi, Pos(1, 2, 1));

		JCheckBox showCB = new JCheckBox("Show password");
        showCB.setFocusPainted(false);
		showCB.setBackground(Color.WHITE);
		g.anchor = GridBagConstraints.WEST;
		loginP.add(showCB, Pos(0, 3, 2));

		JLabel messagesLa = new JLabel("Welcome!");
		g.anchor = GridBagConstraints.CENTER;
		loginP.add(messagesLa, Pos(0, 4, 2));

		JButton loginBu = setButton("Login");
		g.insets = new Insets(25, 3, 0, 3);
		loginP.add(loginBu, Pos(0, 5, 2));
		
		JButton signUpBu = setButton("Sign Up");
		g.insets = new Insets(20, 3, 30, 3);
		loginP.add(signUpBu, Pos(0, 6, 2));

		showCB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (showCB.isSelected()) {
					paswFi.setEchoChar((char)0);
				} 

				else { paswFi.setEchoChar('•'); }
			}
		});

		loginBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                messagesLa.setText(C.Login(
				userFi, paswFi, table));

				if (messagesLa.getText() == "") {
					Switch(F, logP, acAP);
				}
			}
		});
		
		signUpBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                Switch(F, logP, regP);
			}
		});
		
		return loginP;
    }

    private JPanel regstPanel (CommandsDAO C, JFrame F) {
        JPanel registP = new JPanel();
		registP.setLayout(new GridBagLayout());
		registP.setBackground(Color.WHITE);

		//fill the panel
		JLabel titleLa = new JLabel("Sign Up");
		titleLa.setFont(new Font("Verdana", Font.PLAIN, 45));
		g.insets = new Insets(10, 2, 30, 2);
		registP.add(titleLa, Pos(0, 0, 2));

		JLabel userLa = new JLabel("Username");
		g.insets = new Insets(1, 2, 1, 2);
		registP.add(userLa, Pos(0, 1, 1));

		JLabel dateLa = new JLabel("Birth");
		registP.add(dateLa, Pos(0, 2, 1));

		JLabel paswLa = new JLabel("Password");
		registP.add(paswLa, Pos(0, 3, 1));

		JLabel confPaswLa = new JLabel("Confirm Password");
		registP.add(confPaswLa, Pos(0, 4, 1));

		JLabel gendrLa = new JLabel("Gender");
		registP.add(gendrLa, Pos(0, 5, 1));

		JTextField userFi = new JTextField(10);
		registP.add(userFi, Pos(1, 1, 1));

		JTextField dateFi = new JTextField(10);
		registP.add(dateFi, Pos(1, 2, 1));

		JTextField paswFi = new JTextField(10);
		registP.add(paswFi, Pos(1, 3, 1));

		JTextField confPaswFi = new JTextField(10);
		registP.add(confPaswFi, Pos(1, 4, 1));

		String[] genderS = {
			"Male", "Female",
			"Non-Binary", "Null"
		};

		JComboBox<String> genderCo = new JComboBox<>(genderS);
		genderCo.setSelectedIndex(3);
		g.fill = GridBagConstraints.HORIZONTAL;
		registP.add(genderCo, Pos(1, 5, 2));

		JLabel messagesLa = new JLabel("Enter your data");
		g.fill = GridBagConstraints.NONE;
		registP.add(messagesLa, Pos(0, 6, 2));

		JButton backBu = setButton("Back");
		g.insets = new Insets(25, 3, 15, 3);
		registP.add(backBu, Pos(0, 7, 1));

		JButton signUpBu = setButton("Sign Up");
		registP.add(signUpBu, Pos(1, 7, 1));

		backBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                Switch(F, regP, logP);
			}
		});
		
		signUpBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		        messagesLa.setText(C.Register(
				userFi, dateFi, paswFi, confPaswFi, genderCo));
			}
		});
		
		return registP;
    }

	private JPanel accAPanel(CommandsDAO C, JFrame F) {
		JPanel accAP = new JPanel();
		accAP.setLayout(new BorderLayout());
		accAP.setBackground(Color.decode("#BCC2E8"));

		//fill the panel
		JLabel titleLa = new JLabel("Shopping list", SwingConstants.CENTER);
		accAP.add(titleLa, BorderLayout.NORTH);

		table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(Color.decode("#BCC2E8"));
		accAP.add(scroll, BorderLayout.CENTER);

		JPanel subP = new JPanel();
		subP.setLayout(new GridBagLayout());
		subP.setBackground(Color.WHITE);

		JLabel nameLa = new JLabel("");
		g.insets = new Insets(1, 1, 1, 1);
		subP.add(nameLa, Pos(1, 0, 2));

		JLabel priceLa = new JLabel("Price");
		subP.add(priceLa, Pos(1, 1, 2));

		JTextField priceFi = new JTextField(10);
		subP.add(priceFi, Pos(1, 2, 2));

		JLabel messagesLa = new JLabel("Messages");
		subP.add(messagesLa, Pos(1, 3, 2));

		JButton editBu = setButton("Edit");
		subP.add(editBu, Pos(0, 4, 1));

		JButton delBu = setButton("Delete");
		subP.add(delBu, Pos(1, 4, 1));

		JButton addBu = setButton("Add Items");
		subP.add(addBu, Pos(2, 4, 1));

		JButton backBu = setButton("Back");
		subP.add(backBu, Pos(3, 4, 1));

		accAP.add(subP, BorderLayout.SOUTH);

		editBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		        messagesLa.setText(C.Alter
				(priceFi, nameLa, table));
			}
		});

		delBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		        messagesLa.setText(C.Del(nameLa, table));
			}
		});

		addBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		           Switch(F, acAP, acBP);;
			}
		});

		backBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		           Switch(F, acAP, logP);
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable table = (JTable)e.getSource();
					int row = table.getSelectedRow();

					String name = (String)table.getValueAt(row, 0);
					String price = (String)table.getValueAt(row, 1);

					nameLa.setText(name);
					priceFi.setText(price);
				}
			}
		});

		return accAP;
	}

	private JPanel accBPanel(CommandsDAO C, JFrame F) {
		JPanel acBP = new JPanel();
		acBP.setLayout(new GridBagLayout());
		acBP.setBackground(Color.WHITE);

		//fill the panel
		JLabel titleLa = new JLabel("Add Items");
		titleLa.setFont(new Font("Verdana", Font.PLAIN, 45));
		g.insets = new Insets(10, 2, 20, 2);
		acBP.add(titleLa, Pos(1, 0, 1));

		JLabel nameLa = new JLabel("Name");
		g.insets = new Insets(1, 5, 1, 5);
		acBP.add(nameLa, Pos(1, 1, 1));

		JTextField nameFi = new JTextField(10);
		acBP.add(nameFi, Pos(1, 2, 1));

		JLabel priceLa = new JLabel("Price");
		g.insets = new Insets(4, 5, 4, 5);
		acBP.add(priceLa, Pos(1, 3, 1));

		JTextField priceFi = new JTextField(10);
		acBP.add(priceFi, Pos(1, 4, 1));

		JLabel messagesLa = new JLabel("Messages", SwingConstants.CENTER);
		g.insets = new Insets(1, 5, 1, 5);
		acBP.add(messagesLa, Pos(0, 5, 3));

		JButton addBu = setButton("Add Item");
		g.insets = new Insets(25, 5, 15, 5);
		acBP.add(addBu, Pos(1, 6, 1));

		JButton backBu = setButton("Back");
		g.insets = new Insets(7, 5, 5, 5);
		acBP.add(backBu, Pos(1, 7, 1));

		addBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		           messagesLa.setText(C.Add
				   (nameFi, priceFi));
			}
		});

		backBu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				C.LoadTable(table);
                Switch(F, acBP, acAP);
			}
		});

		return acBP;
	}

}