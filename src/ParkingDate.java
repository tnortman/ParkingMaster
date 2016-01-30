
public class ParkingDate 
{
	private int day;
	private int month;
	private int year;
	
	public ParkingDate()
	{
		day = 0;
		month = 0;
		year = 0;
	}
	
	public ParkingDate(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public void setDay(int day)
	{
		this.day = day;
		if(day>31)
		{
			day =1;
			month++;
			if(month>12)
			{
				month = 1;
			}
		}
	}
	
	public void setMonth(int month)
	{
		this.month = month;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String toString()
	{
		String tempDay = Integer.toString(day);
		String tempMonth = Integer.toString(month);
		String tempYear = Integer.toString(year);
		String dateString = tempMonth+"/"+tempDay+"/"+tempYear;
		return dateString;
	}
	
}
