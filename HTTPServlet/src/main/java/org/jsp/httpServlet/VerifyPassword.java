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

@WebServlet("/VerifyPassword")
public class VerifyPassword extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String pwd = req.getParameter("pwd");
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.userinformation where password=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, pwd);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyLogins.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:green;'>Login Successful!!!</h1>");
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyEmail.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid Password Please enter valid password</h1>");
			}
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}
}