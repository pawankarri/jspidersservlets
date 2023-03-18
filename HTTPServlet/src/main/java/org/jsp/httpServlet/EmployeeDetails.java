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

@WebServlet("/EmployeeDetails")
public class EmployeeDetails extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String temp = req.getParameter("id");
		int empId = Integer.parseInt(temp);
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.employee where empId=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("Portal.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:green;'>Login Successful!!!"+"<br> Employee Name : "+rs.getString("ename")+"</h1>");
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("EmployeeDetails.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid Employee ID Please enter valid Employee ID</h1>");
			}
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}
}