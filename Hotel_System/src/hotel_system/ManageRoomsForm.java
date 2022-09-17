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
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class ManageRoomsForm extends JFrame {

	private JPanel contentPane;
	private JTextField roomNumTextField;
	private JComboBox roomTypeBox;
	private JComboBox FOStatusBox;
	private JComboBox HKStatusBox;
	private JTable roomsTable;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageRoomsForm frame = new ManageRoomsForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	RoomManager roomManager = new RoomManager();
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ManageRoomsForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		setResizable(false); 
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 248, 245));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPane = new JPanel();
		headerPane.setLayout(null);
		headerPane.setBackground(new Color(47, 72, 88));
		headerPane.setBounds(0, 0, 800, 70);
		contentPane.add(headerPane);
		
		JLabel headerLabel = new JLabel("MANAGE ROOMS");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerLabel.setBounds(315, 22, 175, 25);
		headerPane.add(headerLabel);
		
		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		dateLabel.setBounds(30, 100, 88, 16);
		contentPane.add(dateLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(130, 92, 165, 30);
		dateChooser.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		dateChooser.setDate(new Date());
		contentPane.add(dateChooser);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateQueried = dateFormat.format(dateChooser.getDate());
		
		JLabel roomNumLabel = new JLabel("Room No.:");
		roomNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumLabel.setBounds(30, 135, 88, 16);
		contentPane.add(roomNumLabel);
		
		roomNumTextField = new JTextField();
		roomNumTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomNumTextField.setColumns(10);
		roomNumTextField.setBounds(130, 130, 165, 25);
		contentPane.add(roomNumTextField);
		
		roomTypeBox = new JComboBox<String>();
		roomTypeBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomTypeBox.setBounds(130, 165, 123, 25);
		contentPane.add(roomTypeBox);
		
		roomManager.fillRoomTypeBox(roomTypeBox);
		
		String[] FOItems = {"--", "VACANT", "OCCUPIED", "DUE OUT"};
		FOStatusBox = new JComboBox(FOItems);
		FOStatusBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		FOStatusBox.setBounds(130, 200, 165, 25);
//		FOStatusBox.addItem("Vacant");
//		FOStatusBox.addItem("Occupied");
//		FOStatusBox.addItem("Due Out");
		contentPane.add(FOStatusBox);
		
		JLabel roomTypeLabel = new JLabel("Room Type:");
		roomTypeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		roomTypeLabel.setBounds(30, 170, 105, 16);
		contentPane.add(roomTypeLabel);
		
		String[] HKItems = {"--","CLEAN", "DIRTY", "OUT OF ORDER", "PICKUP"};
		HKStatusBox = new JComboBox(HKItems);
		HKStatusBox.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		HKStatusBox.setBounds(130, 235, 165, 25);
		contentPane.add(HKStatusBox);
		
		JLabel FOStatusLabel = new JLabel("FO Status:");
		FOStatusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		FOStatusLabel.setBounds(30, 205, 105, 16);
		contentPane.add(FOStatusLabel);
		
		JLabel HKStatusLabel = new JLabel("HK Status:");
		HKStatusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		HKStatusLabel.setBounds(30, 240, 116, 18);
		contentPane.add(HKStatusLabel);
		
		JButton addRoomBtn = new JButton("Add Room");
		addRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int roomNum = Integer.parseInt(roomNumTextField.getText());
					String roomType = roomTypeBox.getSelectedItem().toString();
					String FOStatus = FOStatusBox.getSelectedItem().toString();
					String HKStatus = HKStatusBox.getSelectedItem().toString();
					
					if(roomManager.addRoom(roomNum, roomType, FOStatus, HKStatus)) {
						roomsTable.setModel(new DefaultTableModel(null, new Object[] {"Room No.", "Room Type", "FO Status", "HK Status"}));
						roomManager.fillRoomsTable(roomsTable);
						JOptionPane.showMessageDialog(contentPane, "Room Added Successfully", "Add Room", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Add Room", "Add Room Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Enter Room Number", "Room Nummber Missing Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addRoomBtn.setOpaque(true);
		addRoomBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		addRoomBtn.setBorderPainted(false);
		addRoomBtn.setBackground(new Color(219, 232, 228));
		addRoomBtn.setBounds(620, 100, 142, 29);
		contentPane.add(addRoomBtn);
		
		JButton editRoomBtn = new JButton("Edit");
		editRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int roomNum = Integer.parseInt(roomNumTextField.getText());
					String roomType = roomTypeBox.getSelectedItem().toString();
					String FOStatus = FOStatusBox.getSelectedItem().toString();
					String HKStatus = HKStatusBox.getSelectedItem().toString();
					
					if(roomManager.editRoom(roomNum, roomType, FOStatus, HKStatus)) {
						roomsTable.setModel(new DefaultTableModel(null, new Object[] {"Room No.", "Room Type", "FO Status", "HK Status"}));
						roomManager.fillRoomsTable(roomsTable);
						JOptionPane.showMessageDialog(contentPane, "Room Info Updated Succesfully", "Edit Room", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Update Room Info", "Edit Room Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(contentPane, "Enter Room Number", "Edit Room Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		editRoomBtn.setOpaque(true);
		editRoomBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		editRoomBtn.setBorderPainted(false);
		editRoomBtn.setBackground(new Color(219, 232, 228));
		editRoomBtn.setBounds(620, 135, 142, 29);
		contentPane.add(editRoomBtn);
		
		JButton removeRoomBtn = new JButton("Remove");
		removeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					int roomNum = Integer.parseInt(roomNumTextField.getText());
					if(roomManager.removeRoom(roomNum)) {
						roomsTable.setModel(new DefaultTableModel(null, new Object[] {"Room No.", "Room Type", "FO Status", "HK Status"}));
						roomManager.fillRoomsTable(roomsTable);
						JOptionPane.showMessageDialog(contentPane, "Room Deleted Successfully", "Remove Room", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Failed to Delete Room", "Remove Room Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(contentPane, "Enter Room Number", "Remove Room Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		removeRoomBtn.setOpaque(true);
		removeRoomBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		removeRoomBtn.setBorderPainted(false);
		removeRoomBtn.setBackground(new Color(219, 232, 228));
		removeRoomBtn.setBounds(620, 170, 142, 29);
		contentPane.add(removeRoomBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 300, 730, 300);
		contentPane.add(scrollPane);
		
		roomsTable = new JTable();
		scrollPane.setViewportView(roomsTable);
		roomsTable.setGridColor(Color.GRAY);
		roomsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[]{"Room No.", "Room Type", "FO Status", "HK Status"})
			{
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
		
		roomManager.fillRoomsTable(roomsTable);
		
		roomsTable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) roomsTable.getModel();
				
				int rowIndex = roomsTable.getSelectedRow();
				
				roomNumTextField.setText(tableModel.getValueAt(rowIndex, 0).toString());
				roomTypeBox.setSelectedItem(tableModel.getValueAt(rowIndex, 1)); //toString() can be omitted after applying getValueAt(), which returns strings because the items' values passed into the combo box were defined as String from the beginning - different from getSelectedItem, which returns an Object, not a string
				FOStatusBox.setSelectedItem(tableModel.getValueAt(rowIndex, 2));
				HKStatusBox.setSelectedItem(tableModel.getValueAt(rowIndex, 3));
			}
		});
		
//		JButton refreshBtn = new JButton("Refresh");
//		refreshBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				roomsTable.setModel(new DefaultTableModel(null, new Object[] {"Room No.", "Room Type", "FO Status", "HK Status"}));
//				room.fillRoomsTable(roomsTable);
//			}
//		});
//		refreshBtn.setOpaque(true);
//		refreshBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		refreshBtn.setBorderPainted(false);
//		refreshBtn.setBackground(new Color(219, 232, 228));
//		refreshBtn.setBounds(620, 240, 142, 29);
//		contentPane.add(refreshBtn);
		
		JButton clearSearchBtn = new JButton("Clear Search");
		clearSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateChooser.setDate(new Date());
				roomNumTextField.setText("");
				roomTypeBox.setSelectedItem("--");
				FOStatusBox.setSelectedItem("--");
				HKStatusBox.setSelectedItem("--");
			}
		});
		clearSearchBtn.setOpaque(true);
		clearSearchBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		clearSearchBtn.setBorderPainted(false);
		clearSearchBtn.setBackground(new Color(219, 232, 228));
		clearSearchBtn.setBounds(620, 205, 142, 29);
		contentPane.add(clearSearchBtn);
		
		JButton showTypesBtn = new JButton("...");
		showTypesBtn.setToolTipText("Show All Room Types");
		showTypesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomTypesForm roomTypesForm = new RoomTypesForm();
				roomTypesForm.setVisible(true);
				roomTypesForm.setLocationRelativeTo(null);
				roomTypesForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		showTypesBtn.setOpaque(true);
		showTypesBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		showTypesBtn.setBorderPainted(false);
		showTypesBtn.setBackground(new Color(219, 232, 228));
		showTypesBtn.setBounds(255, 163, 35, 25);
		contentPane.add(showTypesBtn);
		
		
		
	}
}
