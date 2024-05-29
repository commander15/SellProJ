package com.sellpro;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sellpro.data.Product;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products/*")
public class ProductServlet extends Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path == null)
			showAllProducts(request, response);
		else if (path.endsWith("/add"))
			showAddProduct(request, response);
		else if (path.endsWith("/edit"))
			showEditProduct(request, response);
		else if (path.endsWith("/delete"))
			deleteProduct(request, response);
		else
			System.out.println("Requested unknown path: " + path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path.endsWith("/add"))
			addProduct(request, response);
		else if (path.endsWith("/edit"))
			editProduct(request, response);
		else
			System.out.println("Requested unknown path: " + path);
		
		redirect("/products", request, response);
	}
	
	private void showAllProducts(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Produits");
		context.put("sub_title", "Les produits !");
		context.put("products", Product.getAll());
		render("products", context, request, response);
	}
	
	private void showAddProduct(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ajouter un produit");
		context.put("sub_title", "Plus de produits, plus de ventes !");
		context.put("product", new Product());
		context.put("action", "add");
		render("product_edit", context, request, response);
	}
	
	private void addProduct(HttpServletRequest request, HttpServletResponse response) {
		Product product = new Product();
		product.name = request.getParameter("name");
		product.description = request.getParameter("description");
		product.price = Double.parseDouble(request.getParameter("price"));
		product.in_stock = Integer.parseInt(request.getParameter("in_stock"));
		product.save();
	}
	
	private void showEditProduct(HttpServletRequest request, HttpServletResponse response) {
		String[] path = request.getPathInfo().split("/");
		Product product = new Product();
		product.get(Integer.parseInt(path[1]));
		
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Modifier un produit");
		context.put("sub_title", "Update to stay up !");
		context.put("product", product);
		context.put("action", String.valueOf(product.id) + "/edit");
		render("product_edit", context, request, response);
	}
	
	private void editProduct(HttpServletRequest request, HttpServletResponse response) {
		String[] path = request.getPathInfo().split("/");
		Product product = new Product();
		product.id = Integer.parseInt(path[1]);
		product.name = request.getParameter("name");
		product.description = request.getParameter("description");
		product.price = Double.parseDouble(request.getParameter("price"));
		product.in_stock = Integer.parseInt(request.getParameter("in_stock"));
		product.save();
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		String[] path = request.getPathInfo().split("/");
		Product product = new Product();
		product.get(Integer.parseInt(path[1]));
		product.delete();
		
		redirect("/products", request, response);
	}

}
