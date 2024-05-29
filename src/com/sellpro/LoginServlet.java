package com.sellpro;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sellpro.utils.Database;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		render("login", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> context = new HashMap<String, Object>();
		Database db = Database.getInstance();
		
		PreparedStatement query = db.prepare("SELECT password FROM Users WHERE email=? LIMIT 1");
		try {
			query.setString(1, request.getParameter("email"));
			query.execute();
			
			ResultSet results = query.getResultSet();
			if (!results.next()) {
				context.put("error", "Une erreur est survenu, veuillez reessayez");
			} else if (results.getString(1) != request.getParameter("password")) {
				context.put("error", "Login/mot de passe incorrect");
			} else {
				redirect("/dashboard", request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		render("login", context, request, response);
	}

}
