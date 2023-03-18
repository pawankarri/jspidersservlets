package org.jsp.applications;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
				//Retrieve data from database
				String name = rs.getString(1);
				String mob = rs.getString(2);
				String password = rs.getString(3);
				String gender = rs.getString(4);
				String mail = rs.getString(5);
				System.out.println("Data retrieved from Database...");
				
				//Access session object
				HttpSession session = req.getSession();
				System.out.println("Session object accessed from JEE container...");
				
				//Store users data in session object
				session.setAttribute("name",name);
				session.setAttribute("mobile",mob);
				session.setAttribute("genders",gender);
				session.setAttribute("email",mail);
				session.setAttribute("pwd",password);
				System.out.println("User data stored in session object...");
				
				//time interval
				session.setMaxInactiveInterval(10);
				
				//perform servlet chaining
				RequestDispatcher rd = req.getRequestDispatcher("VerifyPassword.html");
				rd.include(req, resp);
				writer.println("<p style = 'color:green;'>Email verified successfully</p>");
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