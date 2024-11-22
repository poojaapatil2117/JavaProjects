package arc.nov.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BooksServlet")
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:mysql://localhost:3306/library_db";
	private static final String USER = "root";
	private static final String PASSWORD = "Root";

	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connection Successfully: (To Cheak My Application Is connected With Database)");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Table to Display The Records
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books");

			out.println("<!doctype html>");
			out.println("<html>");
			out.println("<head><title> Books Records </title>");
			out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>");
			out.println("</head>");
			out.println("<table border='1'>");
			out.println(
					"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td> " + rs.getInt(1) + "</td>");
				out.println("<td> " + rs.getString(2) + "</td>");
				out.println("<td> " + rs.getString(3) + "</td>");
				out.println("<td> " + rs.getString(4) + "</td>");
				out.println("<td> " + rs.getString(5) + "</td>");
				out.println("<td> " + rs.getInt(6) + "</td>");
				out.println("<td> " + rs.getInt(7) + "</td>");
				out.println("<td> " + rs.getString(8) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// For Books Details..
		out.println("<div style='display:none' id='adddetails'>");
		out.println("<h3>Add Books Records : </h3>");
		out.println("<body>");
		out.println("<form action='BooksServlet' method='post'>");
		out.println("Book Name:<input type='text' name='name' placeholder='Enter The Book Name'> <br><br>");
		out.println("ISBN :<input type='text' name='isbn' placeholder='Enter ISBN Number'><br><br>");
		out.println("Author :<input type='text' name='author' placeholder='Enter Author Name'><br><br>");
		out.println("Publisher :<input type='text' name='publisher' placeholder='Enter Publisher Name'><br><br>");
		out.println("Book Count: <input type='text' name='bookcnt' placeholder='Enter The Book Count''><br><br>");
		out.println(
				"Available Count: <input type='text' name='avlcnt' placeholder='Enter The Available Count''><br><br>");
		out.println("Book Price: <input type='text' name='bkprice' placeholder='Enter The Book Price''><br><br>");
		out.println("<input type='hidden' name='action' value='add'>");
		out.println("<input type='submit' value='Add' ><br><br>");

		out.println("</div>");
		// Resgister User And Issue Boook fireld.
		out.println("</form><br><br>");

		out.println("<button onclick='showAddBookDetails()'>Add Book</button><br><br>");
		out.println("<a href='UserServlet'>&nbsp;&nbsp;Register User</a><br><br>");
		out.println("<a href='IssueBook'>&nbsp;&nbsp;Issue here</a><br><br>");
		out.println("<a href='Display_Users'>&nbsp;&nbsp;Display Book Details</a><br><br>");
		out.println("<a href='ShowUserDetails'>&nbsp;&nbsp;Show User Details</a><br><br>");
		
		// Script
		 out.println("<script type='text/javascript'>");
		 out.println("function showAddBookDetails(){");
		 out.println("alert('Want To Add Book Records');");
		 out.println("document.getElementById('adddetails').style.display='block';");
		 out.println("}");
		 out.println("</script>");

		// For The Drop Down For Publisher Table
		 
		 String selectPublication = request.getParameter("publication");
		 String selectPrice = request.getParameter("bookprice");
		 
		 out.println("<form action ='BooksServlet'>");
		 out.println("<br><lable for='publication'>Select Publication:</lable>");
		 out.println("<select name='publication' id='getIdOfBook' onchange='showPublicationDitails()'>");

		 try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

				// Frist DropDowbn For Books Publication
				String query = "SELECT DISTINCT publication from books";
				PreparedStatement stmt1 = conn.prepareStatement(query);
				ResultSet rs1 = stmt1.executeQuery();
			
				//Default option
				out.println("<option value='' " + (selectPublication == null ? "selected" : "") + ">Publications</option>");
				
				while (rs1.next()) {
					String publication = rs1.getString("publication");	
					out.println("<option value='"+publication+"' "+(publication.equals(selectPublication)?"selected":"")+">"+publication+"</option>");
				}
		 }catch(Exception e){
			 e.printStackTrace();
		 }

		out.println("</select>");
		 
		 //Select Price for 
		 out.println("<lable for='bookprice'>Select Book Price:</lable>");
		 out.println("<select name='bookprice' id='bkprice' onchange='showPublicationDitails()'>");

		 // Default "Select" option
		  out.println("<option value='' " + (selectPrice == null ? "selected" : "") + ">Select</option>");
			out.println("<option value='<=100' " + ("<=100".equals(selectPrice) ? "selected" : "") + "><= 100</option>");
			out.println("<option value='<=200' " + ("<=200".equals(selectPrice) ? "selected" : "") + "><= 200</option>");
			out.println("<option value='<=300' " + ("<=300".equals(selectPrice) ? "selected" : "") + "><= 300</option>");
			out.println("<option value='<=500' " + ("<=500".equals(selectPrice) ? "selected" : "") + "><= 500</option>");
			out.println("<option value='>=500' " + (">=500".equals(selectPrice) ? "selected" : "") + ">>= 500</option>");
		
		out.println("</select>");

		out.println("</form> <br><br>");
		
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

			String sql = "SELECT * FROM books WHERE 1=1";
			if (selectPublication != null && !selectPublication.isEmpty()) {
	            sql += " AND publication = ?"; // Add condition for publisher if selected
	        }
			if (selectPrice != null && !selectPrice.isEmpty()) {
		        sql += " AND bookprice " + selectPrice; // Add condition for price range if selected
	        }
			
			PreparedStatement stmt = conn.prepareStatement(sql);
	        int paramIndex = 1;
		 
	        if (selectPublication != null && !selectPublication.isEmpty()) {
	            stmt.setString(paramIndex++, selectPublication);
	        }
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        //Table FOr Display Record
	        out.println("<table border='1'>");
			out.println(
					"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
			while(rs.next())
			{
				out.println("<tr>");
				out.println("<td> " + rs.getInt(1) + "</td>");
				out.println("<td> " + rs.getString(2) + "</td>");
				out.println("<td> " + rs.getString(3) + "</td>");
				out.println("<td> " + rs.getString(4) + "</td>");
				out.println("<td> " + rs.getString(5) + "</td>");
				out.println("<td> " + rs.getInt(6) + "</td>");
				out.println("<td> " + rs.getInt(7) + "</td>");
				out.println("<td> " + rs.getString(8) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
			
			
			//Script For This 
			out.println("<script type='text/javascript'>");
			out.println("function showPublicationDitails(){");
			out.println("alert('in function');");
			//out.println("document.getElementById('tableDisplayOnPublication').style.display='block';");
			out.println("var publication = document.getElementById('getIdOfBook').value;");
			out.println("var bookprice = document.getElementById('bkprice').value;");
			out.println("window.location.href='BooksServlet?publication='+encodeURIComponent(publication)+'&bookprice='+encodeURIComponent(bookprice);");
			out.println("}");
			out.println("</script>");
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
			
		 
		 //my Logic code
//		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
//
//			// Frist DropDowbn For Books Publication
//			String query = "SELECT DISTINCT publication from books";
//			PreparedStatement stmt1 = conn.prepareStatement(query);
//			ResultSet rs1 = stmt1.executeQuery();
			

			// Second DropDown For Book Price.
//			String query1 = "SELECT DISTINCT bookprice from books";
//			PreparedStatement stmt2 = conn.prepareStatement(query1);
//			ResultSet rs2 = stmt2.executeQuery();

			// Book Information Publication
//			out.println("<form action ='BooksServlet'>");
//			out.println("<br><lable for='publication'>Select Publication:</lable>");
//			out.println("<select name='publication' id='getIdOfBook' onchange='showPublicationDitails()'>");

//			while (rs1.next()) {
//				String publication = rs1.getString("publication");
//				out.println("<option value='" + publication + "'>" + publication + "</option>");
//			}

//			out.println("</select>");
//			out.println("<input type='hidden' name='action' id='publicationName' value='publish'>");
//			out.println("</form> <br>");
			// Select Book Price:

//			out.println("<form action ='BooksServlet'>");
//			out.println("<lable for='bookprice'>Select Book Price:</lable>");
//			out.println("<select name='bookprice' id='bookprrice' onchange='this.form.submit()'>");
//
//			while (rs2.next()) {
//				String bookprice = rs2.getString("bookprice");
//				out.println("<option value='" + bookprice + "'>" + bookprice + "</option>");
//			}
//			
//			out.println("</select>");
//			out.println("<input type='hidden' name='action' value='bkprice'>");
//			out.println("</form><br><br>");
			
			//Table to Fectch the Records Depend On Price And Store That Records.	
			
//			out.println("<div style='display:none;' id='tableDisplayOnPublication'>");
//			out.println("<table border='1'>");
//			out.println(
//					"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
//			while(rs1.next())
//			{
//				out.println("<td id='getIdOfBook'>'"+1+"'</td>");
//			}
//			
//			out.println("</table>");
//			out.println("</div>");
			
			//Script For Display Table
//			out.println("<script type='text/javascript'>");
//			out.println("function showPublicationDitails(){");
//			out.println("alert('in function');");
//			out.println("document.getElementById('tableDisplayOnPublication').style.display='block';");
//			out.println("var publication = documeny.getElementById('getIdOfBook').value");
//			out.println("var bookprice = document.getElementById('bookprrice').value");
//			out.println("windows.location.gref='BooksServlet?getIdOfBook='+encodeURIComponent(publication)+'&bookprrice='+encodeURIComponent(bookprice);");
//			out.println("}");
//			out.println("</script>");

			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		out.println("</body>");
//		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		System.out.println("In doPost() : to check if i enter in doPost method when i click on Add");

		String action = request.getParameter("action");
		if (action.equals("publish")) {

			String publication = request.getParameter("publication");
			System.out.println("Publication is :" + publication);

			System.out.println("DropDown Here");
			//reWriteTheTable(out,publication);

			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE publication = ?");
				stmt.setString(1, publication);
				ResultSet rs = stmt.executeQuery();

				out.println("<!doctype html>");
				out.println("<html>");
				out.println("<head><title> Books Records </title></head>");
				out.println("<table border='1'>");
				out.println(
						"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
				while (rs.next()) {

					out.println("<tr>");
					out.println("<td> " + rs.getInt(1) + "</td>");
					out.println("<td> " + rs.getString(2) + "</td>");
					out.println("<td> " + rs.getString(3) + "</td>");
					out.println("<td> " + rs.getString(4) + "</td>");
					out.println("<td> " + rs.getString(5) + "</td>");
					out.println("<td> " + rs.getInt(6) + "</td>");
					out.println("<td> " + rs.getInt(7) + "</td>");
					out.println("<td> " + rs.getString(8) + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("bkprice")) {
			
			String bookprice = request.getParameter("bookprice");
			System.out.println("Book Price Is :" + bookprice);
			System.out.println("In Bk Price Action");
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE bookprice = ?");
				stmt.setString(1, bookprice);
				ResultSet rs = stmt.executeQuery();

				out.println("<!doctype html>");
				out.println("<html>");
				out.println("<head><title> Books Records </title>");
			    out.println("<link rel='stylesheet' href='CSS/login.css'>");
				out.println("</head>");
				out.println("<table border='1'>");
				out.println(
						"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
				while (rs.next()) {

					out.println("<tr>");
					out.println("<td> " + rs.getInt(1) + "</td>");
					out.println("<td> " + rs.getString(2) + "</td>");
					out.println("<td> " + rs.getString(3) + "</td>");
					out.println("<td> " + rs.getString(4) + "</td>");
					out.println("<td> " + rs.getString(5) + "</td>");
					out.println("<td> " + rs.getInt(6) + "</td>");
					out.println("<td> " + rs.getInt(7) + "</td>");
					out.println("<td> " + rs.getString(8) + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("add")) {
			String name = request.getParameter("name");
			String isbn = request.getParameter("isbn");
			String author = request.getParameter("author");
			String publisher = request.getParameter("publisher");
			int cnt = Integer.parseInt(request.getParameter("bookcnt"));
			int avlcount = Integer.parseInt(request.getParameter("avlcnt"));
			String bkprice = request.getParameter("bkprice");

			System.out.println("Book Details " + name + "," + isbn + "," + author + "," + publisher + "," + cnt + ","
					+ avlcount + "," + "TO check The Name And Count of is Taken or not");

			// Insert The Book Records In books table
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				PreparedStatement preparedStatement = null;
				String sqlQuery = "INSERT INTO books (name,isbn,author,publication,quantity,avlquanti,bookprice) values(?,?,?,?,?,?,?)";
				preparedStatement = conn.prepareStatement(sqlQuery);

				preparedStatement.setString(1, name);
				preparedStatement.setString(2, isbn);
				preparedStatement.setString(3, author);
				preparedStatement.setString(4, publisher);
				preparedStatement.setInt(5, cnt);
				preparedStatement.setInt(6, avlcount);
				preparedStatement.setString(7, bkprice);

				preparedStatement.executeUpdate();

				System.out.println("Data Inserted Successfully");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("BooksServlet");
		}
	}

//	private void reWriteTheTable(PrintWriter out, String publication) {
//		
//		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
//
//			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE publication = ?");
//			stmt.setString(1, publication);
//			ResultSet rs = stmt.executeQuery();
//
//			out.println("<!doctype html>");
//			out.println("<html>");
//			out.println("<head><title> Books Records </title></head>");
//			out.println("<table border='1'>");
//			out.println(
//					"<tr> <th>ID</th> <th>Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>BookCount</th> <th>Available Count</th> <th>Book Price</th> </tr>");
//			while (rs.next()) {
//
//				out.println("<tr>");
//				out.println("<td> " + rs.getInt(1) + "</td>");
//				out.println("<td> " + rs.getString(2) + "</td>");
//				out.println("<td> " + rs.getString(3) + "</td>");
//				out.println("<td> " + rs.getString(4) + "</td>");
//				out.println("<td> " + rs.getString(5) + "</td>");
//				out.println("<td> " + rs.getInt(6) + "</td>");
//				out.println("<td> " + rs.getInt(7) + "</td>");
//				out.println("<td> " + rs.getString(8) + "</td>");
//				out.println("</tr>");
//			}
//			out.println("</table>");
//			out.println("</body>");
//			out.println("</html>");
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
