package ShopSourceFinal;

import java.io.Serializable;

public class Bill implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private int count;
	private double inDivitotalprice;
	Items item;

	public Bill(Items items, int count) {
		this.code = items.getCode(); 
		this.item = items;
		this.count = count;
		this.inDivitotalprice = items.getPrice() * count; 
	}

	/**
	 * Create Getters and Setters for Bill fields.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	public int getCount() {
		return count;
	}
	
	public double GetinDivitotalprice() {
		return inDivitotalprice; 
	}

	public void setTotorice(double totorice) {
		this.inDivitotalprice = totorice;
	} 

	/**
	 * This method call by Cart class in showBill method to show all the details with totalprice of cart.
	 */
	public void showBilledItems() {
		this.inDivitotalprice = item.getPrice() * this.count;
		System.out.println("code :" + code + "\t Name: " + item.getName() + "\t Price: " + item.getPrice()
				+ "\t count: " + count + "\t Total Items Price: " + inDivitotalprice);
	}
	
	public void setCount(int count) {
		this.count -= count;
		this.inDivitotalprice=item.getPrice()*this.count;
	}

}
