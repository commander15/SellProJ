package com.sellpro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sellpro.utils.Database;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Dashboard");
		context.put("sub_title", "Vos ventes en image !");
		
		List<Date> labels = new ArrayList<Date>();
		List<Number> data = new ArrayList<Number>();
		
		ResultSet results = Database.getInstance().execSelect(""
				+ "SELECT Sales.date, SUM(Products.price * SaleProducts.quantity) "
				+ "FROM SaleProducts "
				+ "INNER JOIN Sales ON SaleProducts.sale_id = Sales.id "
				+ "INNER JOIN Products ON SaleProducts.product_id = Products.id "
				+ "GROUP BY Sales.date "
				+ "ORDER BY Sales.date ASC");
		
		try {
			while (results.next()) {
				labels.add(results.getDate(1));
				data.add(results.getDouble(2));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		context.put("labels", labels);
		context.put("data", data);
		
		render("dashboard", context, request, response);
	}

}
