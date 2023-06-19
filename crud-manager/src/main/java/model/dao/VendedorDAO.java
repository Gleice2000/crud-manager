package model.dao;

import java.util.List;

import model.ModelException;
import model.Vendedor;

public interface VendedorDAO {
	boolean save(Vendedor vendedor) throws ModelException;
	boolean update(Vendedor vendedor) throws ModelException;
	boolean delete(Vendedor vendedor) throws ModelException;
	List<Vendedor> listAll() throws ModelException;
	Vendedor findById(int id) throws ModelException;
}
