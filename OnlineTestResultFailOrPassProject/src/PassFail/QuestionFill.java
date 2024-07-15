package PassFail;

import java.sql.*;
import javax.swing.JOptionPane;

class QuestionFill {
	
	DatabaseInterface databaseinterface;
	static Connection con;
	static PreparedStatement ps;
	static String sql;
	static ResultSet rs;

	String table;
	private static final String url = "jdbc:ucanaccess://C:\\Users\\USER\\eclipse-workspace\\ResultFailOrPassProject\\src\\PassFail\\AptiDatabase.mdb";

	Question que;
	int id;

	static {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection(url);
			sql = new String();
		} catch (Exception a) {
			System.out.println("" + a);
		}
	}

	QuestionFill() {
		que = new Question();
		id = 1;

		if (SubjectChooserForFillQuetion.getSubject().trim().equals("C Programming"))
			table = "c_apti_db";
		if (SubjectChooserForFillQuetion.getSubject().trim().equals("C++ Programming"))
			table = "cpp_apti_db";
		if (SubjectChooserForFillQuetion.getSubject().trim().equals("Java Programming"))
			table = "j_apti_db";
		if (SubjectChooserForFillQuetion.getSubject().trim().equals("C# Programming"))
			table = "ch_apti_db";
	}

	Question getFullQuestionForQuetionFill(int id) {
		try {
			sql = "select * from " + table + " where id=" + id;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				que.setID(id);
				que.setQuestion(rs.getString("question").trim());
				que.setOption1(rs.getString("opt1").trim());
				que.setOption2(rs.getString("opt2").trim());
				que.setOption3(rs.getString("opt3").trim());
				que.setOption4(rs.getString("opt4").trim());
				que.setAnswer(rs.getString("answer").trim());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "" + e);
		}

		return que;
	}

	void storeQuestion(int id, String que, String o1, String o2, String o3, String o4, String ans) {
		try {
			String sql1 = "insert into " + table + " (id,question,opt1,opt2,opt3,opt4,answer)values(?,?,?,?,?,?,?)";

			ps = con.prepareStatement(sql1);
			ps.setString(1, "" + id);
			ps.setString(2, que.trim());
			ps.setString(3, o1.trim());
			ps.setString(4, o2.trim());
			ps.setString(5, o3.trim());
			ps.setString(6, o4.trim());
			ps.setString(7, ans.trim());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Check Id " + e);
		}
	}

	void updateQuestion(int id, String que, String o1, String o2, String o3, String o4, String ans) {
		try {
			sql = "update " + table + " set question=?,opt1=?,opt2=?,opt3=?,opt4=?,answer=? where id=" + id;
			ps = con.prepareStatement(sql);
			ps.setString(1, que.trim());
			ps.setString(2, o1.trim());
			ps.setString(3, o2.trim());
			ps.setString(4, o3.trim());
			ps.setString(5, o4.trim());
			ps.setString(6, ans.trim());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Check Id " + e);
		}
	}

	/*
	 * Question getFullQuestionForQuetionFIll(int id) //-------------------------- {
	 * try { sql="select * from "+table+" where id="+id;
	 * ps=con.prepareStatement(sql); rs=ps.executeQuery();
	 * 
	 * if(rs.next()) { q.setID(id); q.setQuestion(rs.getString("question").trim());
	 * q.setOption1(rs.getString("opt1").trim());
	 * q.setOption2(rs.getString("opt2").trim());
	 * q.setOption3(rs.getString("opt3").trim());
	 * q.setOption4(rs.getString("opt4").trim());
	 * q.setAnswer(rs.getString("answer").trim()); } } catch (Exception e) {
	 * JOptionPane.showMessageDialog(null,""+e); } return q; }
	 */

	int getNextID() {
		try {
			// sql="select id from apti_db";
			// sql="select id from apti_db";
			String sql1 = "select id from " + table;

			ps = con.prepareStatement(sql1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Result Set Error " + e);
		}
		return id + 1;
	}

	public static void main(String[] args) {
		// new SubjectChooserForFillQuetion("Subject chooser");
		JOptionPane.showMessageDialog(null, "" + new QuestionFill().getNextID());
	}
}
