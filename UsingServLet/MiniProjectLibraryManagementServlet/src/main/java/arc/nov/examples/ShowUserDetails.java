package arc.nov.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowUserDetails")
public class ShowUserDetails extends HttpServlet {
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

		out.println("<!Doctype html>");
		out.println("<html>");
		out.println("<head><title>Display User Details</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>"); // Relative to the context root
		out.println("</head>");
		
		out.println("<body>");
		out.println("<h1>User Details</h1>");

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

			// Table Of Users Displayed
			String selectQuery = "SELECT * FROM users";
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			out.println("<table border='1'>");
			out.println("<tr> <th>ID</th> <th>Name</th> <th>Email</th> <th>Contact</th> <th>Deposite</th> </tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt(1) + "</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("<td>" + rs.getString(3) + "</td>");
				out.println("<td>" + rs.getString(4) + "</td>");
				out.println("<td>" + rs.getInt(5) + "</td>");
				out.println("</tr>");
			}
			out.println("<table><br>");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Record
		out.println("<form>");
		out.println("Enter ID: <input type='text' name='id' style='width:20px; height:20px' id='UserId' value='"
				+ (request.getParameter("id") == null ? "" : request.getParameter("id")) + "'>");
		out.println("</form>");

		// For Last User
		String lastUser = "SELECT * FROM users order by id DESC LIMIT 1";
		int lastrow = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			PreparedStatement stmt = conn.prepareStatement(lastUser);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				lastrow = rs.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Buttons For Oprations.
		out.println("<br><br><button onclick='firstUser()' > << </button>");
		out.println("<button onclick='decreaseByOne()' > < </button>");
		out.println("<button onclick='increaseByOne()' > > </button>");
		out.println("<button onclick='lastUser()' > >> </button>");

		// JavaScipt For This FirstUser
		out.println("<script type='text/javascript'>");
		out.println("function firstUser(){");
		out.println("alert('In Fucntion');");
		out.println("var id = parseInt(document.getElementById('UserId').value);");
		out.println("id=1;");
		out.println("window.location.href='ShowUserDetails?id=' + encodeURIComponent(id);");
		out.println("}");
		out.println("</script>");

			// JavaScript For DecreaseByOne
			out.println("<script type='text/javascript'>");
			out.println("function decreaseByOne(){");
			out.println("alert('In Fucntion');");
			out.println("var id = parseInt(document.getElementById('UserId').value);");
			out.println("id=id-1;");
			out.println("window.location.href='ShowUserDetails?id=' + encodeURIComponent(id);");
			out.println("}");
			out.println("</script>");

		// JavaScript For Increase By one
		out.println("<script type='text/javascript'>");
		out.println("function increaseByOne(){");
		out.println("alert('In Fucntion');");
		out.println("var id = parseInt(document.getElementById('UserId').value);");
		out.println("id=id+1;");
		out.println("window.location.href='ShowUserDetails?id=' + encodeURIComponent(id);");
		out.println("}");
		out.println("</script>");

			// JavaScript For Last User
			out.println("<script type='text/javascript'>");
			out.println("function lastUser(){");
			out.println("alert('In Fucntion');");
			out.println("var id = '" + lastrow + "';");
			out.println("window.location.href='ShowUserDetails?id=' + encodeURIComponent(id);");
			out.println("}");
			out.println("</script>");

		// To display The Records
		int id = Integer.parseInt(request.getParameter("id"));
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			System.out.println("Id Is " + id);

			String query = "SELECT * FROM users WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				out.println("<p>UserID : '" + rs.getInt(1) + "'</p>");
				out.println("<p>UserName :'" + rs.getString(2) + "'</p>");
				out.println("<p>UserEmail : '" + rs.getString(3) + "'</p>");
				out.println("<p>UserContact :'" + rs.getString(4) + "'</p>");
				out.println("<p>UserDeposit : '" + rs.getInt(5) + "'</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
