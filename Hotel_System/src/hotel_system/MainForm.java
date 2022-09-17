package hotel_system;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class MainForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //or this.set[...];
		setBounds(100, 100, 800, 650); //or this.set[...];
		setResizable(false); //or this.set[...];
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu manageMenu = new JMenu("Manage >");
		menuBar.add(manageMenu);
		manageMenu.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JMenuItem guestsMenuItem = new JMenuItem("Guests");
		guestsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageGuestsForm guestsForm = new ManageGuestsForm();
				guestsForm.setVisible(true);
				guestsForm.setLocationRelativeTo(null);
				guestsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		manageMenu.add(guestsMenuItem);
		
		JSeparator separator = new JSeparator();
		manageMenu.add(separator);
		
		JMenuItem roomsMenuItem = new JMenuItem("Rooms");
		roomsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageRoomsForm roomsForm = new ManageRoomsForm();
				roomsForm.setVisible(true);
				roomsForm.setLocationRelativeTo(null);
				roomsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		manageMenu.add(roomsMenuItem);
		
		JSeparator separator_1 = new JSeparator();
		manageMenu.add(separator_1);
		
		JMenuItem reservationsMenuItem = new JMenuItem("Reservations");
		reservationsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageReservationsForm reservationsForm = ManageReservationsForm.getInstance();
				reservationsForm.setVisible(true);
				reservationsForm.setLocationRelativeTo(null);
				reservationsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		manageMenu.add(reservationsMenuItem);
		
		JButton logOutBtn = new JButton("Log Out");
		logOutBtn.setOpaque(true);
		logOutBtn.setBorderPainted(false);
		logOutBtn.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		logOutBtn.setBackground(new Color(255,255,255));
		
		menuBar.add(logOutBtn);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 650);
		contentPane.add(panel);
	}
}
