package com.sellpro;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sellpro.data.Sale;
import com.sellpro.data.SaleProduct;
import com.sellpro.data.Product;

/**
 * Servlet implementation class SaleServlet
 */
@WebServlet("/sales/*")
public class SaleServlet extends Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path == null)
			showAllSales(request, response);
		else if (path.contains("/products/"))
			(new SaleProductServlet(Integer.parseInt(path.split("/")[1]))).doGet(request, response);
		else if (path.endsWith("/add"))
			showAddSale(request, response);
		else if (path.endsWith("/edit"))
			showEditSale(request, response);
		else if (path.endsWith("/delete"))
			deleteSale(request, response);
		else
			System.out.println("Requested unknown path: " + path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path.contains("/products/"))
			(new SaleProductServlet(Integer.parseInt(path.split("/")[1]))).doPost(request, response);
		else if (path.endsWith("/add"))
			addSale(request, response);
		else if (path.endsWith("/edit"))
			editSale(request, response);
		else
			System.out.println("Requested unknown path: " + path);
	}
	
	private void showAllSales(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ventes");
		context.put("sub_title", "Vendez de tout !");
		context.put("sales", Sale.getAll());
		render("sales", context, request, response);
	}
	
	private void showAddSale(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ventes");
		context.put("sub_title", "Vendez de tout !");
		context.put("sale", new Sale());
		
		render("sale_edit", context, request, response);
	}
	
	private void addSale(HttpServletRequest request, HttpServletResponse response) {
		Sale sale = new Sale();
		sale.customer = request.getParameter("customer");
		sale.date = new Date(System.currentTimeMillis());
		sale.time = new Time(System.currentTimeMillis());
		sale.save();
		
		redirect("/sales/" + String.valueOf(sale.id) + "/edit", request, response);
	}
	
	private void showEditSale(HttpServletRequest request, HttpServletResponse response) {
		Sale sale = new Sale();
		sale.get(Integer.parseInt(request.getPathInfo().split("/")[1]));
		
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ventes");
		context.put("sub_title", "Vendez de tout !");
		context.put("sale", sale);
		
		render("sale_edit", context, request, response);
	}
	
	private void editSale(HttpServletRequest request, HttpServletResponse response) {
		Sale sale = new Sale();
		sale.id = Integer.parseInt(request.getPathInfo().split("/")[1]);
		sale.customer = request.getParameter("customer");
		sale.date = new Date(System.currentTimeMillis());
		sale.time = new Time(System.currentTimeMillis());
		sale.save();
		
		redirect("/sales", request, response);
	}
	
	private void deleteSale(HttpServletRequest request, HttpServletResponse response) {
		Sale sale = new Sale();
		sale.get(Integer.parseInt(request.getPathInfo().split("/")[1]));
		sale.delete();
		
		redirect("/sales", request, response);
	}

}
