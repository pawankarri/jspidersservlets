package org.jsp.applications;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.lang.model.element.NestingKind;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerfiyOTP")
public class VerfiyOTP extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String otpp = req.getParameter("otp");
		System.out.println("User password = "+otpp);
		
		//access session object
		HttpSession session =req.getSession();
		//fetch password from session object 
		int sessionOtp = (int) session.getAttribute("otpp");
		System.out.println("DB Password = "+sessionOtp);
		PrintWriter writer = resp.getWriter();
			if (otpp.equals(sessionOtp))
			{			
//				time interval
				session.setMaxInactiveInterval(10);
				//Display user portal
				RequestDispatcher rd = req.getRequestDispatcher("VerifyOTP.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:green;'>OTP Verified Successful!!!</h1>");
				writer.println("Welcome "+session.getAttribute("name"));
				}
			else 
			{
				RequestDispatcher rd = req.getRequestDispatcher("VerifyMobile.html");
				rd.include(req, resp);
				writer.println("<h1 style = 'color:red;'>Invalid OTP Please enter valid OTP</h1>");
				if(session == null)
				{
					writer.println("<h1 style='color:red;'>Please enter OTP before 10 Seconds<h1>");
				}
			}		
	}
}