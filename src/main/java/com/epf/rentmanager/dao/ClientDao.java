package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Reservation;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, client.getName());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			ps.close();
			connection.close();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}else throw new DaoException("Erreur lors de la creation");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

        public long delete(Client client) throws DaoException {
			try {
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps =
						connection.prepareStatement(DELETE_CLIENT_QUERY);
				long id= client.getId();
				ps.setLong(1, id);
				ps.executeUpdate();
				ps.close();
				connection.close();
				return id;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

	public Client findById(long id) throws DaoException {

		return new Client();
	}

	public List<Client> findAll() throws DaoException {
		return new ArrayList<Client>();
	}

}
