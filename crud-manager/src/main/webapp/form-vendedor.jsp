<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@include file="base-head.jsp"%>
</head>
<body>
	<%@include file="nav-menu.jsp"%>
	
	<div id="container" class="container-fluid">
		<h3 class="page-header">Atualizar Vendedor</h3>
		
		<form action="${pageContext.request.contextPath}/vendedor/${action}" 
			method="POST">
			
			<div class="row">
				<div class="form-group col-md-6">
					<label for="name">Nome</label>
					<input type="text" class="form-control" id="name" name="name" 
						   autofocus="autofocus" placeholder="Nome Vendedor" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o nome do vendedor.')"
						   oninput="setCustomValidity('')" />
				</div>
				
				<div class="form-group col-md-6">
					<label for="email">Email</label>
					<input type="text" class="form-control" id="email" name="email" 
						   autofocus="autofocus" placeholder="Email já cadastrado" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe o email.')"
						   oninput="setCustomValidity('')" />
				</div>				
			</div>
			
			<div class="row">
				<div class="form-group col-md-4">
					<label for="dataNasc">Data nascimento</label>
					<input type="date" class="form-control" id="dataNasc" name="dataNasc" 
						   autofocus="autofocus" placeholder="Data de nascimento" 
						   required 
						   oninvalid="this.setCustomValidity('Por favor, informe a data de nascimento.')"
						   oninput="setCustomValidity('')" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="telefone">Telefone</label>
					<input type="text" class="form-control" id="telefone" name="telefone" 
						   autofocus="autofocus" placeholder="Telefone"				    
						   oninvalid="this.setCustomValidity('Por favor, informe o telefone')"
						   oninput="setCustomValidity('')" />
				</div>
				
				<div class="form-group col-md-4">
					<label for="user">Usuário</label>
					<select id="user" class="form-control selectpicker" name="user" 
						    required oninvalid="this.setCustomValidity('Por favor, informe o usuário.')"
						    oninput="setCustomValidity('')">
					  <option value="">Selecione um usuário</option>
					  <c:forEach var="user" items="${users}">
					  	<option value="${user.getId()}">
					  		${user.getName()}
					  	</option>	
					  </c:forEach>
					</select>
				</div>
			</div>
			
			<hr />
			<div id="actions" class="row pull-right">
				<div class="col-md-12">
					
					<a href="${pageContext.request.contextPath}/vendedores" 
					   class="btn btn-default">Cancelar</a>
					
					<button type="submit" 
						    class="btn btn-primary">Atualizar Vendedor</button>
				</div>
			</div>
		</form>
		
	</div>

</body>
</html>