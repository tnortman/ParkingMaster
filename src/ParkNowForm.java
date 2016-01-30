import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ParkNowForm 
{
	private String level;		// The chosen level of Parking garage as an upper-case letter
	private String row;			// The chosen row within the level
	private String space;			// The chosen space within the row
	private int duration;		// How long the customer has the space for (in minutes)
	private String plate;		// License plate number
	private String password;	// Password for reservation
	//private ParkingDate checkOutDate;
	private Calendar arrivalCal;	//Will tell the time of arrival
	private Calendar checkOutCal;
	//private String arrivalTimeString;//Holds string of arrival time
	//private ParkingTime checkOutTime;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ssaa");
	
	/**
	 * Generates a string of random characters and numbers for use as a randomly generated password
	 * 
	 * @param length the length of the string to generate
	 * @return the string of random characters
	 */
	private String generatePassword(int length)
	{
		Random rand = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
		String pass = "";
		
		for (int i = 0; i < length; i++)
		{
			char n = alphabet.charAt(rand.nextInt(alphabet.length()));
			pass += n;
		}
		return pass;
	}
	
	/**
	 * Constructs a blank ParkNowForm.
	 * By default, password is a randomly generated string of 6 characters.
	 */
	public ParkNowForm()
	{
		plate = "";
		password = generatePassword(6);
		level = "";
		row = "";
		space = "";
		duration = 0;
		arrivalCal = Calendar.getInstance();
		checkOutCal = Calendar.getInstance();
	}
	
	/**
	 * Constructs a blank ParkNowForm, using the given license plate number.
	 * By default, password is a randomly generated string of 6 characters.
	 * 
	 * @param plate the car's license plate number
	 */
	public ParkNowForm(String plate)
	{
		this.plate = plate;
		password = generatePassword(6);
		level = "";
		row = "";
		space = "";
		duration = 0;
		arrivalCal = Calendar.getInstance();
		checkOutCal = Calendar.getInstance();
	}
	
	/**
	 * Constructs a ParkNowForm with all of its information filled.
	 * By default, password is a randomly generated string of 6 characters.
	 * 
	 * @param plate the car's license plate number
	 * @param level the reservation's level
	 * @param row the reservation's row
	 * @param space the reservation's space
	 * @param duration the reservation's duration (in minutes)
	 * @param arrivalCal the reservation's arrival calendar
	 * @param checkOutCal the reservation's checkout calendar
	 */
	public ParkNowForm(String plate, String level, String row, String space, 
			int duration, Calendar arrivalCal, Calendar checkOutCal)
	{
		this.plate = plate.toUpperCase();
		this.level = level.toUpperCase();
		this.row = row;
		this.space = space;
		this.duration = duration;
		this.arrivalCal = arrivalCal;
		this.checkOutCal = checkOutCal;
		setSecondsToZero(arrivalCal);
		setSecondsToZero(checkOutCal);
		password = generatePassword(6);
	}
	
	protected void setSecondsToZero(Calendar c)
	{
		c.set(Calendar.SECOND, 0);
	}
	/**
	 * Sets the reservation's level.
	 * 
	 * @param level the level to set the reservation at
	 */
	public void setLevel(String level)
	{
		this.level = level.toUpperCase();
	}
	
	/**
	 * Gets the reservation's level.
	 * 
	 * @return the reservation's level
	 */
	public String getLevel()
	{
		return level;
	}
	
	/**
	 * Sets the reservation's row.
	 * 
	 * @param row the row to set the reservation at
	 */
	public void setRow(String row)
	{
		this.row = row;
	}
	
	/**
	 * Gets the reservation's row.
	 * 
	 * @return the reservation's row
	 */
	public String getRow()
	{
		return row;
	}
	
	/**
	 * Sets the reservation's space.
	 * 
	 * @param space the space to set the reservation at
	 */
	public void setSpace(String space)
	{
		this.space = space;
	}
	
	/**
	 * Gets the reservation's row.
	 * 
	 * @return the reservation's row
	 */
	public String getSpace()
	{
		return space;
	}
	
	/**
	 * Sets the duration of the reservation (in minutes).
	 * 
	 * @param duration the duration of the reservation (in minutes)
	 */
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	/**
	 * Gets the duration of the reservation (in minutes).
	 * 
	 * @return the duration of the reservation (in minutes)
	 */
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * Gets the duration of the reservation as a string.
	 * 
	 * @return the duration of the reservation as a string
	 */
	public String getDurationString()
	{
		int hours = duration / 60;
		int minutes = duration % 60;
		
		if (minutes < 10)
			return hours + ":0" + minutes+":00";
		else
			return hours + ":" + minutes+":00";
	}
	
	/**
	 * Sets the license plate number associated with the reservation.
	 * 
	 * @param plate the license plate number
	 */
	public void setPlate(String plate)
	{
		this.plate = plate.toUpperCase();
	}
	
	/**
	 * Gets the license plate number associated with the reservation.
	 * 
	 * @return the reservation's associated license plate number
	 */
	public String getPlate()
	{
		return plate;
	}
	
	/**
	 * Gets the reservation's checkout time as a string.
	 * 
	 * @return the reservation's checkout time
	 */
	public String getCheckOutTimeString()
	{
		return timeFormat.format(checkOutCal.getTime());
	}
	
	/**
	 * Gets the reservation's arrival time as a string.
	 * 
	 * @return the reservation's arrival time
	 */
	public String getArrivalTimeString()
	{
		return timeFormat.format(arrivalCal.getTime());
	}
	
	public String getArrivalDateString()
	{
		return dateFormat.format(arrivalCal.getTime());
	}
	
	/**
	 * Gets the reservation's checkout date as a string.
	 * 
	 * @return the reservation's checkout date
	 */
	public String getCheckOutDateString()
	{
		return dateFormat.format(checkOutCal.getTime());
	}
	
	/**
	 * Sets the password for accessing the reservation.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * Gets the password needed for accessing the reservation.
	 * 
	 * @return the reservation's password
	 */
	public String getPassword()
	{
		return password;
	}
}
