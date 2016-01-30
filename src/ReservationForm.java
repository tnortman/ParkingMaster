import java.util.Calendar;

public class ReservationForm extends ParkNowForm
{
	//private ParkingDate checkInDate;
	//private ParkingTime checkInTime;
	private Calendar checkInCal;
	
	/**
	 * Constructs a blank ReservationForm.
	 * By default, password is a randomly generated string of 6 characters.
	 */
	public ReservationForm()
	{
		super();
		checkInCal = Calendar.getInstance();
	}
	
	/**
	 * Constructs a blank Reservation form with the given license plate number.
	 * By default, password is a randomly generated string of 6 characters.
	 * 
	 * @param plate the car's license plate number
	 */
	public ReservationForm(String plate)
	{
		super(plate);
		checkInCal = Calendar.getInstance();
	}
	
	/**
	 * Constructs a ReservationForm with all of its information filled.
	 * 
	 * @param plate the car's license plate number
	 * @param level the reservation's level
	 * @param row the reservation's row
	 * @param space the reservation's space
	 * @param duration the reservation's duration (in minutes)
	 * @param arrivalCal the reservation's arrival calendar
	 * @param checkOutCal the reservation's checkout calendar
	 * @param checkInCal the reservation's check-in calendar
	 */
	public ReservationForm(String plate, String level, String row, String space, 
			int duration, Calendar arrivalCal, Calendar checkOutCal, Calendar checkInCal)
	{
		super(plate, level, row, space, duration, arrivalCal, checkOutCal);
		this.checkInCal = checkInCal;
		setSecondsToZero(checkInCal);
		
	}
	
	/**
	 * Gets the reservation's check-in time as a string.
	 * 
	 * @return the reservation's check-in time
	 */
	public String getCheckInTimeString()
	{	
		return timeFormat.format(checkInCal.getTime());
	}
	
	/**
	 * Gets the reservation's check-in time as a string.
	 * 
	 * @return the reservation's check-in time
	 */
	public String getCheckInDateString()
	{
		return dateFormat.format(checkInCal.getTime());
	}
	
	/**
	 * Accepts a Calendar object that checkInCal will be set to 
	 * @param checkInCal
	 */
	public void setCheckInCal(Calendar checkInCal)
	{
		this.checkInCal = checkInCal;
	}
	
}
