package hotel_system;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;


public class ManageReservationsForm extends JFrame {
	public static ManageReservationsForm instance;
	
	private JFrame frame;
	private JPanel contentPane;
	private JTextField idTextField;
	private JTextField roomNumTextField;
	private JTextField guestIDTextField;
	private JTextField creditCardTextField;
	private JTextField expDateTextField;
	private JTextField rateTextField;
	private JTextField guestNameTextField;
	private JLabel roomTypeLabel;
	JDateChooser arrivalDateChooser;
	JDateChooser departureDateChooser;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageReservationsForm frame = new ManageReservationsForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	RoomManager roomManager = new RoomManager();
	ReservationManager reservationManager = new ReservationManager();
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	private ManageReservationsForm() {
		
		setResizable(false);
		setTitle("Manage Reservations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 248, 245));
		setContentPane(contentPane);
		
		JPanel headerPane = new JPanel();
		headerPane.setLayout(null);
		headerPane.setBackground(new Color(47, 72, 88));
		headerPane.setBounds(0, 0, 800, 70);
		contentPane.add(headerPane);
		
		JLabel headerLabel = new JLabel("MANAGE RESERVATIONS");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setBounds(290, 22, 252, 25);
		headerPane.add(headerLabel);
		
		idTextField = new JTextField();
		idTextField.setEditable(false);
		idTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		idTextField.setColumns(10);
		idTextField.setBounds(130, 95, 165, 25);
		contentPane.add(idTextField);
		
		roomNumTextField = new JTextField();
		roomNumTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumTextField.setColumns(10);
		roomNumTextField.setBounds(130, 130, 92, 25);
		contentPane.add(roomNumTextField);
		
		
		guestIDTextField = new JTextField();
		guestIDTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestIDTextField.setColumns(10);
		guestIDTextField.setBounds(422, 95, 122, 25);
		contentPane.add(guestIDTextField);
		
		arrivalDateChooser = new JDateChooser();
		arrivalDateChooser.setBounds(130, 162, 165, 30);
		arrivalDateChooser.setBackground(new Color(250,248,245));
		contentPane.add(arrivalDateChooser);
		
		departureDateChooser = new JDateChooser();
		departureDateChooser.setBounds(130, 197, 165, 30);
		departureDateChooser.setBackground(new Color(250,248,245));
		contentPane.add(departureDateChooser);
		
		JLabel idLabel = new JLabel("Rsv. ID:");
		idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		idLabel.setBounds(30, 100, 106, 16);
		contentPane.add(idLabel);
		
		JLabel guestIDLabel = new JLabel("Guest ID:");
		guestIDLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestIDLabel.setBounds(328, 100, 90, 16);
		contentPane.add(guestIDLabel);
		
		JLabel roomNumLabel = new JLabel("Room No.:");
		roomNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumLabel.setBounds(30, 135, 90, 16);
		contentPane.add(roomNumLabel);
		
		roomTypeLabel = new JLabel("");
		roomTypeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomTypeLabel.setBounds(224, 132, 71, 25);
		contentPane.add(roomTypeLabel);
		
		JLabel arrivalLabel = new JLabel("Arrival:");
		arrivalLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		arrivalLabel.setBounds(30, 170, 61, 16);
		contentPane.add(arrivalLabel);
		
		JLabel departureLabel = new JLabel("Departure:");
		departureLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		departureLabel.setBounds(30, 205, 106, 16);
		contentPane.add(departureLabel);
		
		JLabel rsvStatusLabel = new JLabel("Rsv. Status:");
		rsvStatusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rsvStatusLabel.setBounds(30, 240, 90, 16);
		contentPane.add(rsvStatusLabel);
		
		JLabel channelLabel = new JLabel("Channel:");
		channelLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		channelLabel.setBounds(328, 170, 90, 16);
		contentPane.add(channelLabel);
		
		JComboBox channelBox = new JComboBox(new Object[]{"--", "Hotel Website", "Front Office", "OTA", "GDS", "Multiple Channels", "Others"});
		channelBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		channelBox.setBounds(422, 166, 165, 25);
		contentPane.add(channelBox);
		
		JComboBox rsvStatusBox = new JComboBox(new Object[]{"CONFIRMED", "CHECKED IN", "CHECKED OUT", "NO SHOW", "CANCELLED"});
		rsvStatusBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rsvStatusBox.setBounds(130, 235, 165, 25);
		contentPane.add(rsvStatusBox);
		
		JLabel creditCardLabel = new JLabel("Credit Card:");
		creditCardLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		creditCardLabel.setBounds(328, 205, 100, 16);
		contentPane.add(creditCardLabel);
		
		creditCardTextField = new JTextField();
		creditCardTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		creditCardTextField.setColumns(10);
		creditCardTextField.setBounds(422, 200, 165, 25);
		contentPane.add(creditCardTextField);
		
		JLabel expDateLabel = new JLabel("Exp. Date:");
		expDateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		expDateLabel.setBounds(328, 240, 100, 16);
		contentPane.add(expDateLabel);
		
		expDateTextField = new JTextField();
		expDateTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		expDateTextField.setColumns(10);
		expDateTextField.setBounds(422, 235, 165, 25);
		contentPane.add(expDateTextField);
		
		JLabel rateLabel = new JLabel("Daily Rate:");
		rateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rateLabel.setBounds(30, 275, 100, 16);
		contentPane.add(rateLabel);
		
		rateTextField = new JTextField();
		rateTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		rateTextField.setColumns(10);
		rateTextField.setBounds(130, 268, 165, 25);
		contentPane.add(rateTextField);
		
		JLabel guestNameLabel = new JLabel("Name:");
		guestNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestNameLabel.setBounds(328, 135, 100, 16);
		contentPane.add(guestNameLabel);
		
		guestNameTextField = new JTextField();
		guestNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		guestNameTextField.setColumns(10);
		guestNameTextField.setBounds(422, 130, 165, 25);
		contentPane.add(guestNameTextField);
		
		//create and populate JTable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 300, 730, 300);
		contentPane.add(scrollPane);
		
		JTable reservationsTable = new JTable();
		scrollPane.setViewportView(reservationsTable);
		reservationsTable.setModel(new DefaultTableModel(null, new Object[] {"ID", "Guest ID", "Room No.", "Arrival", "Departure", "Rsv. Status", "Rate", "Channel", "Credit Card", "Expiry Date"})
			{
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
		
		reservationManager.fillReservationsTable(reservationsTable);
		
		reservationsTable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) reservationsTable.getModel();
				
				int rowIndex = reservationsTable.getSelectedRow();
				
				idTextField.setText(tableModel.getValueAt(rowIndex, 0).toString());
				guestIDTextField.setText(tableModel.getValueAt(rowIndex, 1).toString());
				
				int guestID = Integer.parseInt(guestIDTextField.getText());
				String name = reservationManager.getBookingGuestName(guestID);
				guestNameTextField.setText(name);
				
				roomNumTextField.setText(tableModel.getValueAt(rowIndex, 2).toString());
				
				rsvStatusBox.setSelectedItem(tableModel.getValueAt(rowIndex, 5).toString());
				rateTextField.setText(tableModel.getValueAt(rowIndex, 6).toString());
				channelBox.setSelectedItem(tableModel.getValueAt(rowIndex, 7).toString());
				creditCardTextField.setText(tableModel.getValueAt(rowIndex, 8).toString());
				expDateTextField.setText(tableModel.getValueAt(rowIndex, 9).toString());
				
				int roomNum = Integer.parseInt(roomNumTextField.getText());
				String roomType = roomManager.getRoomType(roomNum);
				roomTypeLabel.setText(roomType);
				
				try {
					Date arrivalDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(tableModel.getValueAt(rowIndex, 3).toString());
					arrivalDateChooser.setDate(arrivalDate);
					Date departureDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(tableModel.getValueAt(rowIndex, 4).toString());
					departureDateChooser.setDate(departureDate);
					
//					int id = Integer.parseInt(idTextField.getText());
//					System.out.println(reservationManager.isInHouse(id));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton addRsvBtn = new JButton("Add Rsv.");
		addRsvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					int guestID = Integer.parseInt(guestIDTextField.getText());
					int roomNum = Integer.parseInt(roomNumTextField.getText());
					String rsvStatus = rsvStatusBox.getSelectedItem().toString();
					double rate = Double.parseDouble(rateTextField.getText());
					String channel = channelBox.getSelectedItem().toString();
					String creditCard = creditCardTextField.getText();
					String expDate = expDateTextField.getText();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String arrival = dateFormat.format(arrivalDateChooser.getDate());
					String departure = dateFormat.format(departureDateChooser.getDate());
					
					Reservation reservation = new Reservation.Builder(guestID, roomNum, arrival, departure)
															 .rsvStatus(rsvStatus)
															 .rate(rate)
															 .channel(channel)
															 .creditCard(creditCard)
															 .expDate(expDate)
															 .build();
					try {
						if(reservationManager.addReservation(reservation/*guestID, roomNum, arrival, departure*/)) {
							reservationsTable.setModel(new DefaultTableModel(null, new Object[] {"ID", "Guest ID", "Room No.", "Arrival", "Departure", "Rsv. Status", "Rate", "Channel", "Credit Card", "Expiry Date"}));
							reservationManager.fillReservationsTable(reservationsTable);
							JOptionPane.showMessageDialog(contentPane, "Reservation Added Successfully", "Add Reservation", JOptionPane.INFORMATION_MESSAGE);
						}
//					}
						else {
							JOptionPane.showMessageDialog(contentPane, "Failed to Add Reservation", "Add Reservation Error", JOptionPane.ERROR_MESSAGE);
						}
					
					} catch (InvalidReservationDatesException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(contentPane, "Arrival Date must be from today onwards and earlier than Departure Date", "Invalid Reservation Dates", JOptionPane.WARNING_MESSAGE);
					} catch (RoomAvailabilityException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(contentPane, "Room not available during the selected date range", "Room Not Available", JOptionPane.WARNING_MESSAGE);
					}
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, "All Fields Required", "Add Reservation Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e2) {
					JOptionPane.showMessageDialog(contentPane, "Enter Arrival and Departure Date", "Add Reservation Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addRsvBtn.setOpaque(true);
		addRsvBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		addRsvBtn.setBorderPainted(false);
		addRsvBtn.setBackground(new Color(219, 232, 228));
		addRsvBtn.setBounds(620, 95, 142, 29);
		contentPane.add(addRsvBtn);
		
		JButton editRsvBtn = new JButton("Edit");
		editRsvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			try {
				int id = Integer.parseInt(idTextField.getText());
				int guestID = Integer.parseInt(guestIDTextField.getText());
				int roomNum = Integer.parseInt(roomNumTextField.getText());
				String rsvStatus = rsvStatusBox.getSelectedItem().toString();
				double rate = Double.parseDouble(rateTextField.getText());
				String channel = channelBox.getSelectedItem().toString();
				String creditCard = creditCardTextField.getText();
				String expDate = expDateTextField.getText();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String arrival = dateFormat.format(arrivalDateChooser.getDate());
				String departure = dateFormat.format(departureDateChooser.getDate());
				
				Reservation reservation = new Reservation.Builder(guestID, roomNum, arrival, departure)
						 .id(id)
						 .rsvStatus(rsvStatus)
						 .rate(rate)
						 .channel(channel)
						 .creditCard(creditCard)
						 .expDate(expDate)
						 .build();
				try {
					if(reservationManager.editReservation(reservation)) {
//						if (rsvStatus.equals("CHECKED IN") && !reservationManager.isInHouse(id)) {
//							reservationManager.checkIn(reservation);
//	
//							roomManager.changeRoomFOStatus(roomNum, "OCCUPIED");
//							roomManager.changeRoomHKStatus(roomNum, "DIRTY");
//						}
//						else if (rsvStatus.equals("CHECKED OUT") && !reservationManager.isInHouse(id)) {
//							JOptionPane.showMessageDialog(contentPane, "Guest is not in house", "Guest Not In House Error", JOptionPane.ERROR_MESSAGE);
//						} 
//						else if (rsvStatus.equals("CHECKED OUT") && reservationManager.isInHouse(id)) {
//							reservationManager.checkOut(id);
//							roomManager.changeRoomFOStatus(roomNum, "VACANT");
//						}
//						else if ((!rsvStatus.equals("CHECKED IN") || !rsvStatus.equals("CHECKED OUT")) && reservationManager.isInHouse(id)) {
//							JOptionPane.showMessageDialog(contentPane, "Guest has already checked in", "Guest Already Checked In Warning", JOptionPane.WARNING_MESSAGE);
//						}
						
						reservationsTable.setModel(new DefaultTableModel(null, new Object[] {"ID", "Guest ID", "Room No.", "Arrival", "Departure", "Rsv. Status", "Rate", "Channel", "Credit Card", "Expiry Date"}));
						reservationManager.fillReservationsTable(reservationsTable);
						JOptionPane.showMessageDialog(contentPane, "Reservation Updated Successfully", "Edit Reservation", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Update Reservation", "Edit Reservation Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (InvalidReservationDatesException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, "Arrival Date must be from today onwards and earlier than Departure Date", "Invalid Reservation Dates", JOptionPane.WARNING_MESSAGE);
				} catch (RoomAvailabilityException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, "Room not available during the selected date range", "Room Not Available", JOptionPane.WARNING_MESSAGE);
				} catch (GuestNotInHouseException e3) {
					JOptionPane.showMessageDialog(contentPane, "Guest is not in house", "Guest Not In House Error", JOptionPane.ERROR_MESSAGE);
				} catch (GuestAlreadyCheckedInException e4) {
					JOptionPane.showMessageDialog(contentPane, "Guest has already checked in", "Guest Already Checked In Warning", JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(contentPane, "Enter Reservation ID, Guest ID and Room Number", "Edit Reservation Error", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(contentPane, "Enter Arrival and Departure Date", "Edit Reservation Error", JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		editRsvBtn.setOpaque(true);
		editRsvBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		editRsvBtn.setBorderPainted(false);
		editRsvBtn.setBackground(new Color(219, 232, 228));
		editRsvBtn.setBounds(620, 130, 142, 29);
		contentPane.add(editRsvBtn);
		
		JButton removeRsvBtn = new JButton("Remove");
		removeRsvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(idTextField.getText());
					if(reservationManager.removeReservation(id)) {
						reservationsTable.setModel(new DefaultTableModel(null, new Object[] {"ID", "Guest ID", "Room No.", "Arrival", "Departure", "Rsv. Status", "Rate", "Channel", "Credit Card", "Expiry Date"}));
						reservationManager.fillReservationsTable(reservationsTable);
						idTextField.setText("");
						JOptionPane.showMessageDialog(contentPane, "Reservation Deleted Successfully", "Remove Reservation", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Delete Reservation",  "Remove Reservation Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, "Enter Reservation ID, Guest ID and Room Number", "Remove Room Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		removeRsvBtn.setOpaque(true);
		removeRsvBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		removeRsvBtn.setBorderPainted(false);
		removeRsvBtn.setBackground(new Color(219, 232, 228));
		removeRsvBtn.setBounds(620, 165, 142, 29);
		contentPane.add(removeRsvBtn);
		
		JButton clearSearchBtn = new JButton("Clear Search");
		clearSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idTextField.setText("");
				guestIDTextField.setText("");
				guestNameTextField.setText("");
				roomNumTextField.setText("");
				roomTypeLabel.setText("");
				arrivalDateChooser.setDate(new Date());
				departureDateChooser.setDate(new Date());
				rsvStatusBox.setSelectedItem("CONFIRMED");
				rateTextField.setText("");
				channelBox.setSelectedItem("--");
				creditCardTextField.setText("");
				expDateTextField.setText("");
			}
		});
		clearSearchBtn.setOpaque(true);
		clearSearchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		clearSearchBtn.setBorderPainted(false);
		clearSearchBtn.setBackground(new Color(219, 232, 228));
		clearSearchBtn.setBounds(620, 200, 142, 29);
		contentPane.add(clearSearchBtn);
		
		JButton addAccompanyingGuestBtn = new JButton("...");
		addAccompanyingGuestBtn.setToolTipText("Add Accompanying Guests");
		addAccompanyingGuestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				int bookingGuestID = Integer.parseInt(guestIDTextField.getText());
				AccompanyingGuestForm accGuestForm = /*new AccompanyingGuestForm(bookingGuestID);*/ AccompanyingGuestForm.getInstance(bookingGuestID);
				accGuestForm.setVisible(true);
				accGuestForm.setLocationRelativeTo(null);
				accGuestForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				accGuestForm.getAccompaniedGuest(bookingGuestID);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(contentPane, "Enter Guest ID Number", "ID Missing Error", JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		addAccompanyingGuestBtn.setOpaque(true);
		addAccompanyingGuestBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		addAccompanyingGuestBtn.setBorderPainted(false);
		addAccompanyingGuestBtn.setBackground(new Color(219, 232, 228));
		addAccompanyingGuestBtn.setBounds(547, 95, 35, 25);
		contentPane.add(addAccompanyingGuestBtn);
		
		JButton checkAvailabilityBtn = new JButton("Availability");
		checkAvailabilityBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Date arrival = arrivalDateChooser.getDate();
					Date departure = departureDateChooser.getDate();
					
					AvailableRoomsForm availableRoomsForm;
					try {
						availableRoomsForm = AvailableRoomsForm.getInstance(arrival, departure);
						availableRoomsForm.setVisible(true);
						availableRoomsForm.setLocationRelativeTo(null);
						availableRoomsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//						cleanup();
					dispose();
					} catch (InvalidReservationDatesException e1) {
						JOptionPane.showMessageDialog(contentPane, "Arrival Date must be from today onwards and earlier than Departure Date", "Invalid Reservation Dates", JOptionPane.WARNING_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(contentPane, "Enter Guest ID Number", "ID Missing Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e2) {
					JOptionPane.showMessageDialog(contentPane, "Enter Arrival and Departure Date", "Check Availability Error", JOptionPane.ERROR_MESSAGE);
				} 
				
			}
		});
		
		checkAvailabilityBtn.setOpaque(true);
		checkAvailabilityBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		checkAvailabilityBtn.setBorderPainted(false);
		checkAvailabilityBtn.setBackground(new Color(219, 232, 228));
		checkAvailabilityBtn.setBounds(620, 259, 142, 29);
		contentPane.add(checkAvailabilityBtn);
		
		roomNumTextField.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				try {
					int roomNum = Integer.parseInt(roomNumTextField.getText());
					String roomType = roomManager.getRoomType(roomNum);
					roomTypeLabel.setText(roomType);
					
					String roomRate = Double.toString(roomManager.getRoomRate(roomType));
					rateTextField.setText(roomRate);
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					
				}
			}
		});
		
		guestIDTextField.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				try {
					int guestID = Integer.parseInt(guestIDTextField.getText());
					String name = reservationManager.getBookingGuestName(guestID);
					guestNameTextField.setText(name);
				} catch (NumberFormatException e1) {
					
				}
			}
		});
//		rsvStatusBox.addActionListener(new ActionListener() {
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//	            
//	        }
//	    });
//		rsvStatusBox.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				if(e.getStateChange() == ItemEvent.SELECTED) {
//					
//					Object item = e.getItem();
//					try {
//					
//						int rsvID = Integer.parseInt(idTextField.getText());
//						int guestID = Integer.parseInt(guestIDTextField.getText());
//						int roomNum = Integer.parseInt(roomNumTextField.getText());
//						double rate = Double.parseDouble(rateTextField.getText());
//						
//						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//						String arrival = dateFormat.format(arrivalDateChooser.getDate());
//						String departure = dateFormat.format(departureDateChooser.getDate());
//						
//						Reservation reservation = new Reservation.Builder(guestID, roomNum, arrival, departure)
//																 .id(rsvID)
//																 .rate(rate)
//																 .build();
//						
//						if (item == "CHECKED IN") {
//							reservationManager.checkIn(reservation);
//							
//							roomManager.changeRoomFOStatus(roomNum, "OCCUPIED");
//							roomManager.changeRoomHKStatus(roomNum, "DIRTY");
//
//						} 
//						else if (item == "CHECKED OUT") {
//							if (!reservationManager.isInHouse(rsvID)) {
//								JOptionPane.showMessageDialog(contentPane, "Guest is not in house", "Guest Not In House Error", JOptionPane.ERROR_MESSAGE);
//							}
//							else {
//								reservationManager.checkOut(rsvID);
//								roomManager.changeRoomFOStatus(roomNum, "VACANT");
//							}
//						} 
//						else {
//							if (reservationManager.isInHouse(rsvID)) {
//								JOptionPane.showMessageDialog(contentPane, "Guest has already checked in", "Guest Already Checked In Warning", JOptionPane.WARNING_MESSAGE);
//							}
//						}
//					} catch (NumberFormatException e1) {
//						JOptionPane.showMessageDialog(contentPane, "Enter Room Number and Room Rate", "Room Number Missing Error", JOptionPane.ERROR_MESSAGE);
////						e1.printStackTrace();
//					} 
//				}
//				
//			}
//		});
	}
	
	
	
	public void getAvailableRoom(String roomNum, String roomType, Date arrival, Date departure) {
		
		roomNumTextField.setText(roomNum);
		roomTypeLabel.setText(roomType);
		
		arrivalDateChooser.setDate(arrival);
		departureDateChooser.setDate(departure);
	}
	
	public void getBookingGuest(int bookingGuestID, String bookingGuestName) {
		guestIDTextField.setText(((Integer)bookingGuestID).toString());
		guestNameTextField.setText(bookingGuestName);

	}
	
	public static ManageReservationsForm getInstance() {
		if(instance == null) {
			instance = new ManageReservationsForm();
		}
		return instance;
	}
	
	public void cleanup() {
		instance = null;
	}
//	public void getBookingGuest(int bookingGuestID) {
//		guestIDTextField.setText(((Integer)bookingGuestID).toString());
//		
//		MY_CONNECTION my_connection = new MY_CONNECTION();
//		PreparedStatement ps;
//		ResultSet rs;
//		String selectQuery = "SELECT CONCAT(`first_name`, ' ', `last_name`) FROM `guests` WHERE `id`=?"; // MySQL doesn't support + or || for concatenation. "SELECT `first_name` + ' ' + `last_name` FROM `guests` WHERE `id`=?" wouldn't work.
//		
//		try {
//			ps = my_connection.createConnection().prepareStatement(selectQuery);
//			ps.setInt(1, bookingGuestID);
//			rs = ps.executeQuery();
//			while(rs.next()) {
//				guestNameTextField.setText(rs.getString(1));
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}	
	

