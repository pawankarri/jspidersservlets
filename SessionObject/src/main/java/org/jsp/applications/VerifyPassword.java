package org.jsp.applications;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Random;

import javax.lang.model.element.NestingKind;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyPassword")
public class VerifyPassword extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String pwds = req.getParameter("pwdss");
		System.out.println("User password = "+pwds);
		
		//access session object
		HttpSession session =req.getSession();
		//fetch password from session object 
		String sessionPassword = (String) session.getAttribute("pwd");
		System.out.println("DB Password = "+sessionPassword);
		PrintWriter writer = resp.getWriter();
			if (pwds.equals(sessionPassword))
			{			
				//time interval
				session.setMaxInactiveInterval(10);
				//Display user portal
				
				RequestDispatcher rd = req.getRequestDispatcher("logins.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:green;'>Login Successful!!!</h1>");
				writer.println("Welcome "+session.getAttribute("name"));
			}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyEmail.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid Password Please enter valid password</h1>");
				if(session == null)
				{
					writer.println("<h1 style='color:red;'>Please enter password before 10 Seconds<h1>");
				}
			}		
	}
}