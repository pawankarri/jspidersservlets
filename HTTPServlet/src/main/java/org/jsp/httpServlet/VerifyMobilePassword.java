package org.jsp.httpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/VerifyMobilePassword")
public class VerifyMobilePassword extends HttpServlet
{
  @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException 
  {
     String mobile=req.getParameter("mobile");
     String pwd=req.getParameter("password");
     
     PrintWriter writer=resp.getWriter();
     
     String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
	 String query="Select * from my_database.userinformation where Mobile=? AND password=?";
     try 
     {
    	Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1, mobile);
		ps.setString(2, pwd);
		ResultSet rs=ps.executeQuery();
		if(rs.next())
		{
			writer.println("Welcome "+rs.getString("Name"));
			writer.println("Your EmailId :"+rs.getString("Email"));
			
//			if (rs.getString("Mobile").length() != 10) 
//			{
//	            writer.println("Invalid mobile number!");
//	            return;
//	        }  
			
	        String firstTwoDigits = rs.getString("Mobile").substring(0, 2);
	        String lastTwoDigits = rs.getString("Mobile").substring(8, 10);
	        String middleDigits = "******";
	        
	        String num = firstTwoDigits + middleDigits + lastTwoDigits;
	        
	        writer.println("Your mobile No : " + num);
		}
		else
		{
			writer.println("<h1 style='color:red;'>Invalid Credentials</h1>");
		}
		
	} 
     catch (SQLException | ClassNotFoundException e) 
    {
		e.printStackTrace();
	}
  }
}