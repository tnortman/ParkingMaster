import java.sql.*;
import java.util.Vector;

/**
 * This class represents the database. 
 * It contains methods to interact with the database and encapsulates all SQL in one class.
 * 
 * @author moedmj27
 *
 */
public class Database {
	
	//TODO I've heard I shouldn't make these instance variables
	private Connection conn;
	private Statement st;
	
	/**
	 * This is the constructor for the Database class.
	 * 
	 * @throws SQLException
	 */
	public Database() throws SQLException
	{
		conn = DriverManager.getConnection("jdbc:ucanaccess://./Database/ParkingAutomationDB1.accdb");
		st = conn.createStatement();
	}
	
	public boolean isLevelAvailable(String level) {
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean levelAvailable = false;

	        String sql = "SELECT Level FROM ParkingSpaces WHERE Level = '" + level + "' AND PossibleSpot = TRUE AND AvailableSpot = TRUE";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Level"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	levelAvailable = false;
	        }
	        else
	        {
	        	levelAvailable = true;
	        }
	        
	        return levelAvailable;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean isRowAvailable(String level, String row) {
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean rowAvailable = false;

	        String sql = "SELECT Rows FROM ParkingSpaces WHERE Level = '" + level + "' AND Rows = '" + row + "' AND PossibleSpot = TRUE AND AvailableSpot = TRUE";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Rows"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	rowAvailable = false;
	        }
	        else
	        {
	        	rowAvailable = true;
	        }
	        
