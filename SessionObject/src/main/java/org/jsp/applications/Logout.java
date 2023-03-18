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


@WebServlet("/Logout")
public class Logout extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		//Access username
		String name = (String)session.getAttribute("name");
				
		PrintWriter writer = response.getWriter();
		if(name != null)
		{
			writer.println("Thank You "+ name+ " for visiting the website...");
		}
		else 
		{
			writer.println("<h1 style='color:red;'>Session time out<h1>");
			//if session contains null value then close it
			session.invalidate();
			System.out.println("Session closed...");
		}
	}

}
