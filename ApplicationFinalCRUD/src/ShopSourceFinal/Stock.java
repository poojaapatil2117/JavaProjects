package ShopSourceFinal;

import java.io.Serializable;
import java.util.List;

/**
 * This class is Used to create an Object to Store The Stock.
 */

public class Stock  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int code;
	private int countinstock;
	
	public Stock(int code2, int count) {
		this.code=code2;
		this.countinstock=count;
	}

	/**
	 * Getters and Setters For Stock fiels.
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	public int getCountinstock() {
		return countinstock;
	}
	
	public void setCountinstock(int countinstock) {
		this.countinstock = countinstock;
	}

	/**
	 * call from ListUtil class in addOrUpdateStock method 
	 * @param count -	pass entered count from addOrUpdateStock to 
	 * 					add Stock in current stock or create a new Stock.
	 */
	public void updateStock(int count) {
		countinstock=countinstock+count;
	}
	
	/**
	 * call from Main Class in readStock method to Display or Show The Stocks.
	 * @param items -	list of item is pass as parameter from readStock to get the name and price because 
	 * 					Stock does not have name and price.it contain only code and count.
	 */ 
	public void printStock(List<Items> items)
	{
		Items item = ListUtil.getItemBycode(items,code);
		String str = "StockItems { code :"+code+"\tname :"+item.getName()+"\tPrice :"+item.getPrice()+"\tcount :"+countinstock+" }";
		System.out.println(str);
	}
}
