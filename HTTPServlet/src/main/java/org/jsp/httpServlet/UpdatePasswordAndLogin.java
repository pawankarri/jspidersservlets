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

@WebServlet("/UpdatePasswordAndLogin")
public class UpdatePasswordAndLogin extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		String mobile = req.getParameter("mobile");
		String oldPassword = req.getParameter("oldpassword");
		String newPassword = req.getParameter("newpassword");
		
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query1 = "update my_database.userinformation set Password=? where Mobile=? AND Password=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query1);
			ps.setString(2, mobile);
			ps.setString(3, oldPassword);
			ps.setString(1, newPassword);
			PrintWriter writer = resp.getWriter();
			int record = ps.executeUpdate();
			
			if (record>0) 
			{
				RequestDispatcher rd = req.getRequestDispatcher("Login.html");
				rd.include(req, resp);
//				writer.println("<h1 style = 'color:green;'>Login Successful!!!</h1>");
				String email = req.getParameter("email");
			    String newpwd = req.getParameter("newpwd");
			    String query2 ="Select * from my_database.userinformation where Email=? AND Password=?";
//			    Connection conn = DriverManager.getConnection(url);
			    con.prepareStatement(query2);
				ps.setString(1, email);
				ps.setString(2, newpwd);
				ResultSet rs=ps.executeQuery();
				System.out.println(rs);
				if(rs.next())
				{
					writer.println("Welcome "+rs.getString("Name"));
					writer.println("Your Mobile :"+rs.getString("Mobile"));
					writer.println("Your Password :"+rs.getString("Password"));
					writer.println("Your EmailId :"+rs.getString("Email"));
				}
//				else 
//				{
//					RequestDispatcher rd1 = req.getRequestDispatcher("Login.html");
//					rd.include(req, resp);
//					writer.println("<h1 style = 'color:red;'>Invalid Employee ID Please enter valid Employee ID</h1>");
//				}
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("UpdatePassword.html");
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