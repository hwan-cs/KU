import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author 19juhpark
 *
 */
public class GolfSession implements Serializable
{
	public ArrayList<Person> group = new ArrayList<Person>();
	public GolfCourse gc = new GolfCourse();
	public Boolean week;
	public double centralGST;
	public double stateGST;
	public int numHoles = 0;

	public ArrayList<Person> getGroup() {
		return group;
	}
	public void setGroup(ArrayList<Person> group) {
		this.group = group;
	}
	public GolfCourse getGc() {
		return gc;
	}
	public void setGc(GolfCourse gc) {
		this.gc = gc;
	}
	public Boolean getWeek() {
		return week;
	}
	public void setWeek(Boolean week) {
		this.week = week;
	}
	public double getCentralGST() {
		return centralGST;
	}
	public void setCentralGST(double centralGST) {
		this.centralGST = centralGST;
	}
	public double getStateGST() {
		return stateGST;
	}
	public void setStateGST(double stateGST) {
		this.stateGST = stateGST;
	}
	public int getNumHoles() {
		return numHoles;
	}
	public void setNumHoles(int numHoles) {
		this.numHoles = numHoles;
	}
	
	
	
}
