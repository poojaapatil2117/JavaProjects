package arc.nov.examples;

import java.io.IOException;

import java.sql.Date;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IssueBook")
public class IssueBook extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String URL = "jdbc:mysql://localhost:3306/library_db";
	private static final String USER = "root";
	private static final String PASSWORD = "Root";

	private static final int MAX_ISSUE_LIMIT = 3;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In doGet() of IssueBook when I Click on Issue Link");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");

			out.println("<!doctype html>");
			out.println("<html>");
			out.println("<head><title>Issue Book</title>");
			out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h3>Issue Book Table</h3>");

			// User table
			out.println("<h1>User Table</h1>");
			out.println("<table border='1'>");
			out.println("<tr> <th>User_ID</th> <th>User_Name</th> <th>User_Email</th> <th>User_Contact</th> </tr>");

			while (rs.next()) {
				out.println("<tr>");

				out.println("<td> <a href='javascript:void(0)' onclick=\"userIDTake('" + rs.getInt(1) + "', '"
						+ rs.getString(2) + "')\">" + rs.getInt(1) + "</a></td>");

				System.out.println("Ancher Tag Here");
				out.println("<td> " + rs.getString(2) + "</td>");
				out.println("<td> " + rs.getString(3) + "</td>");
				out.println("<td> " + rs.getString(4) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");

			// Script For User Optration
			out.println("<script type='text/javascript'>");
			out.println("function userIDTake(uid,uname){");
			out.println("alert('in function');");
			out.println("document.getElementById('displayDetails').style.display='block';");
			out.println("document.getElementById('userId').value = uid;");
			out.println("document.getElementById('userName').value = uname;");

			System.out.println("JS Function Tag Here");
			out.println("}");
			out.println("</script>");

			// Book Table
			Statement stmtb = conn.createStatement();
			ResultSet rsb = stmtb.executeQuery("SELECT * FROM books");

			out.println("<h1>Book Table</h1>");
			out.println("<table border='1'>");
			out.println(
					"<tr> <th>Book_ID</th> <th>Book_Name</th> <th>ISBN</th> <th>Author</th> <th>Publisher</th> <th>Book Count</th> <th>Available Count</th> <th>Book Price</th> </tr>");

			while (rsb.next()) {
				out.println("<tr>");
				out.println("<td> <a href='javascript:void(0)' onclick=\"bookIDTake('" + rsb.getInt(1) + "', '"
						+ rsb.getString(2) + "','" + rsb.getString(7) + "')\">" + rsb.getInt(1) + "</a></td>");
				out.println("<td> " + rsb.getString(2) + "</td>");
				out.println("<td> " + rsb.getString(3) + "</td>");
				out.println("<td> " + rsb.getString(4) + "</td>");
				out.println("<td> " + rsb.getString(5) + "</td>");
				out.println("<td> " + rsb.getInt(6) + "</td>");
				out.println("<td> " + rsb.getInt(7) + "</td>");
				out.println("<td> " + rsb.getString(8) + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");

			// Script For Book Optration
			out.println("<script type='text/javascript'>");
			out.println("function bookIDTake(bid,bname,avlcnt){");
			out.println("alert('in function');");
			out.println("document.getElementById('displayDetails').style.display='block';");
			out.println("document.getElementById('BookId').value = bid;");
			out.println("document.getElementById('BookName').value = bname;");
			out.println("document.getElementById('AvailableCount').value = avlcnt;");

			out.println("if(avlcnt>0) {");
			out.println("document.getElementById('result1').innerText = 'Available';");
			out.println("} else {");
			out.println("document.getElementById('result2').innerText='NA';");
			out.println("}");

			System.out.println("JS Function Tag Here");
			out.println("}");
			out.println("</script>");

			out.println("<br>");

			// Input Fields Of Users And Books
			out.println("<div style='display:none;' id='displayDetails'>");

			out.println("<form action='IssueBook' method='post'>");

			// User Fields.
			out.println("<h5>User Fields</h5>");
			out.println("User Id: <input type='text' name='uid' id='userId'> <br><br>");
			out.println("User Name: <input type='text' name='uname' id ='userName'>");

			// Book Fields.
			out.println("<h5>Books Fields</h5>");
			out.println("Book Id: <input type='text' name='bid' id='BookId'> <br><br>");
			out.println("Book Name: <input type='text' name='bname' id ='BookName'><br><br>");
			out.println("Available Count :<input type='text' name='avlcnt' id='AvailableCount'><br>");
			out.println("<h3 id ='result1' style='color:green;' ></h3>");
			out.println("<h3 id ='result2' style='color:red;' ></h3>");// placeholder for available or NA to display.
			out.println("<input type='submit' name ='action' value='Issue'>");
			out.println("<input type='submit' name='action' value='Return'>");
			out.println("<input type='submit' name='action' value='AddDeposit'>");
			out.println("</form>");
			out.println("</div>");

			out.println("</body>");
			out.println("</html>");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		System.out.println("In doPost() of issue book");

		String uid = request.getParameter("uid");
		String uname = request.getParameter("uname");

		int bkid = Integer.parseInt(request.getParameter("bid"));
		String bookname = request.getParameter("bname");

		System.out.println("Uid = " + uid + " Uname =" + uname + " bkid = " + bkid + " bookname = " + bookname);

		LocalDate issuedate = LocalDate.now();
		Date issuedate1 = Date.valueOf(issuedate);

		LocalDate enddate = issuedate.plusDays(8);
		Date enddate1 = Date.valueOf(enddate);

		out.println("<input type='hidden' name='issuedate'  value='" + issuedate + "'><br><br>");
		out.println("<input type='hidden' name='returndate' value='" + enddate + "'><br><br>");

		String takeavlcnt = "SELECT avlquanti from books WHERE id=?";
		String updateavlcnt = "UPDATE books SET avlquanti = ? WHERE id=?";

		// For Issue Or Return

		// For Return Book And User Book Limitation
		String action = request.getParameter("action");

		if (action.equals("Issue")) {
			// --------------------------------------------------------
			// To Reduce Count If User Issue Book

			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				PreparedStatement p = conn.prepareStatement(takeavlcnt);
				p.setInt(1, bkid);
				ResultSet r = p.executeQuery();

				if (r.next()) {
					int temp = r.getInt("avlquanti");
					int newQuantity = temp - 1;

					PreparedStatement updateStatement = conn.prepareStatement(updateavlcnt);
					updateStatement.setInt(1, newQuantity);
					updateStatement.setInt(2, bkid);
					updateStatement.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ----------------------------------------------------------------------

			// Inset Records In Table
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

				PreparedStatement prepareStatment = null;
				if (canIssueBook(conn, uid)) {

					// Deposite Cheack Query

					String depositeCheckQuery = "SELECT deposit FROM users WHERE id =?";
					PreparedStatement depositecheckstmt = conn.prepareStatement(depositeCheckQuery);
					depositecheckstmt.setString(1, uid);
					ResultSet rsCheckDepo = depositecheckstmt.executeQuery();

					// Update Deposite

					if (rsCheckDepo.next()) {
						int deposit = rsCheckDepo.getInt("deposit");

						if (deposit == 0) {
							out.println("<h2>Your deposit is zero. Please add a deposit of 100 Rs.</h2>");
							return; // Don't proceed with issuing the book
						} else if (deposit >= 10) {
							String selectDepositeQuery = "UPDATE users SET deposit = deposit-10 WHERE id=?";
							PreparedStatement deductdeposite = conn.prepareStatement(selectDepositeQuery);
							deductdeposite.setString(1, uid);
							deductdeposite.executeUpdate();

							// Insert The Details In table
							String sqlQuery = "INSERT INTO issuebook(u_id,u_name,bk_id,b_name,issuedate,submitdate) values(?,?,?,?,?,?)";
							prepareStatment = conn.prepareStatement(sqlQuery);
							prepareStatment.setString(1, uid);
							prepareStatment.setString(2, uname);
							prepareStatment.setInt(3, bkid);
							prepareStatment.setString(4, bookname);
							prepareStatment.setDate(5, issuedate1);
							prepareStatment.setDate(6, enddate1);
							prepareStatment.executeUpdate();
							out.println("<h1 style='color:green;'>Book Issued Successfully...</h1>");
						}
						else {
							out.println("<h1 style='color:green;'>Your Deposite Is Less Than 10 Please add 100Rs</h1>");
						}
					} 
					
//-------------------------------------------------------------------------------------------
					// Display The Table
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM issuebook");

					out.println("<!doctype html>");
					out.println("<html>");
					out.println("<head><title>Issue Record Of Users</title>;");
					out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>");
					out.println("</head>");
					out.println("<body>");
					out.println("<table border='1'>");
					out.println(
							"<tr> <th>ID</th> <th>User_Id</th> <th>User_Name</th> <th>Book_Id</th> <th>Book Name</th> <th>Issued Date</th> <th>Return Date</th> <th>Return Status</th></tr>");
					while (rs.next()) {
						out.println("<tr>");
						out.println("<td> " + rs.getInt(1) + "</td>");
						out.println("<td> " + rs.getString(2) + "</td>");
						out.println("<td> " + rs.getString(3) + "</td>");
						out.println("<td> " + rs.getString(4) + "</td>");

						out.println("<td> " + rs.getString(5) + "</td>");
						out.println("<td> " + rs.getDate(6) + "</td>");
						out.println("<td> " + rs.getDate(7) + "</td>");

						out.println("<td>" + rs.getString(8) + "</td>");

						out.println("</tr>");

					}

					out.println("</table>");

					System.out.println("Data Inserted To User Table SuccessFully");
				} else {
					out.println("<h1 style='color:red;' >You Can't Issue Book You Reach The Limit </h1>");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (action.equals("Return")) {

			// If User Return The Book we Have To Add The AvailableCount Of This Book
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				PreparedStatement p = conn.prepareStatement(takeavlcnt);
				p.setInt(1, bkid);
				ResultSet r = p.executeQuery();

				if (r.next()) {
					int temp = r.getInt("avlquanti");
					int newQuantity = temp + 1;

					PreparedStatement updateStatement = conn.prepareStatement(updateavlcnt);
					updateStatement.setInt(1, newQuantity);
					updateStatement.setInt(2, bkid);
					updateStatement.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// --------------------------------------------------------

			// To Update return status.
			String updatstatus = "UPDATE issuebook SET status = 'return' WHERE  u_id = ? AND bk_id = ? AND status = 'Issued'";
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				PreparedStatement p = conn.prepareStatement(updatstatus);
				p.setString(1, uid);
				p.setInt(2, bkid);
				p.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ---------------------------------------------------------------

			// Display IssueBook Table
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM issuebook");

				out.println("<!doctype html>");
				out.println("<html>");
				out.println("<head><title>Issue Record Of Users</title>");
				out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>");
				out.println("</head>");
				out.println("<body>");
				out.println("<table border='1'>");
				out.println(
						"<tr> <th>ID</th> <th>User_Id</th> <th>User_Name</th> <th>Book_Id</th> <th>Book Name</th> <th>Issued Date</th> <th>Return Date</th> <th>Return Status</th></tr>");
				while (rs.next()) {

					out.println("<tr>");
					out.println("<td> " + rs.getInt(1) + "</td>");
					out.println("<td> " + rs.getString(2) + "</td>");
					out.println("<td> " + rs.getString(3) + "</td>");
					out.println("<td> " + rs.getString(4) + "</td>");

					out.println("<td> " + rs.getString(5) + "</td>");
					out.println("<td> " + rs.getDate(6) + "</td>");
					out.println("<td> " + rs.getDate(7) + "</td>");

					out.println("<td>" + rs.getString(8) + "</td>");

					out.println("</tr>");

				}
				out.println("</table>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("AddDeposit")) {
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				String addDepositQuery = "UPDATE users SET deposit = 100 WHERE id = ?";

				PreparedStatement stmt = conn.prepareStatement(addDepositQuery);
				stmt.setString(1, uid);
				stmt.executeUpdate();
				out.println("<h1>Your deposit has been successfully updated to 100 Rs. You can now issue books.</h1>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean canIssueBook(Connection conn, String uid) {

		String query = "SELECT COUNT(*) AS issued_count FROM issuebook WHERE u_id = ? AND status = 'Issued'";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, uid);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int issuedCount = rs.getInt("issued_count");
				return issuedCount < MAX_ISSUE_LIMIT;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
