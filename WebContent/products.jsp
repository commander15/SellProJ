<%@page import="com.sellpro.data.Product"%>
<%@page import="java.util.List"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="base_begin.jsp" />


<div class="card mb-4">
    <div class="card-header">
        <i class="fas fa-table me-1"></i>
        ${ title }
        <a class="btn btn-success" href="products/add">Ajouter</a>
    </div>
    
    <div class="card-body">
        <table id="datatablesSimple">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Description</th>
                    <th>Prix</th>
                    <th>En Stock</th>
                    <th>Operations</th>
                </tr>
            </thead>
            <tbody>
	           <% 
                List<Product> products = (List<Product>) request.getAttribute("products");
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
            	%>
	                <tr>
	                    <td><%= product.name %></td>
	                    <td><%= product.description %></td>
	                    <td><%= product.price %></td>
	                    <td><%= product.in_stock %></td>
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

<jsp:include page="base_end.jsp" />
