package org.jsp.httpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteUserDetails")
public class DeleteUserDetails extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String email = req.getParameter("uemail");
		String pwd = req.getParameter("upwd");
		
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "Delete from my_database.userdetails where EmailId=? AND Password=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pwd);
			PrintWriter writer = resp.getWriter();
			int record = ps.executeUpdate();
			
			if (record>0) 
			{
				writer.println("<h1 style='color:green;'>Record Deleted</h1>");
			}
			else
			{
				writer.println("<h1 style='color:red;'>Invalid Email and Password</h1>");
			}
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}	