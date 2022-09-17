package hotel_system;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginForm {

	private JFrame LoginFrame;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm loginForm = new LoginForm();
					loginForm.LoginFrame.setVisible(true);
					loginForm.LoginFrame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginForm() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LoginFrame = new JFrame();
		LoginFrame.setBounds(100, 100, 450, 300);
		LoginFrame.setResizable(false);
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginFrame.getContentPane().setLayout(null);
		
		JPanel rootPane = new JPanel();
		rootPane.setBounds(0, 0, 450, 272);
		rootPane.setBackground(new Color(250, 248, 245));
		
		JPanel headerPane = new JPanel();
		headerPane.setBackground(new Color(47, 72, 88));
		
		JLabel usernameLabel = new JLabel("Username:");
		
		JLabel passwordLabel = new JLabel("Password:");
		
		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		
		JButton loginBtn = new JButton("Log in");
		loginBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		loginBtn.addActionListener(new ActionListener() { //instead of having the class LoginForm implement an ActionListener interface and writing btnLogin.addActionListener(this) with "this" referring to an instance of this LoginForm class, in other words an instance of the class containing the event handler (ActionListener), and then overriding the method "actionPerformed" of the ActionListener interface separately outside the scope of the frame-defining code --> An instance of the interface ActionListener can be created and passed directly as parameter into addActionListener() method, and the method actionPerformed can be overridden within this ActionListener instance.)
			public void actionPerformed(ActionEvent e) {
				PreparedStatement ps;
				ResultSet rs;
				
				String username = textFieldUsername.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if (username.trim().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "Enter Your Username to Log In", "Empty Username", 2);
					// JOptionPane.showMessageDialog(panel_name, ...) displays the dialog frame at the center of the selected panel
					// JOptionPane.showMessageDialog(null, ...) displays the dialog frame at the center of the entire screen
				}
				else if (password.trim().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "Enter Your Password to Log In", "Empty Password", 2);
				} 
				else {
					MY_CONNECTION myconnection = new MY_CONNECTION();
					
					String selectQuery = "SELECT * FROM `users` WHERE `username`=? AND `password`=?";
					try {
						ps = myconnection.createConnection().prepareStatement(selectQuery);
						
						ps.setString(1, username);
						ps.setString(2, password);
						
						rs = ps.executeQuery();
// executeQuery if the query returns only one ResultSet (such as a SELECT SQL statement), executeUpdate if the query does not return a ResultSet (such as an UPDATE SQL statement), or execute if the query might return more than one ResultSet object. 
						
						if(rs.next()) {
							//if this user exists, open the main form and close the login form
							MainForm mainform = new MainForm();
							mainform.setVisible(true);
							mainform.pack();
							mainform.setLocationRelativeTo(null); //setLocationRelativeTo(null) is used to center the GUI on the screen.
							LoginFrame.dispose();
							mainform.setExtendedState(JFrame.MAXIMIZED_BOTH);
						} 
						else {
							//if this user enters the wrong information
							JOptionPane.showMessageDialog(rootPane, "Wrong UserName Or Password", "Login Error", 2);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				}
			}
		});
		loginBtn.setBackground(new Color(219, 232, 228));
		loginBtn.setOpaque(true);
		loginBtn.setBorderPainted(false);
		
		passwordField = new JPasswordField();
		GroupLayout gl_rootPane = new GroupLayout(rootPane);
		gl_rootPane.setHorizontalGroup(
			gl_rootPane.createParallelGroup(Alignment.LEADING)
				.addComponent(headerPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(gl_rootPane.createSequentialGroup()
					.addGap(43)
					.addGroup(gl_rootPane.createParallelGroup(Alignment.LEADING)
						.addComponent(usernameLabel)
						.addComponent(passwordLabel))
					.addGap(18)
					.addGroup(gl_rootPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(passwordField, 248, 248, 248)
						.addComponent(textFieldUsername, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(75, Short.MAX_VALUE))
				.addGroup(gl_rootPane.createSequentialGroup()
					.addGap(160)
					.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		gl_rootPane.setVerticalGroup(
			gl_rootPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rootPane.createSequentialGroup()
					.addComponent(headerPane, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_rootPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_rootPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		headerPane.setLayout(null);
		
		JLabel headerLabel = new JLabel("USER LOGIN");
		headerLabel.setBounds(161, 27, 131, 26);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setForeground(Color.WHITE);
		headerPane.add(headerLabel);
		rootPane.setLayout(gl_rootPane);
		LoginFrame.getContentPane().add(rootPane);
	}
}
