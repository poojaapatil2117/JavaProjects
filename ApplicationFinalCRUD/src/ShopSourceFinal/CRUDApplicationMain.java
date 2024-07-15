package ShopSourceFinal;

import java.io.IOException;

import java.util.List;
import java.util.Scanner;

/**
 * CRUDApplicationMain is main class where it will provide us menu to perform some operations.
 * 
 */
public class CRUDApplicationMain {
	
	/**
	 * Shopping Application entry point.
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		try {
			
			List<Items> items = FileUtil.loadItems();
			List<Stock> stocks=FileUtil.loadStock();
			List<Cart> carts=FileUtil.loadCart();
			
			Scanner scanner = new Scanner(System.in);

			boolean running = true;
			
			/**
			 * Menu for operations.
			 */
			while (running) {
				System.out.println("Choose an operation: create, read, update, delete, addstock, readStock, allcarts, sell, exit");
				String operation = scanner.nextLine();

				switch (operation.toLowerCase()) {
				case "create":
					createItem(scanner, items);
					break;
				case "read":
					readItems(items);
					break;
				case "update":
					updateItem(scanner, items);
					break;
				case "delete":
					deleteItem(scanner, items);
					break;
				case "addstock":
					addStock(scanner, items,stocks); 
					break;
				case "readstock":
					readStock(items,stocks); 
					break;
				case "sell":
					sellItem(scanner, items,stocks, carts);
					break;
				case "allcarts":
					double total = ListUtil.showAllCarts(carts);
					System.out.println("\n ==================== Shop Sell: "+total+"=======================");
					break;
				case "exit":
					running = false;
					break;
				default:
					System.out.println("Invalid operation");
				}
				
				FileUtil.saveItems(items);
				FileUtil.saveStock(stocks);
				FileUtil.saveCarts(carts);
			}
		} 
		catch (IOException | ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param scanner
	 * @param items
	 * @param stocks
	 * @param carts
	 */
	private static void sellItem(Scanner scanner, List<Items> items,List<Stock> stocks,List<Cart> carts) {
		Cart cart = new Cart();
		String choice;
		int code, count; 
		do {
			System.out.println("Enter item code:");
			code = Integer.parseInt(scanner.nextLine());
			System.out.println("Enter item count:");
			count = Integer.parseInt(scanner.nextLine());
			if(ListUtil.isInStock(code, count, stocks)) 	//call method isInStock from ListUtil.
				/*
				 We add the 'new Bill'to cart object using calling bill constructor where we calculate Individual Total.
				 */
				cart.addItems(new Bill(ListUtil.getItemBycode(items, code),count)); //call addItems method from Cart class.
			else 
				System.out.println("Item Not in Stock");
				System.out.println("Press yes to continue and no to stop:");
				choice = scanner.nextLine();
		} while (choice.equals("yes"));
		
		do
		{
			System.out.println("The Current cart Bill: Rs."+cart.getTotalBill()); 
			System.out.println("Press yes to pay and remove to remove items from cart:");
			choice = scanner.nextLine();
			if(choice.equals("remove"))
			{
				ListUtil.showCart(cart);
				System.out.println("Enter item code:");
				code = Integer.parseInt(scanner.nextLine());
				System.out.println("how many items:");
				count = Integer.parseInt(scanner.nextLine());
				cart.remove(code,count);   //call from cart class
			}
		}while(!choice.equals("yes"));
		carts.add(cart);
		ListUtil.UpdateStock(cart, stocks); // decrease stock 
	}

	/**
	 * read the items from stock
	 * @param items - 	pass list of items to take the name and price of code to display .
	 */
	
	private static void readStock(List<Items> items,List<Stock> stocks) {
		if(stocks.isEmpty())
		{
			System.out.println("Stock is Empty");
		}
		else
		{
			for(Stock i:stocks)
			{
				i.printStock(items); 	// call the method printStock() defined in Stock class.
			}
		}
		
	}

	/**
	 * Method create for update the stock
	 * @param scanner-	Default input Stream Passed Through Main CRUDApplication class to take input.
	 * @param items  -	items list is used to pass as parameter to containCode method from ListUtil for match code between 
	 * 					items list code and entered code.
	 * @param stocks -	stock is used to pass as parameter to addOrUpdateStock if stock 
	 * 					is empty it will add and if Stock is not empty is will update.
	 */
	private static void addStock(Scanner scanner, List<Items> items,List<Stock> stocks) {
		System.out.println("Enter The Code");
		int code = Integer.parseInt(scanner.nextLine());
		if(!ListUtil.containCode(code,items))  //Method in ListUtil
		{
			System.out.println("Code is Not Found");
			return;
		}
		
		System.out.println("Enter the Count");
		int count=Integer.parseInt(scanner.nextLine());
		ListUtil.addOrUpdateStock(code,count,stocks);  //method in ListUtil.
		System.out.println("Stock added Successfully");
	}

	/**
	 * delete the item from items list.
	 * @param scanner-Default input Stream Passed Through Main CRUDApplication class to take input.
	 * @param items-List of items
	 */
	private static void deleteItem(Scanner scanner, List<Items> items) {
		System.out.println("Enter item id to delete:");
		int id = Integer.parseInt(scanner.nextLine());
		items.removeIf(item -> item.getCode() == id);
		System.out.println("Item deleted.");
	}

	/**
	 * Update the items field if code match.
	 * @param scanner - Default input Stream Passed Through Main CRUDApplication class to take input.
	 * @param items - List of items.
	 */
	private static void updateItem(Scanner scanner, List<Items> items) {
		System.out.println("Enter item id to update:");
		int id = Integer.parseInt(scanner.nextLine());
		for (Items item : items) {
			if (item.getCode() == id) {
				System.out.println("Enter new item name:");
				String newName = scanner.nextLine();
				System.out.println("Enter new item price:");
				double newprice = Double.parseDouble(scanner.nextLine());
				item.setName(newName);
				item.setPrice(newprice);
				System.out.println("Item updated.");
				return;
			}
		}
		System.out.println("Item not found.");
	}
 

	/** 
	 * Read the items from file 'items.txt'.
	 * @param items - List of items.
	 */
	private static void readItems(List<Items> items) {
		if (items.isEmpty()) {
			System.out.println("No items found.");
		} 
		else 
		{
			for (Items item : items)
			{ 
				System.out.println(item); 
			}
		}	
	}
	
	/**
	 *  This method used to Create the new item.
	 * @param scanner - Default input Stream Passed Through Main CRUDApplication class to take input.
	 * @param items - The newly created item is added to 'items' ArrayList.
	 * 				 which will be written in items.txt at last.
	 */
	
	private static void createItem(Scanner scanner, List<Items> items) {

		System.out.println("Enter item id:");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.println("Enter item name:");
		String name = scanner.nextLine();

		System.out.println("Enter item price:");
		double price = Double.parseDouble(scanner.nextLine());

		items.add(new Items(id, name, price));
		System.out.println("Item created.");
	}
}

