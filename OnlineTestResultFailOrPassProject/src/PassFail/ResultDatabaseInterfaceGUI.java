package PassFail;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Vector;
import java.sql.*; 
import java.awt.*;

class ResultDatabaseInterfaceGUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JTable table1;
	public DefaultTableModel model1;
	
	/*JTable sheer1;
	Thread t;
	Vector<String> row;
	Vector<String> col;
	Vector<String> roedata;*/
	
	private static final String url ="jdbc:ucanaccess://C:\\Users\\USER\\eclipse-workspace\\ResultFailOrPassProject\\src\\PassFail\\ResultDatabase1.accdb";
	
	ResultDatabaseInterfaceGUI(String ttl) throws ClassNotFoundException, SQLException
	{
		super(ttl);
		setLayout(new BorderLayout());
		int width = 450;
		int height = 300;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);
		setVisible(true);


		JPanel Panel = new JPanel(new BorderLayout());
		String[] columnNames = { "id", "name", "mark", "sub" };
		Object[][] data = {};
		model1 = new DefaultTableModel(data, columnNames);
		table1 = new JTable(model1);
		Panel.add(new JScrollPane(table1));
		add(Panel);
		
		/*
		try
		{
			row=new Vector<String>();

			col=new Vector<String>();
			col.add("Id");
			col.add("Name");
			col.add("Mark");
			col.add("Subject");

			setVisible(true);
			setLocation(350,250);
			setSize(300,300);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			t=new Thread(this);
			t.start();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,""+e);
		}*/
		
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		Connection con=DriverManager.getConnection(url);
		
		model1.setRowCount(0);
		
		String sqlquery= "select * from result_sheer1";
		

		Statement stmt =con.createStatement();
		ResultSet rs=stmt.executeQuery(sqlquery);

		while(rs.next())
		{
			String id = rs.getString("ID"); 
			String nm = rs.getString("name"); 
			String mrk = rs.getString("mark"); 
			String sub = rs.getString("sub"); 
			Object row[] = { id,nm,mrk,sub };
			model1.addRow(row);
		}
		
	}
	/*
	public void Show() throws ClassNotFoundException, SQLException
	{
		
		try
		{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con=DriverManager.getConnection(url);
			
			model1.setRowCount(0);
			
			String sqlquery= "select * from result_sheer1";
			
			//PreparedStatement ps=con.prepareStatement(sql);

			Statement stmt =con.createStatement();
			ResultSet rs=stmt.executeQuery(sqlquery);

//			String id,nm,mrk,sub;

			
			while(rs.next())
			{
				String id = rs.getString("ID"); 
				String nm = rs.getString("name"); 
				String mrk = rs.getString("mark"); 
				String sub = rs.getString("sub"); 
				Object row[] = { id,nm,mrk,sub };
				model1.addRow(row);
			}
			
			//sheer1=new JTable(row,col);  //change
			//sheer1=new JTable();
			//add(sheer1,BorderLayout.CENTER);
//		}
		//catch (Exception e)
		//{
			//JOptionPane.showMessageDialog(null,""+e);
	//	}*/
		
	/*public static void main(String []args) throws ClassNotFoundException, SQLException
	{
		new ResultDatabaseInterfaceGUI();
	}*/
}
