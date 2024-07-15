package ShopSourceFinal;

import java.util.List;

public class ListUtil {

	/**
	 * call from mainClass in addStock method to check entered code and items list
	 * code. if code is same it will addOrUpdateStock otherwise it will show the
	 * message code is not present.
	 * 
	 * @param code  - pass entered code from addStock method to check .
	 * @param items - pass Item List from addStock method to check Entered code with
	 *              code present in item list.
	 * @return - the type of containCode method is boolean so it will return true if
	 *         code match otherwise false.
	 */
	public static boolean containCode(int code, List<Items> items) {
		for (Items i : items) {
			if (code == i.getCode()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * method call from mainClass in addStock method to addOrUpdateStock.
	 * 
	 * @param code   - pass entered code from addStock method to check.
	 * @param count  - pass the count from addStock method to update or add stock.
	 * @param stocks - stocks list is passed as parameter from addStock method to
	 *               add or update stock in 'stocks.txt' file
	 */
	public static void addOrUpdateStock(int code, int count, List<Stock> stocks) {

		// if Stock is empty.
		if (stocks.isEmpty()) {
			stocks.add(new Stock(code, count));
			return;
		}

		// if stock having entry, the add/increase stock of item having same code
		boolean flg = false;
		for (Stock i : stocks) {
			if (code == i.getCode()) {
				i.updateStock(count);
				flg = true;
			}
		}

		// if stock don't have entry, adding the stock of code <code> first time
		if (!flg) {
			stocks.add(new Stock(code, count));
		}
	}

	
	/**
	 * This method called From Stock Class in printStock method to get the item.
	 * 
	 * @param code  - pass code of stock from Stock printStock to getItemBycode
	 *              method to cheak the code of stock and items code if code is
	 *              match we get the deatils of that code.
	 * @param items - list of items.
	 * @return - it will return the object whose code is match otherwise it will
	 *         return null.
	 */

	public static Items getItemBycode(List<Items> items, int code) {

		for (Items i : items) {
			if (code == i.getCode()) {
				return i;
			}
		}
		return null;
	}

	/**
	 * This method Create for check entered code is present in stock or not for
	 * sell.
	 * 
	 * @param code    - pass entered code from MainClass sellItems method to
	 *                ListUtil Class isInStock Method to check code is present or
	 *                not in stock
	 * @param count   - pass entered count from MainClass sellItems method to
	 *                ListUtil Class isInStock Method for cheack the stock have that
	 *                much count if code are matched
	 * @param stocks- list of stocks.using this list we check the code in stocks
	 * @return - if code match it will return true otherwise it will return false.
	 */
	
	public static boolean isInStock(int code, int count, List<Stock> stocks) {

		for (Stock i : stocks) {
			if (code == i.getCode() && count <= i.getCountinstock()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * this method call from mainclass in sell method to show all list of Carts.
	 * 
	 * @param cart - cart is pass by mainclass sell method to display the list of
	 *             cart when user want to remove items when they are unable to pay
	 *             bill.
	 */

	public static void showCart(Cart cart) {
		System.out.println("---------------------------------------------------");
		cart.showBill(); // call from cart class
	}

	/**
	 * 
	 * @param cart 		- here cart is passed by  mainclass sell method to UpdateStock to get getBilList() from Cart class
	 * @param stocks	- stock passed by mainclass sell method to UpdateStock to decrease the stock using decreaseStock() 
	 * 						method
	 */
	public static void UpdateStock(Cart cart, List<Stock> stocks) {
		List<Bill> bill = cart.getBilList();
		for (Bill b : bill) {
			decreaseStock(b.getCode(), b.getCount(), stocks);
		}

	}

	/**
	 * this method used to decrease the stock 
	 * @param code		-	code passed by UpdateStock to which code of item count want to decrease.
	 * @param count		- 	how many items count want to decrease.
	 * @param stocks	- 	Update stock.
	 */
	private static void decreaseStock(int code, int count, List<Stock> stocks) {
		for (Stock s : stocks) {
			if(s.getCode() == code) {
				s.updateStock(-count);
			}
		}
	}
	
	/**
	 * 
	 * @param carts	- pass the carts list to to display all carts and show all total of carts.
	 * @return
	 */
	public static double showAllCarts(List<Cart> carts) {
		double shopsell = 0.0;
		for (int i = 0; i < carts.size(); i++) {
			System.out.print("\n------- Cart:" + (i + 1) + " --------\n");
			carts.get(i).showBill();
			System.out.print("\n------- Cart Total: " +carts.get(i).getTotalBill());
			shopsell+=carts.get(i).getTotalBill();
		}
		return shopsell;
	}

}
