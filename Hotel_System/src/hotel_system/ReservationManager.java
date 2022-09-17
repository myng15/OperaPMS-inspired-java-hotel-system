package hotel_system;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationManager {
	MY_CONNECTION my_connection = new MY_CONNECTION();
	RoomManager roomManager = new RoomManager();
	
	private static java.sql.Date getDate(java.util.Date date){
			return new java.sql.Date(date.getTime());
	}
	
	public boolean isRoomAvailable(int roomNum, int rsvID, Date arrival, Date departure) {
		java.sql.Date desiredArrivalDate = getDate(arrival);
		java.sql.Date desiredDepartureDate = getDate(departure);
		
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT *\n" 
				+ "FROM `reservations` \n"
				+ "WHERE EXISTS"
				+ "(\n"
				+"    SELECT 1 FROM `reservations` rsv \n"
				+"    WHERE rsv.`room_no` = ? AND rsv.`id` <> ?\n"
				+"    AND \n"
				+"    (\n"
				+"         (? >= rsv.`arrival` AND ? < rsv.`departure`) \n"
				+"      OR (? <= rsv.`arrival` AND ? > rsv.`arrival`) \n"
				+"    )\n"
				+")";
// Logic: New reservation for a room can start on the same day as the departure day of that room's previous reservation. 
// Alternative: see comment in showAvailableRooms method below		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setInt(1, roomNum);
			ps.setInt(2, rsvID);
			ps.setDate(3, desiredArrivalDate);
			ps.setDate(4, desiredArrivalDate);
			ps.setDate(5, desiredArrivalDate);
			ps.setDate(6, desiredDepartureDate);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return false;
			} 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void showAvailableRooms (JTable table, Date arrival, Date departure) throws InvalidReservationDatesException {
		java.sql.Date desiredArrivalDate = getDate(arrival);
		java.sql.Date desiredDepartureDate = getDate(departure);
		
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT r.*\n"
				+ "FROM rooms r\n"
				+ "WHERE NOT EXISTS\n"
				+ "(\n"
				+ "    SELECT 1 FROM reservations rsv \n"
				+ "    WHERE rsv.`room_no` = r.`room_no` \n"
				+ "    AND \n"
				+ "    (\n"
				+ "         (? >= rsv.`arrival` AND ? < rsv.`departure`) OR\n"
				+ "		    (? > rsv.`arrival` AND ? <= rsv.`departure`) OR \n"
				+ "		    (? <= rsv.`arrival` AND ? >= rsv.`departure`)\n"
				
//				+ "         ? BETWEEN rsv.`arrival` AND rsv.`departure` OR\n"
//				+ "         ? BETWEEN rsv.`arrival` AND rsv.`departure` OR \n"
//				+ "         (? <= rsv.`arrival` AND ? >= rsv.`departure`)\n"
				+ "    )\n"
				+ ")";
		
// Alternative:
//		"SELECT r.*
//		FROM `rooms` r
//		WHERE NOT EXISTS
//		(
//		    SELECT 1 FROM `reservations` rsv
//		    WHERE rsv.`room_no` = r.`room_no` 
//		    AND 
//		    (
//		         (2021-10-21 >= rsv.`arrival` AND 2021-10-21 <= rsv.`departure`)
//		      OR (2021-10-21 <= rsv.`arrival` AND 2021-10-22 >= rsv.`arrival`)
//		    )
//		)"
		
		Date curDate = new Date();
		LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(curDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime comparedCurrentLocalDateTime = currentLocalDateTime.minus(23, ChronoUnit.HOURS);	
		LocalDateTime arrivalLocalDateTime = LocalDateTime.ofInstant(arrival.toInstant(), ZoneId.systemDefault());
		
		if(departure.compareTo(arrival) < 0 || arrivalLocalDateTime .isBefore(comparedCurrentLocalDateTime)) {
			throw new InvalidReservationDatesException("");
		} 
		
		else {
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setDate(1, desiredArrivalDate);
			ps.setDate(2, desiredArrivalDate);
			ps.setDate(3, desiredDepartureDate);
			ps.setDate(4, desiredDepartureDate);
			ps.setDate(5, desiredArrivalDate);
			ps.setDate(6, desiredDepartureDate);
			
//			ps.setDate(1, desiredArrivalDate);
//			ps.setDate(2, desiredDepartureDate);
//			ps.setDate(3, desiredArrivalDate);
//			ps.setDate(4, desiredDepartureDate);
			rs = ps.executeQuery();
			
			Object[] row;
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			
			while(rs.next()) {
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
		}
	}
	
	
	public boolean addReservation(Reservation reservation/*int guestID, int roomNum, String arrival, String departure, String rsvStatus, double rate, String channel, String creditCard, String expDate*/) throws InvalidReservationDatesException, RoomAvailabilityException {
		PreparedStatement ps;
		String insertQuery = "INSERT INTO `reservations`(`guest_id`, `room_no`, `arrival`, `departure`, `rsv_status`, `daily_rate`, `channel`, `credit_card`, `exp_date`) VALUES (?,?,?,?,?,?,?,?,?)";
		
		try {
			Date arrivalDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getArrivalDate());
			Date departureDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getDepartureDate());
			
			Date curDate = new Date();
			LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(curDate.toInstant(), ZoneId.systemDefault());
			LocalDateTime comparedCurrentLocalDateTime = currentLocalDateTime.minus(23, ChronoUnit.HOURS);	
			LocalDateTime arrivalLocalDateTime = LocalDateTime.ofInstant(arrivalDate.toInstant(), ZoneId.systemDefault());
			
// To convert LocalDateTime back to Date: 
// Ex: Date arrivalDate = Date.from(arrivalLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
			
			if(departureDate.compareTo(arrivalDate) < 0 || arrivalLocalDateTime .isBefore(comparedCurrentLocalDateTime)) {
				throw new InvalidReservationDatesException("");
			} 
			else if (!isRoomAvailable(reservation.getRoomNum(), reservation.getID(), arrivalDate, departureDate)){
				throw new RoomAvailabilityException("");
			}
			else {
				
				try {
					ps = my_connection.createConnection().prepareStatement(insertQuery);
					
					ps.setInt(1, reservation.getGuestID());
					ps.setInt(2, reservation.getRoomNum());
					ps.setDate(3, getDate(arrivalDate));
					ps.setDate(4, getDate(departureDate));
					ps.setString(5, reservation.getRsvStatus());
					ps.setDouble(6, reservation.getRate());
					ps.setString(7, reservation.getChannel());
					ps.setString(8, reservation.getCreditCard());
					ps.setString(9, reservation.getExpDate());
					
					return (ps.executeUpdate()>0); 
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean editReservation(Reservation reservation/*int id, int guestID, int roomNum, String arrival, String departure*/) 
		throws InvalidReservationDatesException, RoomAvailabilityException, GuestNotInHouseException, GuestAlreadyCheckedInException {
		
		PreparedStatement ps;
		String updateQuery = "UPDATE `reservations` SET `guest_id`=?,`room_no`=?,`arrival`=?,`departure`=?,`rsv_status`=?,`daily_rate`=?,`channel`=?,`credit_card`=?,`exp_date`=? WHERE `id`=?";
		
		try {
			Date arrivalDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getArrivalDate());
			Date departureDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getDepartureDate());
			
			Date curDate = new Date();
			LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(curDate.toInstant(), ZoneId.systemDefault());
			LocalDateTime comparedCurrentLocalDateTime = currentLocalDateTime.minus(23, ChronoUnit.HOURS);	
			LocalDateTime arrivalLocalDateTime = LocalDateTime.ofInstant(arrivalDate.toInstant(), ZoneId.systemDefault());

			int id = reservation.getID();
			int roomNum = reservation.getRoomNum();
			String rsvStatus = reservation.getRsvStatus();
			
			if(departureDate.compareTo(arrivalDate) < 0 || arrivalLocalDateTime .isBefore(comparedCurrentLocalDateTime)) {
				throw new InvalidReservationDatesException("Arrival Date must be from today onwards and earlier than Departure Date");
			} 
			else if (!isRoomAvailable(roomNum, id, arrivalDate, departureDate)){
				throw new RoomAvailabilityException("");
			}
			
			
			if (rsvStatus.equals("CHECKED OUT") && !isInHouse(id)) {
				throw new GuestNotInHouseException("");
//				JOptionPane.showMessageDialog(contentPane, "Guest is not in house", "Guest Not In House Error", JOptionPane.ERROR_MESSAGE);
			} 
			
			else if (!(rsvStatus.equals("CHECKED IN") || rsvStatus.equals("CHECKED OUT")) && isInHouse(id)) {
				throw new GuestAlreadyCheckedInException("");
//				JOptionPane.showMessageDialog(contentPane, "Guest has already checked in", "Guest Already Checked In Warning", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if (rsvStatus.equals("CHECKED IN") && !isInHouse(id)) {
					checkIn(reservation);

					roomManager.changeRoomFOStatus(roomNum, "OCCUPIED");
					roomManager.changeRoomHKStatus(roomNum, "DIRTY");
				}
				else if (rsvStatus.equals("CHECKED OUT") && isInHouse(id)) {
					checkOut(id);
					
					roomManager.changeRoomFOStatus(roomNum, "VACANT");
				}
				try {
					ps = my_connection.createConnection().prepareStatement(updateQuery);
					
					ps.setInt(1, reservation.getGuestID());
					ps.setInt(2, reservation.getRoomNum());
					ps.setDate(3, getDate(arrivalDate));
					ps.setDate(4, getDate(departureDate));
					ps.setString(5, reservation.getRsvStatus());
					ps.setDouble(6, reservation.getRate());
					ps.setString(7, reservation.getChannel());
					ps.setString(8, reservation.getCreditCard());
					ps.setString(9, reservation.getExpDate());
					ps.setInt(10, reservation.getID());
					
					return (ps.executeUpdate()>0);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean removeReservation(int id) {
		PreparedStatement ps;
		String deleteQuery = "DELETE FROM `reservations` WHERE `id`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(deleteQuery);
			
			ps.setInt(1, id);
			
			return (ps.executeUpdate()>0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void fillReservationsTable(JTable rsvTable) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `reservations`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();
			
			Object[] row;
			DefaultTableModel tableModel = (DefaultTableModel)rsvTable.getModel();
			
			while(rs.next()) {
				row = new Object[10];
				row[0] = rs.getInt(1);
				row[1] = rs.getInt(2);
				row[2] = rs.getInt(3); 
				row[3] = rs.getDate(4);
				row[4] = rs.getDate(5);
				row[5] = rs.getString(6);
				row[6] = rs.getDouble(7);
				row[7] = rs.getString(8);
				row[8] = rs.getString(9);
				row[9] = rs.getString(10);
				
				tableModel.addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getBookingGuestName(int bookingGuestID) {
		
		MY_CONNECTION my_connection = new MY_CONNECTION();
		PreparedStatement ps;
		ResultSet rs;
		String name = "";
		String selectQuery = "SELECT CONCAT(`first_name`, ' ', `last_name`) FROM `guests` WHERE `id`=?"; // MySQL doesn't support + or || for concatenation. "SELECT `first_name` + ' ' + `last_name` FROM `guests` WHERE `id`=?" wouldn't work.
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setInt(1, bookingGuestID);
			rs = ps.executeQuery();
			while(rs.next()) {
				name= rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public boolean checkIn(Reservation reservation) {
		PreparedStatement ps;
		String insertQuery = "INSERT INTO `in_house_guests`(`rsv_id`, `guest_id`, `guest_name`, `room_no`, `arrival`, `departure`, `balance`) VALUES (?,?,?,?,?,?,?)";
		
		try {
			Date arrivalDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getArrivalDate());
			Date departureDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(reservation.getDepartureDate());
			
				try {
					ps = my_connection.createConnection().prepareStatement(insertQuery);
					
					ps.setInt(1, reservation.getID());
					ps.setInt(2, reservation.getGuestID());
					ps.setString(3, getBookingGuestName(reservation.getGuestID()));
					ps.setInt(4, reservation.getRoomNum());
					ps.setDate(5, getDate(arrivalDate));
					ps.setDate(6, getDate(departureDate));
					ps.setDouble(7, reservation.getRate());
					
					return (ps.executeUpdate()>0); 
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			} catch(ParseException e) {
				return false;
			}
	}
	
	public boolean checkOut(int rsvID) {
		PreparedStatement ps;
		String deleteQuery = "DELETE FROM `in_house_guests` WHERE `rsv_id`=?";
		
		try {
			ps = my_connection.createConnection().prepareStatement(deleteQuery);
			
			ps.setInt(1, rsvID);
			
			return (ps.executeUpdate()>0);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isInHouse(int rsvID) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `in_house_guests` WHERE EXISTS \n"
						   + "(SELECT 1 FROM `in_house_guests` in_house \n"
						   + "WHERE in_house.`rsv_id` = ?)";
				
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			ps.setInt(1, rsvID);
			rs = ps.executeQuery();
			
			return(rs.next());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}		
	
	public void fillInHouseGuestsTable(JTable inHouseTable) {
		PreparedStatement ps;
		ResultSet rs;
		String selectQuery = "SELECT * FROM `in_house_guests`";
		
		try {
			ps = my_connection.createConnection().prepareStatement(selectQuery);
			rs = ps.executeQuery();
			
			Object[] row;
			DefaultTableModel tableModel = (DefaultTableModel)inHouseTable.getModel();
			
			while(rs.next()) {
				row = new Object[7];
				row[0] = rs.getInt(1);
				row[1] = rs.getInt(2);
				row[2] = rs.getString(3); 
				row[3] = rs.getInt(4);
				row[4] = rs.getDate(5);
				row[5] = rs.getDate(6);
				row[6] = rs.getDouble(7);
				
				tableModel.addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
