package arc.nov.examples;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/Display_Users")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Display_Users extends HttpServlet {

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

//		String action = request.getParameter("action");
//
//		if ("image".equals(action)) {
//			serveImage(request, response);
//		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>File Upload Form</title>");
		out.println("<link rel='stylesheet' type='text/css' href='CSS/Style.css'>"); // Relative to the context root
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>Enter Books Details</h2>");

		out.println("<form action='Display_Users' method='post' enctype='multipart/form-data'>");
		out.println("<label for='image'>Select Image:</label>");
		out.println("<input type='file' name='image' accept='image/*' required><br><br>");
		out.println("Book Name:<input type='text' name='name' placeholder='Enter The Book Name'> <br><br>");
		out.println("<input type='submit' value='Add' ><br><br>");
		out.println("<input type='hidden' name='action' value='addbook'>");
		out.println("</form>");

		// Table FOr Display Record
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			String displayQuery = "select * from bookswithimages";
			PreparedStatement stmt = conn.prepareStatement(displayQuery);
			ResultSet rs = stmt.executeQuery();
			out.println("<table border='1'>");
			out.println("<tr> <th>ID</th> <th>Name</th> <th>BookImage</th> </tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td> " + rs.getInt(1) + "</td>");
				out.println("<td> " + rs.getString(2) + "</td>");
			    out.println("<td> <img src='"+rs.getString(3)+"'>"+rs.getString(3)+"</td>");
			    
               // out.println("<td><img src='ImageServlet?id=" + rs.getInt(1) + "' width='100' height='100'/></td>");
             
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println("<br><br>");
		// ----------------------------------------------

		// Form To Fetch Column according to check Box

		String bookId = request.getParameter("id");
		String bookName = request.getParameter("bkname");

		out.println("<form action='Display_Users'>");
		out.println("<label for='bookid'>id:</label>");
		out.println("<input type='checkbox' name='id' id='bookid'value='id'>");

		out.println("<label for='bookName'>BookName:</label>");
		out.println("<input type='checkbox' name='bkname' id='bookname'value='bookname'>");

//		out.println("<label for='bookPrice'>price</label>");
//		out.println("<input type='checkbox' name='bkprice' id='bookprice'value='bprice'>");

		out.println("<label for='bookImage'>Image</label>");
		out.println("<input type='checkbox' name='bkimg' id='bookimg'value='bimage'>");

//		out.println("<label for='bookDeposite'>Deposite</label>");
//		out.println("<input type='checkbox' name='bkdeposit' id='bookdeposite'value='bdeposite'>");

//		out.println("<label for='bookDescription'>Description</label>");
//		out.println("<input type='checkbox' name='bkdescribe' id='bookdescrip'value='bdescription'>");

//		out.println("<input type='hidden' name='action' value='selectcheckbox'>");

		out.println("<input type='submit' value='Submit'>");
		out.println("</form><br>");

		// ---------------------------------------------------------------------------
		// To display The Records According to Checkbox..

		if (bookId != null || bookName != null) {
			try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
				StringBuilder sql = new StringBuilder("SELECT id");
				if (bookName != null) {
					sql.append(",name");
				}
				sql.append(" FROM bookswithimages");

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());

				out.println("<h3>Book Details</h3>");
				out.println("<table border='1'>");
				out.println("<tr>");

				if (bookId != null) {
					out.println("<th>Book ID</th>");
				}
				if (bookName != null) {
					out.println("<th>Book Name</th>");
				}
				out.println("</tr>");

				// Add Row To the Table....
				while (rs.next()) {
					out.println("<tr>");
					if (bookId != null) {
						out.println("<td>" + rs.getInt(1) + "</td>");
					}
					if (bookName != null) {
						out.println("<td> " + rs.getString(2) + "</td>");
					}
					out.println("</tr>");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			out.println("<p style='color:red;'>Please Select At Least One Colunm to Display..</p>");
		}

		// --------------------------------------------------------------------
		out.println("</body>");
		out.println("</html>");
	}

	// --------------------------------------------------------------------------------

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get The Data
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
//		String action = request.getParameter("action");

		// Add Books in Table
//		if(action.equals("addbook"))
//		{

		String name = request.getParameter("name");
		Part filePart = request.getPart("image");

		String fileName = filePart.getSubmittedFileName();

		String uploadPath = "C:/Users/USER/eclipse-workspace/MiniProjectLibraryManagementServlet/src/main/webapp/BookImages"; 
		
		System.out.println("Uploaded Images Path :"+uploadPath);
		String img_url=uploadPath + "\\" + fileName;
		System.out.println("Image Url :"+img_url);

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			String insertQuery = "INSERT INTO bookswithimages(name,img)values(?,?)";
			PreparedStatement stmt = conn.prepareStatement(insertQuery);

			stmt.setString(1, name);
			stmt.setString(2, img_url);

			stmt.executeUpdate();
			System.out.println("Data Inserted Successfully...");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("Display_Users");
//		}

		// Check Box
//		else if(action.equals("selectcheckbox"))
//		{
//			String bookId = request.getParameter("id");
//			String bookName = request.getParameter("bkname");
//			System.out.println("In Action SelectCheckBox");
//			
//			try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD))
//			{
//				//Sql Query To Fetch Data...
//				
//				StringBuilder sql = new StringBuilder("SELECT id");
//				if(bookName!=null)
//				{
//					sql.append(",name");
//				}
//				sql.append(" FROM bookswithimages");
//				
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql.toString());
//				
//				//Start The Table
//				out.println("<table border='1'>");
//				//table header
//				out.println("<tr>");
//				out.println("<th>Book Id</th>");
//				if(bookName!=null)
//				{
//					out.println("<th>Book Name</th>");
//				}
//				out.println("</tr>");
//				
//				while(rs.next())
//				{
//					out.println("<tr>");
//					out.println("<td>"+rs.getInt(1)+"</th>");
//					if(bookName!=null)
//					{
//						out.println("<td>"+rs.getString(2)+"</th>");
//					}
//					out.println("</tr>");
//				}
//                out.println("</table>");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
}
