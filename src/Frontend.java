import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;

public class Frontend 
{
	private Database parkingMasterDB;
	private double feeChargePerMinute = 0.15;
	
	public Frontend() throws SQLException
	{
		parkingMasterDB = new Database();
	}
	
	/**
	* Checks if a reservation for the given license plate number already exists in the database.
	*
	* @param plate the license plate number to check
	* @return true if a reservation with the given plate number already exists, false if not.
	*/
	public boolean doesReservationExist(String plate, String password)
	{
		try {
			return parkingMasterDB.checkUserExists(plate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public void sendForm(ParkNowForm pnf) throws SQLException
	{
		parkingMasterDB.insertUser(pnf.getPlate(), parkingMasterDB.getParkingSpaceID(pnf.getLevel(), pnf.getRow(), pnf.getSpace()), 
				pnf.getPassword(), pnf.getCheckOutTimeString(), pnf.getArrivalTimeString(),"12:00:00AM", pnf.getDurationString(), pnf.getCheckOutDateString());
	}
	
	public void sendForm(ReservationForm rf) throws SQLException
	{
		parkingMasterDB.insertUser(rf.getPlate(), parkingMasterDB.getParkingSpaceID(rf.getLevel(), rf.getRow(), rf.getSpace()), rf.getPassword(), rf.getCheckOutTimeString(),
				rf.getArrivalTimeString(),"01/12/1994", rf.getDurationString(), rf.getCheckOutDateString());
		parkingMasterDB.insertReservations(rf.getPlate(), rf.getCheckInTimeString(), rf.getCheckInDateString());
	}
	
	public DefaultComboBoxModel getLevels() throws SQLException
	{
		Vector<Object> tempVect = parkingMasterDB.getColumnwithDuplicates("Level","ParkingSpaces","NULL","NULL");
		return new DefaultComboBoxModel(tempVect);
	}
	
	public DefaultComboBoxModel getTimes()
	{
		Vector<Object> tempVect = new Vector<Object>();
		Calendar displayableTimes = Calendar.getInstance();
		//Rouding to nearest half hour
		if(displayableTimes.get(Calendar.MINUTE) < 15)
		{
			displayableTimes.set(Calendar.MINUTE, 0);
		}
		else if((15 <= displayableTimes.get(Calendar.MINUTE)) && (displayableTimes.get(Calendar.MINUTE) <= 30) || ((30< displayableTimes.get(Calendar.MINUTE)) && (displayableTimes.get(Calendar.MINUTE) < 45)))
		{
			displayableTimes.set(Calendar.MINUTE, 30);
		}
		else if((45<= displayableTimes.get(Calendar.MINUTE)) && (displayableTimes.get(Calendar.MINUTE) <= 60))
		{
			displayableTimes.set(Calendar.MINUTE, 0);
			displayableTimes.set(Calendar.HOUR, (1+(displayableTimes.get(Calendar.HOUR))));
		}
		displayableTimes.set(Calendar.SECOND, 0);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssaa");
	
		for(int i = 0; i < 48; i++)
		{
			tempVect.add((String)timeFormat.format(displayableTimes.getTime()));
			displayableTimes.add(Calendar.MINUTE, 30);
		}
		
		return new DefaultComboBoxModel(tempVect);
	}
	
	public DefaultComboBoxModel getDurations()
	{
		Vector<Object> tempVect = new Vector<Object>();
		int hours = 0, minutes = 0;
		String duration;
		for(int i =0; i<24; i++)
		{
			minutes = minutes + 30;
			if(minutes == 60)
			{
				minutes = 0;
			}
			if(i%2 != 0)
			{
				hours++;
			}
			if(minutes == 30)
				duration = Integer.toString(hours) + "hr "+Integer.toString(minutes) + "min";
			else
				duration = Integer.toString(hours) + "hr "+Integer.toString(minutes) + "0min";
			tempVect.addElement(duration);
		}
		tempVect.addElement("24hr 00min");
		tempVect.addElement("48hr 00min");
		return new DefaultComboBoxModel(tempVect);
	}
	
	public DefaultComboBoxModel getSpaces(String level, String row) throws SQLException
	{
		Vector<Object> tempVect = parkingMasterDB.getColumnSpaces(level, row);
		return new DefaultComboBoxModel(tempVect);
	}
	
	public DefaultComboBoxModel getRows(String level) throws SQLException
	{
		Vector<Object> tempVect = parkingMasterDB.getColumnRow(level);
		return new DefaultComboBoxModel(tempVect);
	}
	
	public DefaultComboBoxModel getDates() throws SQLException
	{
		Vector<Object> tempVect = new Vector<Object>();
		Date myDate = new Date();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		tempVect.addElement(formatter.format(myDate));
		for(int i = 0; i<7; i++)
		{
			myDate.setDate(myDate.getDate() + 1);
			String tempString = formatter.format(myDate);
			tempVect.addElement(tempString);
		}
		return new DefaultComboBoxModel(tempVect);
	}
	
	public Vector<ReservationForm> getReservations() throws NumberFormatException, SQLException
	{
		Vector<ReservationForm> resVect = new Vector<ReservationForm>();
		ResultSet rs = parkingMasterDB.getReservationsResultSet();
		while(rs.next())
		{
			/**Creating a reservationForm that will hold the five returned results from the SQL query
 			 *License Plate, Password, 
 			 *CheckInTime and CheckInDate (which will be turned into a Calendar Object)
 			 *and Parking Space ID(which will be turned into a level row and space
 			*/
			ReservationForm tempRForm = new ReservationForm();
			tempRForm.setPlate(rs.getString("LicensePlate"));
			tempRForm.setPassword(rs.getString("Password"));
			
			//Getting information needed to set up checkInCal for ReservationForm
			String tempCheckInDate = rs.getString("CheckInDate"); 
			String tempCheckInTime = rs.getString("CheckInTime");
			Calendar tempCheckInCal = getCalendar(tempCheckInDate, tempCheckInTime);

			tempRForm.setCheckInCal(tempCheckInCal);
			
			tempRForm.setLevel(parkingMasterDB.getParkingSpaceLevel(rs.getString("ParkingSpaceID")));
			tempRForm.setRow(parkingMasterDB.getParkingSpaceRow(rs.getString(5)));
			tempRForm.setSpace(parkingMasterDB.getParkingSpaceSpace(rs.getString(5)));
			//Now we send the completed reservation form with the information we need to the temp vector to be returned by the function
			resVect.add(tempRForm);
		}
		return resVect;
	}
	
	public double getParkingSpacePrice(String level, String row, String space) throws SQLException
	{
		if(level.equals("")||row.equals("")||space.equals(""))
		{
			return 0.0;
		}
		else
		{
			double parkingSpacePrice = parkingMasterDB.getParkingSpacePrice(level, row, space);
			return parkingSpacePrice;
		}
		
		
	}
	
	public void setParkingSpacePrice(String level, String row, String space, double price) throws SQLException
	{
		parkingMasterDB.setParkingSpacePrice(level, row, space, Double.toString(price));
	}
	
	public void setParkingSpacePossible(String level, String row, String space, boolean possible) throws SQLException
	{
		parkingMasterDB.setParkingSpacePossible(level, row, space, Boolean.toString(possible));
	}
	
	public boolean isParkingSpacePossible(String level, String row, String space) throws SQLException
	{
		boolean available = parkingMasterDB.isParkingSpacePossible(level, row, space);
		return available;
	}
	
	public void setParkingSpaceAvailable(String level, String row, String space, boolean available) throws SQLException
	{
		parkingMasterDB.setParkingSpaceAvailable(level, row, space, Boolean.toString(available));
	}
	
	public boolean isParkingSpaceAvailable(String level, String row, String space) throws SQLException
	{
		boolean available = parkingMasterDB.isParkingSpaceAvailable(level, row, space);
		return available;
	}
	
	public void setParkingSpaceHandicapped(String level, String row, String space, boolean handicap) throws SQLException
	{
		parkingMasterDB.setParkingSpaceHandicapped(level, row, space, Boolean.toString(handicap));
	}
	
	public boolean isParkingSpaceHandicapped(String level, String row, String space) throws SQLException
	{
		boolean handicapped = parkingMasterDB.isParkingSpaceHandicapped(level, row, space);
		return handicapped;
	}
	
	public boolean doesLevelExist(String level) throws SQLException
	{
		boolean exists = parkingMasterDB.doesLevelExist(level);
		return exists;
	}
	
	public void addLevel(String level, int rows, int spaces, double price) throws SQLException
	{
		int currentID = 1;
		for (int r = 0; r < rows; r++)
		{
			for (int s = 0; s < spaces; s++)
			{
				while(!parkingMasterDB.isIDTaken(Integer.toString(currentID)))
				{
					currentID++;
				}
					
				parkingMasterDB.insertParkingSpace(Integer.toString(currentID), level, Integer.toString(r), Integer.toString(s), Double.toString(price), "true", "true", "false"); 
				currentID++;
			}
		}
	}

	public void deleteLevel(String level) throws SQLException
	{
		parkingMasterDB.deleteEntireLevel(level);
	}
	
	public boolean doesRowExist(String level, String row) throws SQLException
	{
		boolean exists = parkingMasterDB.doesRowExist(level, row);
		return exists;
	}
	
	public void addRow(String level, String row, int spaces, double price) throws SQLException
	{
		int currentID = 1;
		for (int s = 0; s < spaces; s++)
		{
			while(!parkingMasterDB.isIDTaken(Integer.toString(currentID)))
			{
				currentID++;
			}
				
			parkingMasterDB.insertParkingSpace(Integer.toString(currentID), level, row, Integer.toString(s), Double.toString(price), "true", "true", "false"); 
			currentID++;
		}
	}
	
	public void deleteRow(String level, String row) throws SQLException
	{
		parkingMasterDB.deleteEntireRow(level, row);
	}
	
	public boolean doesParkingSpaceExist(String level, String row, String space) throws SQLException
	{
		boolean exists = parkingMasterDB.doesParkingSpaceExist(level, row, space);
		return exists;
	}
	
	public void addParkingSpace(String level, String row, String space, double price) throws SQLException
	{
		int currentID = 1;
		while(!parkingMasterDB.isIDTaken(Integer.toString(currentID)))
		{
			currentID++;
		}
			
		parkingMasterDB.insertParkingSpace(Integer.toString(currentID), level, row, space, Double.toString(price), "true", "true", "false"); 
	}
	
	public void deleteParkingSpace(String level, String row, String space) throws SQLException
	{
		parkingMasterDB.deleteParkingSpace(level, row, space);
	}
	
	public void deleteUser(String licensePlate) throws SQLException
	{
		parkingMasterDB.deleteUser(licensePlate);
	}
	public boolean verifyUserPassword(String licensePlate, String password) throws SQLException
	{
		if(parkingMasterDB.verifyUser(licensePlate, password))
			return true;
		else 
			return false;
	}
	public boolean validCheckInTime(String licensePlate) throws SQLException
	{
		TimeUnit timeUnit = TimeUnit.MINUTES;
		Calendar currentCal = Calendar.getInstance();
		Calendar checkInCal = getCalendar(parkingMasterDB.getCheckInDate(licensePlate), parkingMasterDB.getCheckInTime(licensePlate));
		currentCal.set(Calendar.SECOND, 0);
		long timeDiff = checkInCal.getTimeInMillis() - currentCal.getTimeInMillis();
		
		int minutes = (int) ((timeDiff / (1000*60)) % 60);
		
		if(minutes > 0)
		{
			return false;
		}
		else
			return true;
	}
	public void reservationCheckIn(String licensePlate) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssaa");
		//parkingMasterDB.update("Users", "ArrivalTime", timeFormat.format(cal.getTime().toString()), licensePlate);
		//parkingMasterDB.update("Reservations", "CheckedIn", "true", licensePlate);
		parkingMasterDB.reservationCheckIn(licensePlate);
	}
	public double checkForFeeDue(String licensePlate) throws SQLException, ParseException
	{
		
		TimeUnit timeUnit = TimeUnit.MINUTES;
		Calendar currentCal = Calendar.getInstance();
		Calendar checkOutCal = getCalendar(parkingMasterDB.getCheckOutDate(licensePlate), parkingMasterDB.getCheckOutTime(licensePlate));
		currentCal.set(Calendar.SECOND, 0);
		
		if(currentCal.getTimeInMillis()>checkOutCal.getTimeInMillis())
		{
			
			long timeDiff = currentCal.getTimeInMillis() - checkOutCal.getTimeInMillis();
			int minutes = (int) ((timeDiff / (1000*60)) % 60);
			return feeChargePerMinute*timeUnit.convert(timeDiff, TimeUnit.MILLISECONDS);
		}
		else
			return 0;
	}
	
	private Calendar getCalendar(String date, String time)
	{
	
		int tempCheckInYear = Integer.parseInt(date.substring(0,4));
		int tempCheckInMonth = Integer.parseInt(date.substring(5, 7));
		int tempCheckInDay = Integer.parseInt(date.substring(8, 10));
		
		int tempCheckInHour = Integer.parseInt(time.substring(11, 13));
		int tempCheckInMinute = Integer.parseInt(time.substring(14, 16));
		
		//AM_PM: 0 is AM, 1 is PM
		int AM_PM = 0;
		if(tempCheckInHour>=12)
			AM_PM = 1;
		if(tempCheckInHour>12)
			tempCheckInHour-=12;
		
		Calendar tempCal = Calendar.getInstance();
		//Setting tempCal to DB Values
		tempCal.set(tempCheckInYear, tempCheckInMonth-1, tempCheckInDay);
		tempCal.set(Calendar.HOUR_OF_DAY, tempCheckInHour);
		tempCal.set(Calendar.MINUTE, tempCheckInMinute);
		tempCal.set(Calendar.SECOND, 00);
		tempCal.set(Calendar.AM, AM_PM); 
		return tempCal;
	}
	public boolean isReservationCheckedIn(String licensePlate) throws SQLException
	{
		if(parkingMasterDB.isReservationCheckedIn(licensePlate))
			return true;
		else
			return false;
	}
	
	
}
