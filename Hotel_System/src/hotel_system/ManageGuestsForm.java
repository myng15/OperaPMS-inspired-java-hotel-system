package hotel_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class ManageGuestsForm extends JFrame {

	private JPanel contentPane;
	private JTextField idTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField phoneTextField;
	private JTextField emailTextField;
	private JTextField companyTextField;
//	private JComboBox channelBox;
	private JTable guestsTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageGuestsForm frame = new ManageGuestsForm();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	GuestManager guestManager = new GuestManager();
	private JTextField accompanyTextField;
	
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ManageGuestsForm() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //or this.set[...];
		setBounds(100, 100, 800, 650);
		setResizable(false); 
		setLocationRelativeTo(null);
		
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
		
		JLabel headerLabel = new JLabel("MANAGE GUESTS");
		headerLabel.setBounds(325, 22, 186, 25);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setForeground(Color.WHITE);
		headerPane.add(headerLabel);
		
		JLabel idLabel = new JLabel("ID:");
		idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		idLabel.setBounds(30, 100, 61, 16);
		contentPane.add(idLabel);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		firstNameLabel.setBounds(30, 135, 90, 16);
		contentPane.add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lastNameLabel.setBounds(30, 170, 90, 16);
		contentPane.add(lastNameLabel);
		
		JLabel accompanyingLabel = new JLabel("Accompanies:");
		accompanyingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		accompanyingLabel.setBounds(30, 205, 119, 16);
		contentPane.add(accompanyingLabel);
		
		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		phoneLabel.setBounds(328, 100, 61, 16);
		contentPane.add(phoneLabel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		emailLabel.setBounds(328, 135, 61, 16);
		contentPane.add(emailLabel);
		
		idTextField = new JTextField();
		idTextField.setEditable(false);
		idTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		idTextField.setBounds(135, 95, 165, 25);
		contentPane.add(idTextField);
		idTextField.setColumns(10);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		firstNameTextField.setColumns(10);
		firstNameTextField.setBounds(135, 130, 165, 25);
		contentPane.add(firstNameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(135, 165, 165, 25);
		contentPane.add(lastNameTextField);
		
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
		
		accompanyTextField = new JTextField();
		accompanyTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		accompanyTextField.setColumns(10);
		accompanyTextField.setBounds(135, 200, 165, 25);
		contentPane.add(accompanyTextField);
		
		JButton addGuestBtn = new JButton("Add Guest");
		addGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		addGuestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = firstNameTextField.getText();
				String lname = lastNameTextField.getText();
				String phone = phoneTextField.getText();
				String email = emailTextField.getText();
				String company = companyTextField.getText();
				int accompanying = 0;
				
				Guest guest = new Guest.Builder(fname, lname, phone)
									   .email(email)
									   .company(company)
									   .accompanying(accompanying)
									   .build();
				if(fname.trim().equals("") || lname.trim().equals("") || phone.trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "First Name, Last Name and Phone Nummer Required", "Empty Field Error", JOptionPane.WARNING_MESSAGE);
				} 
				else {
					try {
						accompanying = Integer.parseInt(accompanyTextField.getText());
						if(guestManager.addGuest(guest/*fname, lname, phone, email, company*/)) {
							guestsTable.setModel(new DefaultTableModel(null, new Object[] {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"}));
							guestManager.fillGuestsTable(guestsTable);
							JOptionPane.showMessageDialog(contentPane, "New Guest Added Succesfully", "Add Guest", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(contentPane, "Failed to Add New Guest", "Add Guest Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(contentPane, "Enter ID of Guest to Accompany (Enter 0 for Booking Guest)", "ID Missing Error", JOptionPane.WARNING_MESSAGE);
					} catch (IllegalArgumentException e2) {
						JOptionPane.showMessageDialog(contentPane, "First Name, Last Name and Phone Nummer Required", "Empty Field Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			
			}
		});
		
		addGuestBtn.setBounds(620, 95, 142, 29);
		addGuestBtn.setBackground(new Color(219, 232, 228));
		addGuestBtn.setOpaque(true);
		addGuestBtn.setBorderPainted(false);
		contentPane.add(addGuestBtn);
		
		JButton editGuestBtn = new JButton("Edit");
		editGuestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String fname = firstNameTextField.getText();
				String lname = lastNameTextField.getText();
				String phone = phoneTextField.getText();
				String email = emailTextField.getText();
				String company = companyTextField.getText();

				if(fname.trim().equals("") && lname.trim().equals("") && phone.trim().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "No Guest Selected To Edit", "Empty Field Error", JOptionPane.WARNING_MESSAGE);
				} 
				else {
					try {
						int id = Integer.parseInt(idTextField.getText()); //or Integer.valueOf(...) 
						int accompanying = Integer.parseInt(accompanyTextField.getText());
						
						Guest guest = new Guest.Builder(fname, lname, phone)
								   .id(id)
								   .email(email)
								   .company(company)
								   .accompanying(accompanying)
								   .build();
						
						if(guestManager.editGuest(guest/*id, fname, lname, phone, email, company*/)) {
							JOptionPane.showMessageDialog(contentPane, "Guest Data Updated Succesfully", "Edit Guest", JOptionPane.INFORMATION_MESSAGE);
							guestsTable.setModel(new DefaultTableModel(null, new Object[] {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"}));
							guestManager.fillGuestsTable(guestsTable);
						}
						else {
							JOptionPane.showMessageDialog(contentPane, "Failed to Update Guest Data", "Edit Guest Error", JOptionPane.ERROR_MESSAGE);
						} 
					}
					catch (NumberFormatException ex){
						JOptionPane.showMessageDialog(contentPane, "Guest ID and ID of Guest to Accompany Required (Enter 0 for Booking Guest)", "ID Missing Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		editGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		editGuestBtn.setBounds(620, 130, 142, 29);
		editGuestBtn.setBackground(new Color(219, 232, 228));
		editGuestBtn.setOpaque(true);
		editGuestBtn.setBorderPainted(false);
		contentPane.add(editGuestBtn);
		
		JButton removeGuestBtn = new JButton("Remove");
		removeGuestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				int id = 0;
				
				try {
					id = Integer.parseInt(idTextField.getText()); //or Integer.valueOf(...) 
					
					if(guestManager.removeGuest(id)) {
						JOptionPane.showMessageDialog(contentPane, "Guest Data Deleted Succesfully", "Remove Guest", JOptionPane.INFORMATION_MESSAGE);
						guestsTable.setModel(new DefaultTableModel(null, new Object[] {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"}));
						guestManager.fillGuestsTable(guestsTable);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Delete Guest Data", "Remove Guest Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(contentPane, "Enter Guest ID Number", "ID Missing Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		removeGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		removeGuestBtn.setBounds(620, 165, 142, 29);
		removeGuestBtn.setBackground(new Color(219, 232, 228));
		removeGuestBtn.setOpaque(true);
		removeGuestBtn.setBorderPainted(false);
		contentPane.add(removeGuestBtn);
		
		JButton clearSearchBtn = new JButton("Clear Search");
		clearSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTextField.setText("");
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				phoneTextField.setText("");
				emailTextField.setText("");
				companyTextField.setText("");
				accompanyTextField.setText("");
			}
		});
		
		clearSearchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		clearSearchBtn.setBounds(620, 200, 142, 29);
		clearSearchBtn.setBackground(new Color(219, 232, 228));
		clearSearchBtn.setOpaque(true);
		clearSearchBtn.setBorderPainted(false);
		
		Border border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
		clearSearchBtn.setBorder(border);
		
		contentPane.add(clearSearchBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 300, 730, 300);
		contentPane.add(scrollPane);
		
		guestsTable = new JTable();
		scrollPane.setViewportView(guestsTable);
		guestsTable.setGridColor(Color.GRAY);
		guestsTable.setModel(new DefaultTableModel(
			new Object[][] { }, // data rows - which will be added using fillGuestsTable function
			new String[] {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"} //column names
		) {
			/**
			 * 
			 */
			//make the jTable cells not editable - 1. Alternative

			public boolean isCellEditable(int row, int column){
				return false;
			};
		});
	
		//make the jTable cells not editable - 2. Alternative	
//		guestsTable.setDefaultEditor(Object.class, null);
		
		//populate guestsTable
		guestManager.fillGuestsTable(guestsTable);
		
		guestsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel)guestsTable.getModel();
				
				int rowIndex = guestsTable.getSelectedRow();
				
				//display data
				idTextField.setText(tableModel.getValueAt(rowIndex, 0).toString());
				firstNameTextField.setText(tableModel.getValueAt(rowIndex, 1).toString());
				lastNameTextField.setText(tableModel.getValueAt(rowIndex, 2).toString());
				phoneTextField.setText(tableModel.getValueAt(rowIndex, 3).toString());
				emailTextField.setText(tableModel.getValueAt(rowIndex, 4).toString());
				companyTextField.setText(tableModel.getValueAt(rowIndex, 5).toString());
				accompanyTextField.setText(tableModel.getValueAt(rowIndex, 6).toString());
			}
		});
		
//		JButton refreshBtn = new JButton("Refresh");
//		refreshBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// empty the table first so that data won't be duplicated
//				guestsTable.setModel(new DefaultTableModel(null, new Object[] {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Channel"}));
//				// populate the table
//				guestManager.fillGuestsTable(guestsTable);
//			}
//		});
//		refreshBtn.setOpaque(true);
//		refreshBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		refreshBtn.setBorderPainted(false);
//		refreshBtn.setBackground(new Color(219, 232, 228));
//		refreshBtn.setBounds(620, 235, 142, 29);
//		contentPane.add(refreshBtn);
		
		companyTextField = new JTextField();
		companyTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		companyTextField.setColumns(10);
		companyTextField.setBounds(422, 165, 165, 25);
		contentPane.add(companyTextField);
		
		JLabel companyLabel = new JLabel("Company:");
		companyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		companyLabel.setBounds(328, 170, 90, 20);
		contentPane.add(companyLabel);
		
		JButton newReservationBtn = new JButton("New Rsv.");
		newReservationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int bookingGuestID = Integer.parseInt(idTextField.getText());
					String bookingGuestName = firstNameTextField.getText() + " " + lastNameTextField.getText(); 
					ManageReservationsForm reservationForm = ManageReservationsForm.getInstance();
					reservationForm.setVisible(true);
					reservationForm.setLocationRelativeTo(null);
					reservationForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					reservationForm.getBookingGuest(bookingGuestID, bookingGuestName);
					dispose();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(contentPane, "Enter Guest ID Number", "ID Missing Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		newReservationBtn.setOpaque(true);
		newReservationBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newReservationBtn.setBorderPainted(false);
		newReservationBtn.setBackground(new Color(219, 232, 228));
		newReservationBtn.setBounds(620, 252, 142, 29);
		contentPane.add(newReservationBtn);
		
		
		
	}
}
