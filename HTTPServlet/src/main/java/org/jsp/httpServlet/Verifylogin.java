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

@WebServlet("/Verifylogin")
public class Verifylogin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String email = req.getParameter("email");
		String password = req.getParameter("newpwd");
		
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query1 = "select * from my_database.userinformation where Email=? AND Password=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query1);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next())
			{
				resp.sendRedirect("https://www.amazon.com");
//				RequestDispatcher rd = req.getRequestDispatcher("https://www.amazon.com");
//				rd.include(req, resp);
//				writer.println("<h1 style = 'color:green;'>Login Successful!!!"+"<br> Employee Name : "+rs.getString("ename")+"</h1>");
			}
			else 
			{
				resp.sendRedirect("https://www.facebook.com");
//				RequestDispatcher rd = req.getRequestDispatcher("https://www.facebook.com");
//				rd.include(req, resp);
//				writer.println("<h1 style = 'color:red;'>Invalid Employee ID Please enter valid Employee ID</h1>");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}	