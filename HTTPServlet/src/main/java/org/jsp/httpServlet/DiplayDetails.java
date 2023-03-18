package org.jsp.httpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DiplayDetails")
public class DiplayDetails extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mob = req.getParameter("mob");
		String pass = req.getParameter("pass");
		
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.userinformation where Mobile=? AND Email=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, mob);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next()) 
			{
				rs.beforeFirst();
				while (rs.next())
				{
					writer.println("User Email = "+rs.getInt(5));
					writer.println("User Mobile = "+rs.getString(2));
				}
			}
			else
			{
				writer.println("<h1 style='color:red;'>Invalid Details</h1>");
			}
			con.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
}
