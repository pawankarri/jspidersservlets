package org.jsp.httpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VerifyStudent2")
public class VerifyStudent2 extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String temp = req.getParameter("rn");
		int rollNo = Integer.parseInt(temp);
		
		String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
		String query = "select * from my_database.student_table where rollNo=?";
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, rollNo);
			ResultSet rs = ps.executeQuery();
			PrintWriter writer = resp.getWriter();
			if (rs.next()) 
			{
				writer.println("Student Roll No = "+rs.getInt(1));
				writer.println("Student Name = "+rs.getString(2));
				writer.println("Student Percentage = "+rs.getDouble(3));
				writer.println("Student Stream = "+rs.getString(4));
			}
			else
			{
				writer.println("<h1 style='color:red;'>Invalid Roll No</h1>");
			}
			con.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
}
