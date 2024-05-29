package com.sellpro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	void render(String template, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		render(template, context, request, response);
	}
	
	void render(String template, HashMap<String, Object> context, HttpServletRequest request, HttpServletResponse response) {
		Set<String> attributes = context.keySet();
		attributes.forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				request.setAttribute(t, context.get(t));
			}
		});
		
		try {
			request.getRequestDispatcher('/' + template + ".jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void redirect(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
