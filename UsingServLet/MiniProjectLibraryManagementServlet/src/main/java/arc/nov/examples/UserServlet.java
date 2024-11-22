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

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head><title>User registration</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h3>Register Form Of User</h3>");
		out.println("<form action='UserServlet' method='post'>");
		out.println("Name : <input type='text' name='name' placeholder='Enter The User Name'> <br><br>");
		out.println("Email: <input type='email' name='email' placeholder='Enter The User Email'><br><br>");
		out.println("Contact: <input type='text' name='mobile' placeholder='Enter The User Contact'><br><br>");
		out.println("Password: <input type='text' name='pass' placeholder='Enter The User Password'><br><br>");
		out.println("<input type='submit' value='Register'>");
		out.println("</form><br><br>");	
		
		//Table To Display User Regstration
		out.println("<table border='1'>");
		out.println("<tr> <th>ID</th> <th>Name</th> <th>Email</th> <th>Contact</th> <th>Deposite</th> </tr>");
		try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD))
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");
			
			while(rs.next())
			{
				out.println("<tr>");
				out.println("<td> "+rs.getInt(1)+"</td>");
				out.println("<td>"+rs.getString(2)+"</td>");
				out.println("<td>"+rs.getString(3)+"</td>");
				out.println("<td>"+rs.getString(4)+"</td>");
				out.println("<td>"+rs.getInt(5)+"</td>");
				//out.println("<td>"+rs.getString(6)+"</td>");
				
				//out.println("<td>");
				//out.println("<a href='BookIssueServlet?action=issue&id="+rs.getInt("id")+"'>Issue Book |</a>");
				//out.println("<a href='BookIssueServlet?action=issue&id="+rs.getInt("id")+"'>Return Book</a>");
				//out.println("</td>");
				out.println("</tr>");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</table");
		out.println("</body>");
		out.println("</html>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String contact = request.getParameter("mobile");
		String password = request.getParameter("pass");
		
		
		System.out.println("The Users Data is "+name+email+contact);
		
		
		try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD))
		{
			PreparedStatement prepareStatment = null;
			String sqlQuery = "INSERT INTO users(name,email,contact,password) values(?,?,?,?)";
			prepareStatment = conn.prepareStatement(sqlQuery);
			prepareStatment.setString(1, name);
			prepareStatment.setString(2,email);
			prepareStatment.setString(3,contact);
			prepareStatment.setString(4,password);
			
			prepareStatment.executeUpdate();
			System.out.println("Data Inserted To User Table SuccessFully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("UserServlet");
	}

}
