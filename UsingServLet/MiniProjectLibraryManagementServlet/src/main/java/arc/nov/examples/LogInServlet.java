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

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
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
	    out.println("<link rel='stylesheet' href='CSS/login.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>Log In Here</h2>");
		//User LogIn 
		out.println("<form action='LogInServlet' method='post'>");
		out.println("Enter User ID : <input type='text' name='userId' id='userId' placeholder='Enter The User ID'> <br><br>");
		out.println("Enter PassWord: <input type='password' id='password' name='password' placeholder='Enter The User Password''><br><br>");
		out.println("<input type='submit' value='Log in'><br><br>");
		out.println("</form>");
		
		//User Registration
		out.println("<a href='UserServlet'>Register Here</a>");
		
		out.println("</body");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // Get user inputs
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        
        // Set response type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(userId==null || password==null || password.isEmpty() || userId.isEmpty())
        {
        	  out.println("<h3>Error: User ID or Password is missing.</h3>");
              return;
        }
        
        try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD))
        {
            String query = "SELECT * FROM users WHERE id = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
            	out.println("LoginSuccessfully");
            	response.sendRedirect("BooksServlet");
            }
            else
            {
            	out.println("Invalied UserId Or Password");
            }
            
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
