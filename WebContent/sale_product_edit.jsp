<%@page import="com.sellpro.data.Product"%>
<%@page import="com.sellpro.data.SaleProduct" %>
<%@page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="base_begin.jsp" />

 <form method="POST">
     <div class="form-floating mb-3">
         <select id="product" name="product" class="form-control">
         	<% 
         		SaleProduct sale_product = SaleProduct.class.cast(request.getAttribute("sale_product"));
         	
         		List<Product> products = Product.getAll();
         		for (int i = 0; i < products.size(); ++i) {
         			Product product = products.get(i);
         	%>
         	<option value="<%= product.id %>" <% if (sale_product.product.id == product.id) { %> selected <% } %>><%= product.name %></option>
         	<% } %>
         </select>
         <label for="product">Produit</label>
     </div>
     
     <div class="form-floating mb-3">
         <input id="quantity" name="quantity" value="${ sale_product.quantity }" class="form-control" />
         <label for="quantity">Quantité</label>
     </div>
     
     <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
     	<input value="Enregister" type="submit" class="btn btn-primary" />
     </div>
 </form>

<jsp:include page="base_end.jsp" />