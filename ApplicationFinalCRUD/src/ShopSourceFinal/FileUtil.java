package ShopSourceFinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class Provides Us The Files Where we Write The Data And Save The Data
 * Using Some Static Methods
 */
public class FileUtil {

	// Create the static Items File

	private static final String Item_File = "items.txt";

	/**
	 * Here We Write The entire Items List to items.txt File.
	 * 
	 * @param items : Items List.
	 * @throws IOException : Exception Class.
	 */

	public static void saveItems(List<Items> items) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Item_File));) {
			oos.writeObject(items);
		}
	}

	/**
	 * 
	 * @param items : Items List From We Read The items.
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public static List<Items> loadItems() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(Item_File);
		if (!file.exists()) {
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Item_File));) {
			return (List<Items>) ois.readObject();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	// ------------------------------------Crreate The File For
	// Stock-------------------------------------------

	/**
	 * Create stock file.
	 */
	private static final String Stock_File = "stocks.txt";

	/**
	 * in this file we write the stocks.
	 * 
	 * @param stocks - stock list.
	 * @throws IOException - Exception class.
	 */
	public static void saveStock(List<Stock> stocks) throws IOException

	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Stock_File));) {
			oos.writeObject(stocks);
			;
		}
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Stock> loadStock() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(Stock_File);
		if (!file.exists()) {
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Stock_File));) {
			return (List<Stock>) ois.readObject();
		}
	}
	
	// ---------------------------------------------------------------------------------------------------
	// ----------------------------Create file for Cart---------------------------------
 
	private static final String Cart_File = "carts.txt";

	/**
	 * 
	 * @param carts
	 * @throws IOException
	 */
	public static void saveCarts(List<Cart> carts) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Cart_File));) {
			oos.writeObject(carts);
			;
		}
	}

	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public static List<Cart> loadCart() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(Cart_File);
		if (!file.exists()) {
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Cart_File));) {
			return (List<Cart>) ois.readObject();
		}
	}

}
