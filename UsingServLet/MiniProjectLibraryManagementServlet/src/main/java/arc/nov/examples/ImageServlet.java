package arc.nov.examples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "jdbc:mysql://localhost:3306/library_db";
	private static final String USER = "root";
	private static final String PASSWORD = "Root";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In Image Servlet");
		String bookId = request.getParameter("id");

		if (bookId == null || bookId.isEmpty()) {
			response.getWriter().println("No image ID provided.");
			return;
		}
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			String sql = "SELECT img FROM bookswithimages WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(bookId));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				response.setContentType("image/jpeg");
				byte[] imgData = rs.getBytes("img");

				OutputStream Out2 = response.getOutputStream();
				Out2.write(imgData);
			} else {
				response.getWriter().println("<p>No Image Found For The Given ID</p>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
