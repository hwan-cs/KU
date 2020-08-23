import java.io.Serializable;

/**
 * 
 */

/**
 * @author 19juhpark
 *
 */
public class Person implements Serializable
{
	public Boolean Caddie;
	public Boolean golfCart;
	public Boolean member;
	public String name;
	public int price = 0;
	
	
	public double getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getCaddie() {
		return Caddie;
	}
	public void setCaddie(Boolean caddie) {
		Caddie = caddie;
	}
	public Boolean getGolfCart() {
		return golfCart;
	}
	public void setGolfCart(Boolean golfCart) {
		this.golfCart = golfCart;
	}
	public Boolean getMember() {
		return member;
	}
	public void setMember(Boolean member) {
		this.member = member;
	}
}
