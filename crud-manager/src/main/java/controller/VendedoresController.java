package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Vendedor;
import model.ModelException;
import model.User;
import model.dao.VendedorDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/vendedores", "/vendedor/form", 
		"/vendedor/insert", "/vendedor/delete", "/vendedor/update"})

public class VendedoresController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/vendedor/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-vendedor.jsp");			
			break;
		}
		case "/crud-manager/vendedor/update": {
			
			String idStr = req.getParameter("vendedorId");
            int idVendedor = Integer.parseInt(idStr);
            
            VendedorDAO dao = DAOFactory.createDAO(VendedorDAO.class);
            
            Vendedor vendedor = null;
            try {
                vendedor = dao.findById(idVendedor);
            } catch (ModelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            CommonsController.listUsers(req);
            req.setAttribute("action", "update");
            req.setAttribute("vendedor", vendedor);
            ControllerUtil.forward(req, resp, "/form-vendedor.jsp");
            break;
		}
		default:
			listVendedores(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/vendedores.jsp");
		}
	}
	
	private void listVendedores(HttpServletRequest req) {
		VendedorDAO dao = DAOFactory.createDAO(VendedorDAO.class);
		
		List<Vendedor> vendedores = null;
		try {
			vendedores = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (vendedores != null)
			req.setAttribute("vendedores", vendedores);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/vendedor/insert": {
			insertVendedor(req, resp);			
			break;
		}
		case "/crud-manager/vendedor/update": {
			updateVendedor(req, resp);			
			break;
		}
		case "/crud-manager/vendedor/delete" :{
			
			deleteVendedor(req, resp);
			
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/vendedores");
	}

	private void deleteVendedor(HttpServletRequest req, HttpServletResponse resp) {
		String vendedorIdParameter = req.getParameter("id");
		
		int vendedorId = Integer.parseInt(vendedorIdParameter);
		
		VendedorDAO dao = DAOFactory.createDAO(VendedorDAO.class);
		
		try {
			Vendedor vendedor = dao.findById(vendedorId);
			
			if (vendedor == null)
				throw new ModelException("Vendedor não encontrada para deleção.");
			
			if (dao.delete(vendedor)) {
				ControllerUtil.sucessMessage(req, "Vendedor '" + 
						vendedor.getName() + "' deletada com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Vendedor '" + 
						vendedor.getName() + "' não pode ser deletado. "
								+ "Há dados relacionados ao vendedor.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof 
					SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertVendedor(HttpServletRequest req, HttpServletResponse resp) {
		String vendedorName = req.getParameter("name");
		String email = req.getParameter("email");
		String dataNasc = req.getParameter("dataNasc");
		String telefone = req.getParameter("telefone");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Vendedor vende = new Vendedor();
		vende.setName(vendedorName);
		vende.setEmail(email);
		vende.setDataNasc(ControllerUtil.formatDate(dataNasc));
		vende.setTelefone(telefone);
		vende.setUser(new User(userId));
		
		VendedorDAO dao = DAOFactory.createDAO(VendedorDAO.class);
	
		try {
			if (dao.save(vende)) {
				ControllerUtil.sucessMessage(req, "Vendedor '" + vende.getName() 
				+ "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Vendedor '" + vende.getName()
				+ "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateVendedor(HttpServletRequest req, HttpServletResponse resp) {
		 String vendedorName = req.getParameter("name");
		 String email = req.getParameter("email");
		 String dataNasc = req.getParameter("dataNasc");
		 String telefone = req.getParameter("telefone");
		 Integer userId = Integer.parseInt(req.getParameter("user"));

		 Vendedor vende = new Vendedor();
		 vende.setName(vendedorName);
		 vende.setEmail(email);
		 vende.setDataNasc(ControllerUtil.formatDate(dataNasc));
		 vende.setTelefone(telefone);
		 vende.setUser(new User(userId));

		 VendedorDAO dao = DAOFactory.createDAO(VendedorDAO.class);

		 try {
	            if (dao.update(vende)) {
	                ControllerUtil.sucessMessage(req, "Vendedor '" + vende.getName() + "' atualizado com sucesso.");
	            }
	            else {
	                ControllerUtil.errorMessage(req, "Vendedor '" + vende.getName() + "' não pode ser atualizado.");
	            }                
	        } catch (ModelException e) {
	            // log no servidor
	            e.printStackTrace();
	            ControllerUtil.errorMessage(req, e.getMessage());
	        }
	
	}
	
}
