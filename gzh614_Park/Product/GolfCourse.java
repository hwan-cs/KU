import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author 19juhpark
 *
 */
public class GolfCourse implements Serializable
{
	public String golfCourse = "";
	double pGolfCartNH;
	double pWeekNH;
	double pCaddie;
	double pGolfCartEH;
	double pNH;
	double pEH;
	
	public double getpNH() {
		return pNH;
	}
	public void setpNH(double pNH) {
		this.pNH = pNH;
	}
	public double getpEH() {
		return pEH;
	}
	public void setpEH(double pEH) {
		this.pEH = pEH;
	}
	double pWeekEh;
	public ArrayList<DateCal> promotionDates;
	
	public String getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(String golfCourse) {
		this.golfCourse = golfCourse;
	}
	public double getpGolfCartNH() {
		return pGolfCartNH;
	}
	public void setpGolfCartNH(double pGolfCartNH) {
		this.pGolfCartNH = pGolfCartNH;
	}
	public double getpWeekNH() {
		return pWeekNH;
	}
	public void setpWeekNH(double pWeekNH) {
		this.pWeekNH = pWeekNH;
	}
	public double getpCaddie() {
		return pCaddie;
	}
	public void setpCaddie(double pCaddie) {
		this.pCaddie = pCaddie;
	}
	public double getpGolfCartEH() {
		return pGolfCartEH;
	}
	public void setpGolfCartEH(double pGolfCartEH) {
		this.pGolfCartEH = pGolfCartEH;
	}
	public double getpWeekEh() {
		return pWeekEh;
	}
	public void setpWeekEh(double pWeekEh) {
		this.pWeekEh = pWeekEh;
	}
	
	
}
