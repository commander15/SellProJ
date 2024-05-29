<%@page import="com.sellpro.data.Sale"%>
<%@page import="java.util.List"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="base_begin.jsp" />


<div class="card mb-4">
    <div class="card-header">
        <i class="fas fa-table me-1"></i>
        ${ title }
        <a class="btn btn-success" href="sales/add">Ajouter</a>
    </div>
    
    <div class="card-body">
        <table id="datatablesSimple">
            <thead>
                <tr>
                    <th>Client</th>
                    <th>Date</th>
                    <th>Heure</th>
                    <th>Montant</th>
                    <th>Operations</th>
                </tr>
            </thead>
            <tbody>
	           <% 
                List<Sale> sales = (List<Sale>) request.getAttribute("sales");
                for (int i = 0; i < sales.size(); i++) {
                    Sale sale = sales.get(i);
            	%>
	                <tr>
	                    <td><%= sale.customer %></td>
	                    <td><%= sale.date %></td>
	                    <td><%= sale.time %></td>
	                    <td><%= sale.getAmount() %></td>
	            		<td>
	            			<center>
		            			<a href="sales/<%= sale.id %>/edit" class="btn btn-warning">Modiffier</a>
		            			<a href="sales/<%= sale.id %>/delete" class="btn btn-danger">Supprimer</a>
	            			</center>
	            		</td>
	                </tr>
	            <% } %>
            </tbody>
          </table>
       </div>
    </div>

<jsp:include page="base_end.jsp" />
