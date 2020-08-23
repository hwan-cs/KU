import java.io.Serializable;
import java.util.ArrayList;

public class Month implements Serializable
{
	public int Month;
	public DateCal[] date;

	public DateCal[] getDate() {
		return date;
	}

	public void setDate(DateCal[] date) 
	{
		this.date = date;
	}

	public int getMonth() {
		return Month;
	}

	public void setMonth(int month) {
		Month = month;
	}
}
