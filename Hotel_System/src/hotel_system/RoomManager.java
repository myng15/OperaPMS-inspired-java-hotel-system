package hotel_system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RoomManager {
	MY_CONNECTION my_connection = new MY_CONNECTION();
	
	public boolean addRoom(int roomNum, String roomType, String FOStatus, String HKStatus) {
		PreparedStatement ps;
		String insertQuery = "INSERT INTO `rooms`(`room_no`, `type`, `fo_status`, `hk_status`) VALUES (?,?,?,?)";
		
		try {
			ps = my_connection.createConnection().prepareStatement(insertQuery);
			
			ps.setInt(1, roomNum);
			ps.setString(2, roomType);
			ps.setString(3, FOStatus);
			ps.setString(4, HKStatus);
			
			return (ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean editRoom(int roomNum, String roomType, String FOStatus, String HKStatus) {
		PreparedStatement ps;
		String updateQuery = "UPDATE `rooms` SET `type`=?,`fo_status`=?,`hk_status`=? WHERE `room_no`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(updateQuery);
			
			ps.setString(1, roomType);
			ps.setString(2, FOStatus);
			ps.setString(3, HKStatus);
			ps.setInt(4, roomNum);
			
			return (ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	};
	

	public boolean removeRoom(int roomNum) {
		PreparedStatement ps;
		String deleteQuery = "DELETE FROM `rooms` WHERE `room_no`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(deleteQuery);
			
			ps.setInt(1, roomNum);
			
			return (ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	};
	
	public void fillRoomTypesTable(JTable roomTypesTable) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `room_types`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();
			
			Object[] row;
			
			DefaultTableModel tableModel = (DefaultTableModel)roomTypesTable.getModel();

			while (rs.next()) {
				row = new Object[6];
				row[0] = rs.getString(1);
				row[1] = rs.getString(2);
				row[2] = rs.getString(3);
				row[3] = rs.getInt(4);
				row[4] = rs.getInt(5);
				row[5] = rs.getDouble(6);
				
				tableModel.addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

// fill Room Type ComboBox with the room type ID
	public void fillRoomTypeBox(JComboBox<String> roomTypeBox) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `room_types`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				roomTypeBox.addItem(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public void fillRoomsTable(JTable roomsTable) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `rooms`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();
			
			Object[] row;
			
			DefaultTableModel tableModel = (DefaultTableModel)roomsTable.getModel();

			while (rs.next()) {
				row = new Object[4];
				row[0] = rs.getInt(1);
				row[1] = rs.getString(2);
				row[2] = rs.getString(3);
				row[3] = rs.getString(4);
				
				tableModel.addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public String getRoomType(int roomNum) {
		MY_CONNECTION my_connection = new MY_CONNECTION();
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT `type` FROM `rooms` WHERE `room_no`=?"; 
		String roomType = "";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setInt(1, roomNum);
			rs = ps.executeQuery();
			while(rs.next()) {
				roomType = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roomType;
	}
	
	public double getRoomRate(String roomType) {
		MY_CONNECTION my_connection = new MY_CONNECTION();
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT `rack_rate` FROM `room_types` WHERE `type_id`=?"; 
		double rate = 0;
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setString(1, roomType);
			rs = ps.executeQuery();
			while(rs.next()) {
				rate = rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rate;
	}
	
	public boolean changeRoomFOStatus(int roomNum, String status) {
		PreparedStatement ps;
		String updateQuery = "UPDATE `rooms` SET `fo_status`=? WHERE `room_no`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(updateQuery);
			
			ps.setString(1, status);
			ps.setInt(2, roomNum);
			
			return (ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean changeRoomHKStatus(int roomNum, String status) {
		PreparedStatement ps;
		String updateQuery = "UPDATE `rooms` SET `hk_status`=? WHERE `room_no`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(updateQuery);
			
			ps.setString(1, status);
			ps.setInt(2, roomNum);
			
			return (ps.executeUpdate() > 0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
