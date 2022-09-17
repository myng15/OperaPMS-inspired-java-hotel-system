package hotel_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

public class AvailableRoomsForm extends JFrame {
	public static AvailableRoomsForm instance;
	
	private JPanel contentPane;
	private JTextField roomNumTextField;
	private JTextField roomTypeTextField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AvailableRoomsForm frame = new AvailableRoomsForm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	ReservationManager reservationManager = new ReservationManager();
	/**
	 * Create the frame.
	 */
//	public AvailableRoomsForm() {
//		
//	}
	
	@SuppressWarnings("serial")
	private AvailableRoomsForm(Date arrival, Date departure) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 248, 245));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel headerPane = new JPanel();
		headerPane.setLayout(null);
		headerPane.setBackground(new Color(47, 72, 88));
		headerPane.setBounds(0, 0, 800, 70);
		contentPane.add(headerPane);
		
		JLabel headerLabel = new JLabel("AVAILABLE ROOMS SEARCH");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setBounds(259, 22, 296, 25);
		headerPane.add(headerLabel);
		
		JLabel arrivalLabel = new JLabel("Arrival:");
		arrivalLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		arrivalLabel.setBounds(30, 100, 61, 16);
		contentPane.add(arrivalLabel);
		
		JLabel departureLabel = new JLabel("Departure:");
		departureLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		departureLabel.setBounds(30, 135, 106, 16);
		contentPane.add(departureLabel);
		
		JDateChooser arrivalDateChooser = new JDateChooser();
		arrivalDateChooser.setBounds(130, 92, 165, 30);
		arrivalDateChooser.setBackground(new Color(250,248,245));
		arrivalDateChooser.setDate(arrival);
		contentPane.add(arrivalDateChooser);
		
		JDateChooser departureDateChooser = new JDateChooser();
		departureDateChooser.setBounds(130, 127, 165, 30);
		departureDateChooser.setBackground(new Color(250,248,245));
		departureDateChooser.setDate(departure);
		contentPane.add(departureDateChooser);
		
		JLabel roomNumLabel = new JLabel("Room No.:");
		roomNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumLabel.setBounds(328, 100, 88, 16);
		contentPane.add(roomNumLabel);
		
		JLabel roomTypeLabel = new JLabel("Room Type:");
		roomTypeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomTypeLabel.setBounds(328, 135, 106, 16);
		contentPane.add(roomTypeLabel);
		
		roomNumTextField = new JTextField();
		roomNumTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumTextField.setColumns(10);
		roomNumTextField.setBounds(422, 95, 165, 25);
		contentPane.add(roomNumTextField);
		
		roomTypeTextField = new JTextField();
		roomTypeTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomTypeTextField.setColumns(10);
		roomTypeTextField.setBounds(422, 130, 165, 25);
		contentPane.add(roomTypeTextField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 196, 730, 386);
		contentPane.add(scrollPane);
		
		JTable availableRoomsTable = new JTable();
		scrollPane.setViewportView(availableRoomsTable);
		availableRoomsTable.setModel(new DefaultTableModel(null, new Object[] {"Room No.", "Room Type", "FO Status", "HK Status"})
			{
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});

		try {
			reservationManager.showAvailableRooms(availableRoomsTable, arrival, departure);
		} catch (InvalidReservationDatesException e1) {
			JOptionPane.showMessageDialog(contentPane, "Arrival Date must be from today onwards and earlier than Departure Date", "Invalid Reservation Dates", JOptionPane.WARNING_MESSAGE);
		}
		
		availableRoomsTable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) availableRoomsTable.getModel();
				
				int rowIndex = availableRoomsTable.getSelectedRow();
				
				roomNumTextField.setText(tableModel.getValueAt(rowIndex, 0).toString());
				roomTypeTextField.setText(tableModel.getValueAt(rowIndex, 1).toString());
				
			}
		});
		
		JButton selectBtn = new JButton("Select");
		selectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					String roomNum = roomNumTextField.getText();
					String roomType = roomTypeTextField.getText(); 
					ManageReservationsForm reservationForm = ManageReservationsForm.getInstance();
					reservationForm.setVisible(true);
					reservationForm.setLocationRelativeTo(null);
					reservationForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					reservationForm.getAvailableRoom(roomNum, roomType, arrival, departure);
//					cleanup();
					dispose();
			}
		});
		selectBtn.setOpaque(true);
		selectBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		selectBtn.setBorderPainted(false);
		selectBtn.setBackground(new Color(219, 232, 228));
		selectBtn.setBounds(620, 130, 140, 29);
		contentPane.add(selectBtn);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setOpaque(true);
		searchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		searchBtn.setBorderPainted(false);
		searchBtn.setBackground(new Color(219, 232, 228));
		searchBtn.setBounds(620, 95, 140, 29);
		contentPane.add(searchBtn);
	}

	public static AvailableRoomsForm getInstance(Date arrival, Date departure) throws InvalidReservationDatesException {
		Date curDate = new Date();
		LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(curDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime comparedCurrentLocalDateTime = currentLocalDateTime.minus(23, ChronoUnit.HOURS);	
		LocalDateTime arrivalLocalDateTime = LocalDateTime.ofInstant(arrival.toInstant(), ZoneId.systemDefault());
		
		if(departure.compareTo(arrival) < 0 || arrivalLocalDateTime .isBefore(comparedCurrentLocalDateTime)) {
			throw new InvalidReservationDatesException("");
		} 
		else {
			if (instance == null) {
				instance = new AvailableRoomsForm(arrival, departure); 
			}
		}
		return instance;
	}
	
	public void cleanup() {
		instance = null;
	}
}