	        return rowAvailable;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param parkingSpace
	 * @return
	 */
	public boolean isSpaceAvailable(String parkingSpace) {
		try
		{
			boolean spaceAvailable = false;

	        String sql = "SELECT AvailableSpot FROM ParkingSpaces WHERE ParkingSpaceID = " + parkingSpace;
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	spaceAvailable = Boolean.parseBoolean(rs.getString(1));
	        } 
	        
	        return spaceAvailable;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	/**
	 * @param licensePlate
	 * @param parkingSpaceID
	 * @param password
	 * @param checkoutTime
	 * @param arrivalTime
	 * @param departureTime
	 * @param lengthOfStay
	 * @param checkoutDate
	 * @return
	 */
	public int insertUser(String licensePlate, String parkingSpaceID, String password, String checkoutTime, String arrivalTime, String departureTime, String lengthOfStay, String checkoutDate)
	{
		int rowsInserted = 0;
		String sql = "";
		
		try {
			
			if(!checkUserExists(licensePlate))//Check to see if the user exists
			{
				sql = "INSERT INTO Users VALUES ('" + licensePlate + "', " + parkingSpaceID + ", '"
						+ password + "', ";
				
				if(checkoutTime.equalsIgnoreCase("NULL"))
				{
					sql += checkoutTime + ", ";
				}
				else
				{
					sql += "#" + checkoutTime + "#, ";
				}
				
				if(arrivalTime.equalsIgnoreCase("NULL"))
				{
					sql += arrivalTime + ", ";
				}
				else
				{
					sql += "#" + arrivalTime + "#, ";
				}
				
				if(departureTime.equalsIgnoreCase("NULL"))
				{
					sql += departureTime + ", ";
				}
				else
				{
					sql += "#" + departureTime + "#, ";
				}
				
				if(lengthOfStay.equalsIgnoreCase("NULL"))
				{
					sql += lengthOfStay + ", ";
				}
				else
				{
					sql += "#" + lengthOfStay + "#, ";
				}
				
				if(checkoutDate.equalsIgnoreCase("NULL"))
				{
					sql += checkoutDate + "); ";
				}
				else
				{
					sql += "#" + checkoutDate + "#); ";
				}
			}
			else
			{
				sql = "UPDATE Users SET ParkingSpaceID = " + parkingSpaceID + ", Password = '" + password;
				
				if(checkoutTime.equalsIgnoreCase("NULL"))
				{
					sql += "', CheckOutTime = " + checkoutTime + ", ";
				}
				else
				{
					sql += "', CheckOutTime = #" + checkoutTime + "#, ";
				}
				
				if(arrivalTime.equalsIgnoreCase("NULL"))
				{
					sql += "ArrivalTime = " + checkoutTime + ", ";
				}
				else
				{
					sql += "ArrivalTime = #" + arrivalTime + "#, ";
				}
				
				if(departureTime.equalsIgnoreCase("NULL"))
				{
					sql += "DepartureTime = " + departureTime + ", ";
				}
				else
				{
					sql += "DepartureTime = #" + departureTime + "#, ";
				}
				
				if(lengthOfStay.equalsIgnoreCase("NULL"))
				{
					sql += "LengthOfStay = " + lengthOfStay + ", ";
				}
				else
				{
					sql += "LengthOfStay = #" + lengthOfStay + "#, ";
				}
				
				if(checkoutDate.equalsIgnoreCase("NULL"))
				{
					sql += "CheckOutDate = " + checkoutDate;
				}
				else
				{
					sql += "CheckOutDate = #" + checkoutDate + "#";
				}
				
				sql += " WHERE LicensePlate = '" + licensePlate + "'; ";
			}
			
			sql += "UPDATE ParkingSpaces SET AvailableSpot = FALSE WHERE ParkingSpaceID = " + parkingSpaceID + ";";
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
		
	}
	
	public boolean checkUserExists(String licensePlate) throws SQLException
	{
		boolean userExists = false;
		String users = "";
		
		String sql = "SELECT LicensePlate FROM Users WHERE LicensePlate = '" + licensePlate + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	users = rs.getString(1);
        } 
		
		if(users.equals(""))
		{
			userExists = false;
		}
		else
		{
			userExists = true;
		}
		
		// This was for debug purposes.
		//System.out.println(userExists);
		
		return userExists;
		//return users;
	}
	
	/**
	 * @param licensePlate
	 * @param checkinTime
	 * @param checkinDate
	 * @return
	 */
	public int insertReservations(String licensePlate, String checkinTime, String checkinDate)
	{
		int rowsInserted = 0;
		
		try {
			String sql = "Insert INTO Reservations VALUES " + "('" + licensePlate + "', #" + checkinTime + "#, #" + checkinDate + "#, "+"'false'"+")";
			sql += "UPDATE ParkingSpaces SET AvailableSpot = TRUE WHERE ParkingSpaceID = " + getUserParkingSpaceID(licensePlate) + ";";
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
		
	}
	
	/**
	 * @param parkingSpaceID
	 * @param level
	 * @param row
	 * @param space
	 * @param price
	 * @param possibleSpot
	 * @param availableSpot
	 * @param handicappedSpot
	 * @return
	 */
	public int insertParkingSpace(String parkingSpaceID, String level, String row, String space, String price, String possibleSpot, String availableSpot, String handicappedSpot)
	{
		int rowsInserted = 0;
		
		try {
			String sql = "Insert INTO Users VALUES " + "(" + parkingSpaceID + ", '" + level + "', '"
					+ row + "', '" + space + "', " + price + ", " + possibleSpot + ", " + availableSpot + ", " + handicappedSpot + ")";
			
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
		
		
	}
	
	public int update(String table, String columnName, String value, String licensePlate)
	{
		try{
	        String query = "UPDATE Users SET ";
	        query += columnName;
	        
	        if(columnName == "CheckOutTime" || columnName == "ArrivalTime" || columnName == "DepartureTime" || columnName == "LengthOfStay")
	        {
	        	query += " = #" + value + "#";
	        }
	        else
	        {
	        	query += " = " + value;
	        }
	        
	        query += " WHERE LicensePlate = '" + licensePlate + "'";
	        int rowsUpdated = st.executeUpdate(query);
	        return rowsUpdated;
	    }
	    catch(SQLException e) {
	        System.err.println(e);
	        return 0;
	    }

	}
	
	/**
	 * @param column
	 * @param table
	 * @param keyColumn
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Vector<Object> getColumnwithDuplicates(String column, String table, String keyColumn, String key) throws SQLException
	{
		Vector<Object> columnVector = new Vector<Object>();
		String sql;
		if(key.equalsIgnoreCase("NULL"))
			sql = "SELECT " + column + " FROM " + table;
		else
			sql = "SELECT " + column + " FROM " + table + " WHERE "+ keyColumn +" = '" + key + "'";
        
        ResultSet rs = st.executeQuery(sql);
        boolean first = true;
        while(rs.next())
        {
        	boolean copy = false;
        	if(first)
        	{
        		columnVector.add(rs.getObject(column));
        		first = false;
        	}
        	for(int i = 0; i<columnVector.size();i++)
        	{
        		if(columnVector.get(i).equals(rs.getObject(column)))
        			copy = true;
        	}
        	if(!copy)
        	{
        		columnVector.add(rs.getObject(column));
        	}
        	
        }
        return columnVector;
	}
	
	public Vector<Object> getColumnLevel() throws SQLException
	{
		Vector<Object> columnVector = new Vector<Object>();
		
		String sql = "SELECT DISTINCT Level FROM ParkingSpaces";
		        
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        	if(isLevelAvailable(rs.getObject("Level").toString()))
        	{
        		columnVector.add(rs.getObject("Level"));
        	}
        }
        return columnVector;
	}
	
	public Vector<Object> getColumnRow(String level) throws SQLException
	{
		Vector<Object> columnVector = new Vector<Object>();
		
		String sql = "SELECT DISTINCT Rows FROM ParkingSpaces WHERE Level = '" + level + "'";
		        
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        	if(isRowAvailable(level, rs.getObject("Rows").toString()) && isLevelAvailable(level))
        	{
        		columnVector.add(rs.getObject("Rows"));
        	}
        }
        return columnVector;
	}
	
	public Vector<Object> getColumnSpaces(String level, String row) throws SQLException
	{
		Vector<Object> columnVector = new Vector<Object>();
		
		String sql = "SELECT DISTINCT Space FROM ParkingSpaces WHERE Level = '" + level + "' AND Rows = '" + row + "'";
		
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        	String spaceID = getParkingSpaceID(level, row, rs.getObject("Space").toString());
        	if(isSpaceAvailable(spaceID) && isRowAvailable(level, row) && isLevelAvailable(level))
        	{
        		columnVector.add(rs.getObject("Space"));
        	}
        }
        return columnVector;
	}

	
	/**
	 * @param table
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	public Vector<Object> getColumn(String table, String column) throws SQLException
	{
		Vector<Object> columnVector = new Vector<Object>();
		
		String sql = "SELECT " + column + " FROM " + table;
        
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        	columnVector.add(rs.getObject(column));
        }
        return columnVector;
	}
	/**
	 * Returns a resultSet to be processed by the front end(turns it into a vector of ReservationForms)
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getReservationsResultSet() throws SQLException
	{
		String sql = "SELECT Users.LicensePlate, Users.Password, Reservations.CheckInTime, Reservations.CheckInDate, Users.ParkingSpaceID "
				+ "FROM Users INNER JOIN Reservations ON Users.LicensePlate=Reservations.LicensePlate;";
		ResultSet rs = st.executeQuery(sql);
		return rs;
		
	}
	
	public String getUserParkingSpaceID(String licensePlate) throws SQLException
	{
		String parkingSpaceID = "";
		
		String sql = "SELECT ParkingSpaceID FROM Users WHERE LicensePlate = '" + licensePlate + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	parkingSpaceID = rs.getString(1);
        } 
		
		return parkingSpaceID;
	}
	
	/**
	
 * @param level
	 * @param row
	 * @param space
	 * @return
	 * @throws SQLException
	 */
	public String getParkingSpaceID(String level, String row, String space) throws SQLException
	{
		String parkingSpaceID = "";
		
		String sql = "SELECT ParkingSpaceID FROM ParkingSpaces WHERE Level = '" + level 
				+ "' AND Rows = '" + row + "' AND Space = '" + space + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	parkingSpaceID = rs.getString(1);
        } 
		
		return parkingSpaceID;
	}
	/**
	 * Returns the level corresponded with the parkingSpaceID
	 * @param parkingSpaceID
	 * @return
	 * @throws SQLException
	 */
	public String getParkingSpaceLevel(String parkingSpaceID) throws SQLException
	{
		String sql = "SELECT ParkingSpaces.Level FROM ParkingSpaces WHERE ParkingSpaceID = " + parkingSpaceID +";";
		ResultSet rs = st.executeQuery(sql);
		String level = "";
		while(rs.next())
		{
			level = rs.getString(1);
		}
		return level;
	}
	/**
	 * Returns the row corresponded with the parkingSpaceID
	 * @param parkingSpaceID
	 * @return
	 * @throws SQLException
	 */
	public String getParkingSpaceRow(String parkingSpaceID) throws SQLException
	{
		String sql = "SELECT ParkingSpaces.Rows FROM ParkingSpaces WHERE ParkingSpaces.ParkingSpaceID = " + parkingSpaceID +";";
		ResultSet rs = st.executeQuery(sql);
		String row = "";
		while(rs.next())
		{
			row = rs.getString(1);
		}
		return row;
	}
	/**
	 * Returns the space corresponded with the parkingSpaceID
	 * @param parkingSpaceID
	 * @return
	 * @throws SQLException
	 */
	public String getParkingSpaceSpace(String parkingSpaceID) throws SQLException
	{
		String sql = "SELECT ParkingSpaces.Space FROM ParkingSpaces WHERE ParkingSpaces.ParkingSpaceID = " + parkingSpaceID +";";
		ResultSet rs = st.executeQuery(sql);
		String space = "";
		while(rs.next())
		{
			space = rs.getString(1);
		}
		return space;
	}
	
	public double getParkingSpacePrice(String level, String row, String space) throws SQLException
	{
		
		double parkSpaceIDPrice = 0;
		String parkSpaceID = getParkingSpaceID(level, row, space);
		String sql = "SELECT Price FROM ParkingSpaces WHERE ParkingSpaceID = '" + parkSpaceID + "'";
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
		{
			parkSpaceIDPrice = rs.getDouble(1);	
		}
		return parkSpaceIDPrice;
	}
	public void reservationCheckIn(String licensePlate) throws SQLException
	{
		String sql = "UPDATE ParkingSpaces SET AvailableSpot = FALSE WHERE ParkingSpaceID = " + getUserParkingSpaceID(licensePlate) + " ";
		sql += "UPDATE Reservations SET CheckedIn = TRUE WHERE LicensePlate = '"+licensePlate+"';";
		st.execute(sql);
	}
	
	public int deleteReservation(String licensePlate)
	{
		int rowsDeleted = 0;
		try 
		{
			
			String sql = "DELETE FROM Reservations WHERE LicensePlate = '" + licensePlate + "'";
		
			rowsDeleted= st.executeUpdate(sql);
			
			return rowsDeleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rowsDeleted;
		}	
	}
	
	public boolean userHasReservation(String licensePlate)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean userHasReservation = false;

	        String sql = "SELECT LicensePlate FROM Reservations WHERE LicensePlate = '" + licensePlate + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("LicensePlate"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	userHasReservation = false;
	        }
	        else
	        {
	        	userHasReservation = true;
	        }
	        
	        return userHasReservation;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
		
	}
	
	public int deleteUser(String licensePlate)
	{
		String sql = "";
		int rowsDeleted = 0;
		try 
		{
			sql += "UPDATE ParkingSpaces SET AvailableSpot = TRUE WHERE ParkingSpaceID = " + getUserParkingSpaceID(licensePlate) + "; ";
			
			if(!userHasReservation(licensePlate))
			{
				sql += "DELETE FROM Users WHERE LicensePlate = '" + licensePlate + "'";
			}
			else
			{
				sql += "DELETE FROM Reservations WHERE LicensePlate = '" + licensePlate + "';"
					+ " DELETE FROM Users WHERE LicensePlate = '" + licensePlate + "';";
			}
		
			rowsDeleted= st.executeUpdate(sql);
			
			return rowsDeleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rowsDeleted;
		}
			
	}
	
	

	public boolean isIDTaken(String ID)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean IDTaken = false;

	        String sql = "SELECT ParkingSpaceID FROM ParkingSpaces WHERE ParkingSpaceID = '" + ID + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("ParkingSpaceID"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	IDTaken = false;
	        }
	        else
	        {
	        	IDTaken = true;
	        }
	        
	        return IDTaken;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public int setParkingSpacePrice(String level, String row, String space, String price) throws SQLException
	{
		int rowsInserted = 0;
		String sql = "";
		String parkingSpaceID = getParkingSpaceID(level, row, space);
		
		try {
			sql = "UPDATE ParkingSpaces SET Price = " + price;
			
			sql += " WHERE ParkingSpaceID = " + parkingSpaceID;
			
			
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
	}
	
	public int setParkingSpacePossible(String level, String row, String space, String possible) throws SQLException
	{
		int rowsInserted = 0;
		String sql = "";
		String parkingSpaceID = getParkingSpaceID(level, row, space);
		
		try {
			sql = "UPDATE ParkingSpaces SET PossibleSpot = " + possible;
			
			sql += " WHERE ParkingSpaceID = " + parkingSpaceID;
			
			
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
	}
	
	public boolean isParkingSpacePossible(String level, String row, String space) throws SQLException
	{
		boolean possible = false;
		
		String sql = "SELECT PossibleSpot FROM ParkingSpaces WHERE Level = '" + level 
				+ "' AND Rows = '" + row + "' AND Space = '" + space + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	possible = rs.getBoolean(1);
        } 
		
		return possible;
	}
	
	public int setParkingSpaceAvailable(String level, String row, String space, String available) throws SQLException
	{
		int rowsInserted = 0;
		String sql = "";
		String parkingSpaceID = getParkingSpaceID(level, row, space);
		
		try {
			sql = "UPDATE ParkingSpaces SET AvailableSpot = " + available;
			
			sql += " WHERE ParkingSpaceID = '" + parkingSpaceID + "'";
			
			
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
	}
	
	public boolean isParkingSpaceAvailable(String level, String row, String space) throws SQLException
	{
		boolean available = false;
		
		String sql = "SELECT AvailableSpot FROM ParkingSpaces WHERE Level = '" + level 
				+ "' AND Rows = '" + row + "' AND Space = '" + space + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	available = rs.getBoolean(1);
        } 
		
		return available;
	}
	
	public int setParkingSpaceHandicapped(String level, String row, String space, String handicap) throws SQLException
	{
		int rowsInserted = 0;
		String sql = "";
		String parkingSpaceID = getParkingSpaceID(level, row, space);
		
		try {
			sql = "UPDATE ParkingSpaces SET HandicappedSpot = " + handicap;
			
			sql += " WHERE ParkingSpaceID = '" + parkingSpaceID + "'";
			
			
			rowsInserted = st.executeUpdate(sql);
			
			return rowsInserted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return rowsInserted;
		}
	}
	
	public boolean isParkingSpaceHandicapped(String level, String row, String space) throws SQLException
	{
		boolean handicapped = false;
		
		String sql = "SELECT HandicappedSpot FROM ParkingSpaces WHERE Level = '" + level 
				+ "' AND Rows = '" + row + "' AND Space = '" + space + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	handicapped = rs.getBoolean(1);
        } 
		
		return handicapped;
	}
	

	public int deleteParkingSpace(String level, String row, String space)
	{
		int rowsDeleted = 0;
		try 
		{
			
			String sql = "DELETE FROM ParkingSpaces WHERE Spaces = '" + space + "' AND Rows = '" + row + "' AND Level = '" + level + "'";
		
			rowsDeleted= st.executeUpdate(sql);
			
			return rowsDeleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rowsDeleted;
		}
	}
	
	public int deleteEntireRow(String level, String row)
	{
		int rowsDeleted = 0;
		try 
		{
			
			String sql = "DELETE FROM ParkingSpaces WHERE Rows = '" + row + "' AND Level = '" + level + "'";
		
			rowsDeleted= st.executeUpdate(sql);
			
			return rowsDeleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rowsDeleted;
		}
	}
	
	public int deleteEntireLevel(String level)
	{
		int rowsDeleted = 0;
		try 
		{
			
			String sql = "DELETE FROM ParkingSpaces WHERE Level = '" + level + "'";
		
			rowsDeleted= st.executeUpdate(sql);
			
			return rowsDeleted;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rowsDeleted;
		}
	}
	
	public boolean doesLevelExist(String level)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean levelExists = false;

	        String sql = "SELECT Level FROM ParkingSpaces WHERE Level = '" + level + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Level"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	levelExists = false;
	        }
	        else
	        {
	        	levelExists = true;
	        }
	        
