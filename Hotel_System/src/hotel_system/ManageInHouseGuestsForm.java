package hotel_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageInHouseGuestsForm extends JFrame {

	private JPanel contentPane;
	private JTextField rsvIDTextField;
	private JTextField roomNumTextField;
	private JTextField guestIDTextField;
	private JTextField guestNameTextField;
	private JTextField arrivalTextField;
	private JTextField departureTextField;
	private JTextField balanceTextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageInHouseGuestsForm frame = new ManageInHouseGuestsForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	ReservationManager reservationManager = new ReservationManager();
	/**
	 * Create the frame.
	 */
	public ManageInHouseGuestsForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 248, 245));
		setContentPane(contentPane);
		
		JPanel headerPane = new JPanel();
		headerPane.setLayout(null);
		headerPane.setBackground(new Color(47, 72, 88));
		headerPane.setBounds(0, 0, 800, 70);
		contentPane.add(headerPane);
		
		JLabel headerLabel = new JLabel("MANAGE IN HOUSE GUESTS");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setBounds(265, 22, 289, 25);
		headerPane.add(headerLabel);
		
		rsvIDTextField = new JTextField();
		rsvIDTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rsvIDTextField.setColumns(10);
		rsvIDTextField.setBounds(130, 130, 165, 25);
		contentPane.add(rsvIDTextField);
		
		guestIDTextField = new JTextField();
		guestIDTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestIDTextField.setColumns(10);
		guestIDTextField.setBounds(422, 130, 165, 25);
		contentPane.add(guestIDTextField);
		
		roomNumTextField = new JTextField();
		roomNumTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumTextField.setColumns(10);
		roomNumTextField.setBounds(130, 165, 165, 25);
		contentPane.add(roomNumTextField);
		
		JLabel guestNameLabel = new JLabel("Name:");
		guestNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestNameLabel.setBounds(328, 170, 100, 16);
		contentPane.add(guestNameLabel);
		
		guestNameTextField = new JTextField();
		guestNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestNameTextField.setColumns(10);
		guestNameTextField.setBounds(422, 165, 165, 25);
		contentPane.add(guestNameTextField);
		
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(130, 92, 165, 30);
		dateChooser.setBackground(new Color(250,248,245));
		dateChooser.setDate(new Date());
		contentPane.add(dateChooser);
		
		JLabel rsvIDLabel = new JLabel("Rsv. ID:");
		rsvIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rsvIDLabel.setBounds(30, 135, 106, 16);
		contentPane.add(rsvIDLabel);
		
		JLabel guestIDLabel = new JLabel("Guest ID:");
		guestIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestIDLabel.setBounds(328, 135, 90, 16);
		contentPane.add(guestIDLabel);
		
		JLabel roomNumLabel = new JLabel("Room No.:");
		roomNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumLabel.setBounds(30, 170, 90, 16);
		contentPane.add(roomNumLabel);
		
		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		dateLabel.setBounds(30, 100, 61, 16);
		contentPane.add(dateLabel);
		
		
		//create and populate JTable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 300, 730, 300);
		contentPane.add(scrollPane);
		
		JTable inhouseGuestsTable = new JTable();
		scrollPane.setViewportView(inhouseGuestsTable);
		inhouseGuestsTable.setModel(new DefaultTableModel(null, new Object[] {"Rsv. ID", "Guest ID", "Name", "Room No.", "Arrival", "Departure", "Balance"})
			{
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
		
		reservationManager.fillInHouseGuestsTable(inhouseGuestsTable);
		
		inhouseGuestsTable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) inhouseGuestsTable.getModel();
				
				int rowIndex = inhouseGuestsTable.getSelectedRow();
				
				rsvIDTextField.setText(tableModel.getValueAt(rowIndex, 0).toString());
				guestIDTextField.setText(tableModel.getValueAt(rowIndex, 1).toString());
				
//				int guestID = Integer.parseInt(guestIDTextField.getText());
//				reservationManager.getBookingGuestName(guestID);
				
				guestNameTextField.setText(tableModel.getValueAt(rowIndex, 2).toString());
				roomNumTextField.setText(tableModel.getValueAt(rowIndex, 3).toString());
				balanceTextField.setText(tableModel.getValueAt(rowIndex, 6).toString());
				arrivalTextField.setText(tableModel.getValueAt(rowIndex, 4).toString());
				departureTextField.setText(tableModel.getValueAt(rowIndex, 5).toString());
//				try {
//					Date arrivalDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(tableModel.getValueAt(rowIndex, 3).toString());
//					arrivalTextField.setDate(arrivalDate);
//					Date departureDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(tableModel.getValueAt(rowIndex, 4).toString());
//					departureDateChooser.setDate(departureDate);
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
			}
		});
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setOpaque(true);
		searchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		searchBtn.setBorderPainted(false);
		searchBtn.setBackground(new Color(219, 232, 228));
		searchBtn.setBounds(620, 95, 142, 29);
		contentPane.add(searchBtn);
		
		JButton clearSearchBtn = new JButton("Clear Search");
		clearSearchBtn.setOpaque(true);
		clearSearchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		clearSearchBtn.setBorderPainted(false);
		clearSearchBtn.setBackground(new Color(219, 232, 228));
		clearSearchBtn.setBounds(620, 130, 142, 29);
		contentPane.add(clearSearchBtn);
		
		JButton billingBtn = new JButton("Billing");
		billingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		billingBtn.setOpaque(true);
		billingBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		billingBtn.setBorderPainted(false);
		billingBtn.setBackground(new Color(219, 232, 228));
		billingBtn.setBounds(620, 165, 142, 29);
		contentPane.add(billingBtn);
		
		JButton checkOutBtn = new JButton("Check Out");
		checkOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rsvID = Integer.parseInt(rsvIDTextField.getText());
				double balance = Double.parseDouble(balanceTextField.getText());
				if (balance > 0) {
//					BillingForm billingForm = new BillingForm();
				}
				
				reservationManager.checkOut(rsvID);
			}
		});
		checkOutBtn.setOpaque(true);
		checkOutBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		checkOutBtn.setBorderPainted(false);
		checkOutBtn.setBackground(new Color(219, 232, 228));
		checkOutBtn.setBounds(620, 200, 142, 29);
		contentPane.add(checkOutBtn);
		
		JLabel arrivalLabel = new JLabel("Arrival:");
		arrivalLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		arrivalLabel.setBounds(30, 205, 90, 16);
		contentPane.add(arrivalLabel);
		
		arrivalTextField = new JTextField();
		arrivalTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		arrivalTextField.setColumns(10);
		arrivalTextField.setBounds(130, 200, 165, 25);
		contentPane.add(arrivalTextField);
		
		departureTextField = new JTextField();
		departureTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		departureTextField.setColumns(10);
		departureTextField.setBounds(130, 235, 165, 25);
		contentPane.add(departureTextField);
		
		JLabel departureLabel = new JLabel("Departure:");
		departureLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		departureLabel.setBounds(30, 240, 90, 16);
		contentPane.add(departureLabel);
		
		JLabel balanceLabel = new JLabel("Balance:");
		balanceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		balanceLabel.setBounds(328, 205, 100, 16);
		contentPane.add(balanceLabel);
		
		balanceTextField = new JTextField();
		balanceTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		balanceTextField.setColumns(10);
		balanceTextField.setBounds(422, 200, 165, 25);
		contentPane.add(balanceTextField);
	}

}
