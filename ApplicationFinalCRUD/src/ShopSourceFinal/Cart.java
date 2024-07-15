package ShopSourceFinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * create List of bill 
	 */
	
	List<Bill> bill = new ArrayList<Bill>();
	
	/**
	 * this method add the bill to cart object
	 * @param bill 
	 */
	
	public void addItems(Bill bill) {
		this.bill.add(bill);
	}

	/**
	 * this method call from mainClass to get Overall bill of carts.
	 * @return
	 */
	public double getTotalBill() {
		double totbill=0.0;
		for(Bill b :bill)
		{
			totbill=totbill+b.GetinDivitotalprice();
		}
		return totbill;
	}

	/**
	 * this method call from ListUtil class in showCart method.
	 * This method used to get  bill of carts with all details
	 */
	
	public void showBill() {
		
		for(int i=0;i<bill.size();i++)
		{
			bill.get(i).showBilledItems(); //call from bill class
		}
	}

	
	/**
	 * This method create for when user is unable to pay the bill and want to remove some item count.
	 * @param code	-	code is passed to method to check which items of count user want to remove.
	 * @param count	- 	how many count user want to remove.
	 */
	public void remove(int code, int count) {
		for(int i=0 ; i<bill.size();i++)
		{
			if(bill.get(i).getCode()==code) {
				bill.get(i).setCount(count);
			}
		}
	}
	
	/**
	 * method called by ListUtilClass in UpdateStock() method to get Bill  list.
	 * @return 	-	return the bill from list of bill.
	 */ 
	public List<Bill> getBilList() {
		return bill;
	}
	
}