	        return levelExists;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean doesRowExist(String level, String row)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean rowExists = false;

	        String sql = "SELECT Rows FROM ParkingSpaces WHERE Rows = '" + row + "' AND Level = '" + level + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Rows"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	rowExists = false;
	        }
	        else
	        {
	        	rowExists = true;
	        }
	        
	        return rowExists;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean doesParkingSpaceExist(String level, String row, String space)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean spaceExists = false;

	        String sql = "SELECT Spaces FROM ParkingSpaces WHERE Spaces = '" + space + "' AND Rows = '" + row + "' AND Level = '" + level + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Spaces"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	spaceExists = false;
	        }
	        else
	        {
	        	spaceExists = true;
	        }
	        
	        return spaceExists;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean verifyUser(String licensePlate, String password)
	{
		try
		{
			Vector<Object> columnVector = new Vector<Object>();
			boolean passwordMatch = false;

	        String sql = "SELECT Password FROM Users WHERE LicensePlate = '" + licensePlate + "' AND Password = '" + password + "'";
	        
	        ResultSet rs = st.executeQuery(sql);
	        
	        while(rs.next())
	        {
	        	columnVector.add(rs.getObject("Password"));
	        } 
	        
	        if(columnVector.isEmpty())
	        {
	        	passwordMatch = false;
	        }
	        else
	        {
	        	passwordMatch = true;
	        } 
	        
	        return passwordMatch;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			
			return false;
		}
	}
	public String getCheckOutTime(String licensePlate) throws SQLException
	{
		
		String checkOutTime = "";
		String sql = "SELECT CheckOutTime FROM Users WHERE LicensePlate = '"+ licensePlate +"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			checkOutTime = rs.getString(1);
		}
		return checkOutTime;
	}
	public String getCheckOutDate(String licensePlate) throws SQLException
	{
		String checkOutDate = "";
		String sql = "SELECT CheckOutDate FROM Users WHERE LicensePlate = '"+ licensePlate +"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			checkOutDate = rs.getString(1);
		}
		return checkOutDate;
	}
	public String getCheckInTime(String licensePlate) throws SQLException
	{
		String checkInTime = "";
		String sql = "SELECT CheckInTime FROM Reservations WHERE LicensePlate = '"+ licensePlate +"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			checkInTime = rs.getString(1);
		}
		return checkInTime;
	}
	public String getCheckInDate(String licensePlate) throws SQLException
	{
		String checkInDate = "";
		String sql = "SELECT CheckInDate FROM Reservations WHERE LicensePlate = '"+ licensePlate +"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			checkInDate = rs.getString(1);
		}
		return checkInDate;
	}
	
	public boolean isReservationCheckedIn(String licensePlate) throws SQLException
	{
		boolean checkedIn = false;
		String sql = "SELECT CheckedIn FROM Reservations WHERE LicensePlate = '" + licensePlate + "'";
        
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
        {
        	checkedIn = rs.getBoolean(1);
        } 
		
		return checkedIn;
	}

	
	/**
	 * 
	 */
	public void closeConnection()
	{
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
