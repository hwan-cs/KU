import java.io.Serializable;


public class DateCal implements Serializable
{
	public int dd;
	public int mm;
	public int yy;
	public int score = 0;
	public double price;
	public GolfSession gs = new GolfSession();
	public int getDd() {
		return dd;
	}
	public void setDd(int dd) {
		this.dd = dd;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getYy() {
		return yy;
	}
	public void setYy(int yy) {
		this.yy = yy;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public GolfSession getGs() {
		return gs;
	}
	public void setGs(GolfSession gc) {
		this.gs = gs;
	}
	
	}
