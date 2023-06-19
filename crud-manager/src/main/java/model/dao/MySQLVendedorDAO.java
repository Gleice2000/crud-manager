package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Vendedor;
import model.ModelException;
import model.User;

public class MySQLVendedorDAO implements VendedorDAO {

	@Override
	public boolean save(Vendedor vendedor) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO vendedores VALUES (DEFAULT, ?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		
		db.setString(1, vendedor.getName());
		db.setString(2, vendedor.getEmail());
		db.setDate(3, vendedor.getDataNasc() == null ? new Date() : vendedor.getDataNasc());
		db.setString(4, vendedor.getTelefone());
		db.setInt(5, vendedor.getUser().getId());
		
		return db.executeUpdate() > 0;	
	}

	@Override
	public boolean update(Vendedor vendedor) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE vendedores "
				+ " SET nome = ?, "
				+ " email = ?, "
				+ " dataNasc = ?, "
				+ " telefone = ?, "
				+ " user_id = ? "
				+ " WHERE id = ?; "; 
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, vendedor.getName());
		db.setString(2, vendedor.getEmail());
		db.setDate(3, vendedor.getDataNasc() == null ? new Date() : vendedor.getDataNasc());
		db.setString(4, vendedor.getTelefone());
		db.setInt(5, vendedor.getUser().getId());
		db.setInt(6, vendedor.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Vendedor vendedor) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM vendedores "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1, vendedor.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Vendedor> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
			
		// Declara uma instrução SQL
		String sqlQuery = " SELECT c.id as 'vendedor_id', c.*, u.* \n"
				+ " FROM vendedores c \n"
				+ " INNER JOIN users u \n"
				+ " ON c.user_id = u.id;";
		
		db.createStatement();
	
		db.executeQuery(sqlQuery);

		while (db.next()) {
			User user = new User(db.getInt("user_id"));
			user.setName(db.getString("nome"));
			user.setGender(db.getString("sexo"));
			user.setEmail(db.getString("email"));
			
			Vendedor vendedor = new Vendedor(db.getInt("vendedor_id"));
			vendedor.setName(db.getString("nome"));
			vendedor.setEmail(db.getString("email"));
			vendedor.setDataNasc(db.getDate("dataNasc"));
			vendedor.setTelefone(db.getString("telefone"));
			vendedor.setUser(user);
			
			vendedores.add(vendedor);
		}
		
		return vendedores;
	}

	@Override
	public Vendedor findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM vendedores WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Vendedor v = null;
		while (db.next()) {
			v = new Vendedor(id);
			v.setName(db.getString("nome"));
			v.setEmail(db.getString("email"));
			v.setDataNasc(db.getDate("dataNasc"));
			v.setTelefone(db.getString("telefone"));
			
			UserDAO userDAO = DAOFactory.createDAO(UserDAO.class); 
			User user = userDAO.findById(db.getInt("user_id"));
			v.setUser(user);
			
			break;
		}
		
		return v;
	}
}
