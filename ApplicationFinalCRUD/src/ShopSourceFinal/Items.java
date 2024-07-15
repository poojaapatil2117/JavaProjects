package ShopSourceFinal;

import java.io.Serializable;

/**
 * class That provide Or represent main entity of Items from application
 */
public class Items  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String name;
	private double price;
	
	//constructer to intialize fiels.
	
	public Items(int code,String name,double price)
	{
		this.code=code;
		this.name=name;
		this.price=price;
	}
	 
	/**
	 * Setters and Getters.
	 * @return
	 */
	public int getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String toString()
	{
		return "ItemsList { code :"+code+"\tname :"+name+"\tprice :"+price+" } ";
	}
}
