package hotel_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AccompanyingGuestForm extends JFrame {
	public static AccompanyingGuestForm instance;
	
	private JPanel contentPane;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField accompaniedGuestIDTextField;
	private JTextField phoneTextField;
	private JTextField emailTextField;
	private JTextField companyTextField;
	private JLabel accompaniedGuestNameLabel;
	private JScrollPane scrollPane;
	private JTable accGuestsTable;
	private int accompanying;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AccompanyingGuestForm frame = new AccompanyingGuestForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	GuestManager accGuestManager = new GuestManager();
	/**
	 * Create the frame.
	 */
//	public AccompanyingGuestForm() {
//		//create an empty frame, because this form is supposed to be initiated from ManageReservationsForm only.
//	}
	
	@SuppressWarnings("serial")
	private AccompanyingGuestForm(int bookingGuestID) {
		super("Accompanying Guests");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 248, 245));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPane = new JPanel();
		headerPane.setBounds(0, 0, 800, 70);
		headerPane.setBackground(new Color(47, 72, 88));
		contentPane.add(headerPane);
		headerPane.setLayout(null);
		
		JLabel headerLabel = new JLabel("ACCOMPANYING GUESTS");
		headerLabel.setBounds(273, 23, 326, 25);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setForeground(Color.WHITE);
		headerPane.add(headerLabel);
		
		JLabel idLabel = new JLabel("Accompanies:");
		idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		idLabel.setBounds(30, 170, 130, 16);
		contentPane.add(idLabel);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		firstNameTextField.setBounds(145, 95, 165, 25);
		contentPane.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(145, 130, 165, 25);
		contentPane.add(lastNameTextField);
		
		accompaniedGuestIDTextField = new JTextField();
		accompaniedGuestIDTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		accompaniedGuestIDTextField.setColumns(10);
		accompaniedGuestIDTextField.setBounds(145, 165, 165, 25);
		contentPane.add(accompaniedGuestIDTextField);
		
		accompaniedGuestNameLabel = new JLabel("New label");
		accompaniedGuestNameLabel.setBounds(148, 195, 160, 16);
		contentPane.add(accompaniedGuestNameLabel);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		firstNameLabel.setBounds(30, 100, 90, 16);
		contentPane.add(firstNameLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		phoneTextField.setColumns(10);
		phoneTextField.setBounds(422, 95, 165, 25);
		contentPane.add(phoneTextField);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		emailTextField.setColumns(10);
		emailTextField.setBounds(422, 130, 165, 25);
		contentPane.add(emailTextField);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lastNameLabel.setBounds(30, 135, 90, 16);
		contentPane.add(lastNameLabel);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		phoneLabel.setBounds(335, 100, 90, 16);
		contentPane.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		emailLabel.setBounds(335, 135, 90, 16);
		contentPane.add(emailLabel);
		
		companyTextField = new JTextField();
		companyTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		companyTextField.setColumns(10);
		companyTextField.setBounds(422, 165, 165, 25);
		contentPane.add(companyTextField);
		
		JLabel companyLabel = new JLabel("Company:");
		companyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		companyLabel.setBounds(335, 170, 90, 20);
		contentPane.add(companyLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 300, 730, 300);
		contentPane.add(scrollPane);
		
		accGuestsTable = new JTable();
		scrollPane.setViewportView(accGuestsTable);
		accGuestsTable.setGridColor(Color.GRAY);
		accGuestsTable.setModel(new DefaultTableModel(
			new Object[][] { }, // data rows - which will be added using fillGuestsTable function
			new String[] {"ID", "First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"} //column names
		) {
			public boolean isCellEditable(int row, int column){
				return false;
			};
		});
		
//		try {		
//			bookingGuestID = Integer.parseInt(accompaniedGuestIDTextField.getText());
			accGuestManager.fillAccompanyingGuestsTable(accGuestsTable, bookingGuestID);
//		} catch (NumberFormatException e1) {
//			JOptionPane.showMessageDialog(contentPane, "Make sure the right guest is selected to accompany.", "Missing Booking Guest's ID", JOptionPane.WARNING_MESSAGE);
//		}
		
		
		JButton attachGuestBtn = new JButton("Attach Guest");
		attachGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		attachGuestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fname = firstNameTextField.getText();
				String lname = lastNameTextField.getText();
				String phone = phoneTextField.getText();
				String email = emailTextField.getText();
				String company = companyTextField.getText();
			try {
				accompanying = Integer.parseInt(accompaniedGuestIDTextField.getText());
				
				if(fname.trim().equals("") || lname.trim().equals("") || accompanying == 0) {
					JOptionPane.showMessageDialog(contentPane, "The Accompanying Guest's First Name and Last Name and the Booking Guest's ID Required", "Empty Field Error", JOptionPane.WARNING_MESSAGE);
				} 
				else {
					if(accGuestManager.addAccompanyingGuest(fname, lname, phone, email, company, accompanying)) {
						accGuestsTable.setModel(new DefaultTableModel(null, new Object[] {"ID", "First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"}));
						accGuestManager.fillAccompanyingGuestsTable(accGuestsTable, accompanying);
						JOptionPane.showMessageDialog(contentPane, "Guest Attached Succesfully", "Attach Guest", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Attach Guest", "Add Guest Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(contentPane, "Enter ID of Guest to Accompany", "Booking Guest ID Missing Error", JOptionPane.ERROR_MESSAGE);
			} catch (NoGuestToAccompanyException e2) {
				JOptionPane.showMessageDialog(contentPane, "The guest to be accompanied does not have any reservation.", "Booking Guest Not Found Error", JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		
		attachGuestBtn.setBounds(620, 95, 148, 29);
		attachGuestBtn.setBackground(new Color(219, 232, 228));
		attachGuestBtn.setOpaque(true);
		attachGuestBtn.setBorderPainted(false);
		contentPane.add(attachGuestBtn);
		
		JButton detachGuestBtn = new JButton("Detach Guest");
		detachGuestBtn.setOpaque(true);
		detachGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		detachGuestBtn.setBorderPainted(false);
		detachGuestBtn.setBackground(new Color(219, 232, 228));
		detachGuestBtn.setBounds(620, 130, 148, 29);
		contentPane.add(detachGuestBtn);
	}


	public void getAccompaniedGuest(int bookingGuestID) {
		accompaniedGuestIDTextField.setText(((Integer)bookingGuestID).toString());
		
		MY_CONNECTION my_connection = new MY_CONNECTION();
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT CONCAT(`first_name`, ', ', `last_name`) FROM `guests` WHERE `id`=?"; // MySQL doesn't support + or || for concatenation. "SELECT `first_name` + ' ' + `last_name` FROM `guests` WHERE `id`=?" wouldn't work.
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setInt(1, bookingGuestID);
			rs = ps.executeQuery();
			while(rs.next()) {
				accompaniedGuestNameLabel.setText(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static AccompanyingGuestForm getInstance(int bookingGuestID) {
		if (instance == null)
			  instance = new AccompanyingGuestForm(bookingGuestID);
		  return instance;
	}
	
	public void cleanup() {
		 instance = null;
	  }
}
