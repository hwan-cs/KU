import java.io.Serializable;
import java.util.ArrayList;

public class Year implements Serializable
{
	public int Year;
	public Month [] month = new Month[31];

	public Month[] getMonth() {
		return month;
	}

	public void setMonth(Month[] month) {
		this.month = month;
	}

	public int getYear() 
	{
		return Year;
	}

	public void setYear(int year) 
	{
		Year = year;
	}
	
}
