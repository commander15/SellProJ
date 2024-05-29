<%@page import="com.sellpro.data.Product"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="base_begin.jsp" />


 <form method="POST">
     <div class="form-floating mb-3">
         <input id="name" name="name" value="${ product.name }" type="text" class="form-control" />
         <label for="name">Nom</label>
     </div>
     
     <div class="form-floating mb-3">
         <input id="name" name="description" value="${ product.description }" type="text" class="form-control" />
         <label for="name">Description</label>
     </div>
     
     <div class="form-floating mb-3">
         <input id="name" name="price" value="${ product.price }" type="text" class="form-control" />
         <label for="name">Prix</label>
     </div>
     
     <div class="form-floating mb-3">
         <input id="name" name="in_stock" value="${ product.in_stock }" type="text" class="form-control" />
         <label for="name">En Stock</label>
     </div>
     
     <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
     	<input value="Enregister" type="submit" class="btn btn-primary" />
     </div>
 </form>

<jsp:include page="base_end.jsp" />