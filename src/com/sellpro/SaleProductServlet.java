package com.sellpro;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sellpro.data.Sale;
import com.sellpro.data.SaleProduct;

/**
 * Servlet implementation class SaleProductServlet
 */
@WebServlet("/sales/*/products/*")
public class SaleProductServlet extends Servlet {
	private int saleId;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleProductServlet(int saleId) {
        super();
        this.saleId = saleId;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path.endsWith("add"))
			showAddProduct(request, response);
		else if (path.endsWith("edit"))
			showEditProduct(request, response);
		else if (path.endsWith("delete"))
			deleteProduct(request, response);
		else
			System.out.println("Requested unknown path: " + path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path.endsWith("add"))
			addProduct(request, response);
		else if (path.endsWith("edit"))
			editProduct(request, response);
		else
			System.out.println("Requested unknown path: " + path);
	}
	
	private void showAddProduct(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ventes");
		context.put("sub_title", "Vendez de tout !");
		context.put("sale_product", new SaleProduct());
		render("sale_product_edit", context, request, response);
	}
	
	private void addProduct(HttpServletRequest request, HttpServletResponse response) {
		SaleProduct product = new SaleProduct();
		product.product.id = Integer.parseInt(request.getParameter("product"));
		product.quantity = Integer.parseInt(request.getParameter("quantity"));
		product.saleId = saleId;
		product.save();
		
		redirect("/sales/" + String.valueOf(saleId) + "/edit", request, response);
	}
	
	private void showEditProduct(HttpServletRequest request, HttpServletResponse response) {
		SaleProduct product = new SaleProduct();
		product.id = Integer.parseInt(request.getPathInfo().split("/")[3]);
		product.get();
		
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("title", "Ventes");
		context.put("sub_title", "Vendez de tout !");
		context.put("sale_product", product);
		render("sale_product_edit", context, request, response);
	}
	
	private void editProduct(HttpServletRequest request, HttpServletResponse response) {
		SaleProduct product = new SaleProduct();
		product.id = Integer.parseInt(request.getPathInfo().split("/")[3]);
		product.product.id = Integer.parseInt(request.getParameter("product"));
		product.quantity = Integer.parseInt(request.getParameter("quantity"));
		product.saleId = saleId;
		product.save();

		redirect("/sales/" + String.valueOf(saleId) + "/edit", request, response);
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		SaleProduct product = new SaleProduct();
		product.get(Integer.parseInt(request.getPathInfo().split("/")[3]));
		product.delete();

		redirect("/sales/" + String.valueOf(saleId) + "/edit", request, response);
	}

}
