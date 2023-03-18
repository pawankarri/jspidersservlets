package org.jsp.httpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VerifyEmail")
public class VerifyEmail extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String email = req.getParameter("email");
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.userinformation where email=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyPassword.html");
				rd.include(req, resp);
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyEmail.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid Mail ID Please enter valid Mail ID</h1>");
			}
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}
}