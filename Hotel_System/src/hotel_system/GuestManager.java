package hotel_system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GuestManager {
	MY_CONNECTION my_connection = new MY_CONNECTION();
	
	// create Add function
	public boolean addGuest(Guest guest/*String fname, String lname, String phone, String email, String company*/) {
		
		PreparedStatement ps;
		String insertQuery = "INSERT INTO `guests`(`first_name`, `last_name`, `phone`, `email`, `company`, `accompanying`) VALUES (?,?,?,?,?,?)";
		
		try {
			ps = my_connection.createConnection().prepareStatement(insertQuery);
			
//			ps.setString(1, fname);
//			ps.setString(2, lname);
//			ps.setString(3, phone);
//			ps.setString(4, email);
//			ps.setString(5, company);
//			ps.setString(6, channel);
			
			ps.setString(1, guest.getFName());
			ps.setString(2, guest.getLName());
			ps.setString(3, guest.getPhone());
			ps.setString(4, guest.getEmail());
			ps.setString(5, guest.getCompany());
			ps.setInt(6, guest.getAccompaniedGuest()); //guest not accompanied by anybody
			
			return(ps.executeUpdate() > 0);
// executeQuery if the query returns only one ResultSet (such as a SELECT SQL statement), executeUpdate if the query does not return a ResultSet (such as an UPDATE SQL statement), or execute if the query might return more than one ResultSet object. 	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	};
	
	
	
	// create Edit function
	public boolean editGuest(Guest guest/*int id, String fname, String lname, String phone, String email, String company*/) {
	
		PreparedStatement ps;
		String updateQuery = "UPDATE `guests` SET `first_name`=?,`last_name`=?,`phone`=?,`email`=?,`company`=?, `accompanying`=? WHERE `id`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(updateQuery);
			
			ps.setString(1, guest.getFName());
			ps.setString(2, guest.getLName());
			ps.setString(3, guest.getPhone());
			ps.setString(4, guest.getEmail());
			ps.setString(5, guest.getCompany());
			ps.setInt(6, guest.getAccompaniedGuest());
			ps.setInt(7, guest.getID());
			
			return(ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	};
	// create Remove function
	public boolean removeGuest(int id) {
		PreparedStatement ps;
		String deleteQuery = "DELETE FROM `guests` WHERE `id`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(deleteQuery);
		
			ps.setInt(1, id);
			
			return(ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	};
	
	// create function that populates the jTable with all the guests in the db
	public void fillGuestsTable(JTable guestsTable) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `guests`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();
			
//			Object[] column = {"ID","First Name", "Last Name", "Phone", "Email", "Company", "Accompanying"};
			Object[] row;
			DefaultTableModel tableModel = (DefaultTableModel) guestsTable.getModel();
			
//			tableModel.setColumnIdentifiers(column);
			
			while (rs.next())
			{
				row = new Object[7];
				row[0] = rs.getInt(1);
				row[1] = rs.getString(2);
				row[2] = rs.getString(3);
				row[3] = rs.getString(4);
				row[4] = rs.getString(5);
				row[5] = rs.getString(6);
				row[6] = rs.getInt(7);
				
				tableModel.addRow(row);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	};
	
		public boolean addAccompanyingGuest(String fname, String lname, String phone, String email, String company, int accompanying) throws NoGuestToAccompanyException{
//			AccompanyingGuest accGuest = new AccompanyingGuest(fname, lname, phone, email, company, accompanying);
			
			PreparedStatement psSelect, ps;
			ResultSet rs;
			String selectQuery = "SELECT * FROM `reservations` WHERE `guest_id`=?";
			String insertQuery = "INSERT INTO `guests`(`first_name`, `last_name`, `phone`, `email`, `company`, `accompanying`) VALUES (?,?,?,?,?,?)";
			
			try {
				// check if the guest to accompany actually has a reservation
				psSelect = my_connection.createConnection().prepareStatement(selectQuery);
				psSelect.setInt(1, accompanying);
				rs = psSelect.executeQuery();

				// only attach guest to another guest when there exists a reservation made by the latter 
				if(rs.next()) {
					ps = my_connection.createConnection().prepareStatement(insertQuery);
					ps.setString(1, fname);
					ps.setString(2, lname);
					ps.setString(3, phone);
					ps.setString(4, email);
					ps.setString(5, company);
					ps.setInt(6, accompanying);
					
					return(ps.executeUpdate() > 0);
			
				}
				else {
					throw new NoGuestToAccompanyException("The Guest to be Accompanied does not have any reservation.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		};
		
		public void fillAccompanyingGuestsTable(JTable accGuestsTable, int accompanying) {
			PreparedStatement ps;
			ResultSet rs;
			String selectQuery = "SELECT * FROM `guests` WHERE `accompanying`=?";
			
			try {
				ps = my_connection.createConnection().prepareStatement(selectQuery);
				ps.setInt(1, accompanying);
				rs = ps.executeQuery();
				
				Object[] row;
				DefaultTableModel tableModel = (DefaultTableModel) accGuestsTable.getModel();
				
				while (rs.next())
				{
					row = new Object[7];
					row[0] = rs.getInt(1);
					row[1] = rs.getString(2);
					row[2] = rs.getString(3);
					row[3] = rs.getString(4);
					row[4] = rs.getString(5);
					row[5] = rs.getString(6);
					row[6] = rs.getInt(7);
					
					tableModel.addRow(row);
		
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
