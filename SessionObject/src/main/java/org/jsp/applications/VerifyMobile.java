package org.jsp.applications;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyMobile")
public class VerifyMobile extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String mobileNo = req.getParameter("mobile");
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.userinformation where Mobile=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, mobileNo);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			Random random = new Random();
			int number = random.nextInt(10000);
			//Display only 4 digits numbers
			if (number<1000) 
			{
				number +=1000;
			}
			System.out.println("Your OTP is : "+number);
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
				RequestDispatcher rd = req.getRequestDispatcher("VerifyOTP.html");
				rd.include(req, resp);
				writer.println("<p style = 'color:green;'>Mobile Number verified successfully</p>");
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyMobile.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid Mobile Number Please enter valid Mobile Number</h1>");
			}
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}
}