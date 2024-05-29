<%@page import="com.sellpro.data.Sale"%>
<%@page import="com.sellpro.data.SaleProduct"%>
<%@page import="com.sellpro.data.Product"%>
<%@page import="java.util.List"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="base_begin.jsp" />


 <form method="POST">
     <% Sale sale = Sale.class.cast(request.getAttribute("sale")); %>
     
     <div class="form-floating mb-3">
         <input id="customer" name="customer" value="${ sale.customer }" type="text" class="form-control" />
         <label for="customer">Client</label>
     </div>
     
     <% if (sale.id > 0) { %>
     <div class="form-floating mb-3">
         <p id="date" class="form-control">${ sale.date }</p>
         <label for="name">Date</label>
     </div>
     
     <div class="form-floating mb-3">
         <p id="time" class="form-control">${ sale.time }</p>
         <label for="time">Heure</label>
     </div>
     
     <% } %>
     
     <div class="card mb-4"  style="display:<% if (sale.id > 0) { %> block <% } else { %> none <% } %>">
     
     <div class="card-header">
        <i class="fas fa-table me-1"></i>
        ${ title }
        <a class="btn btn-success" href="products/add">Ajouter</a>
     </div>
     
     <div class="card-body">
     <table id="datatablesSimple">
     	<thead>
     		<th>Product</th>
     		<th>Quantity</th>
     		<th>Price</th>
     		<th>Operations</th>
     	</thead>
     	<tbody>
     		<%
            List<SaleProduct> products = sale.getProducts();
            for (int i = 0; i < products.size(); i++) {
                SaleProduct product = products.get(i);
     		%>
     		<tr>
     			<td><%= product.product.name %></td>
     			<td><%= product.quantity %></td>
     			<td><%= product.product.price * product.quantity %></td>
     			<td>
           			<center>
            			<a href="products/<%= product.id %>/edit" class="btn btn-warning">Modiffier</a>
            			<a href="products/<%= product.id %>/delete" class="btn btn-danger">Supprimer</a>
           			</center>
     			</td>
     		</tr>
     		<% } %>
     	</tbody>
     </table>
     </div>
     </div>
     
     <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
     	<input value="Enregister" type="submit" class="btn btn-primary" />
     </div>
 </form>

<jsp:include page="base_end.jsp" />