package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

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
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);){

			ps.setString(1, client.getName());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long idCreer= resultSet.getLong(1);
				client.setId(idCreer);
				System.out.println(idCreer);
				return idCreer;
			}else throw new DaoException("Erreur lors de la creation du Client");
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
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, id);
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			ps.close();
			connection.close();
			if (resultSet.next()) {
				String name = resultSet.getString(1);
				String prenom = resultSet.getString(2);
				String email = resultSet.getString(3);
				LocalDate naissance = LocalDate.parse(resultSet.getString(4),formatter);
				return new Client(id, name, prenom, email, naissance);
			} else throw new DaoException("Erreur lors de la recherche");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

        public List<Client> findAll() throws DaoException {
			List<Client> lClient= new ArrayList<>();
			try (Connection connection = ConnectionManager.getConnection();
				 PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
				 ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong(1);
					String name = resultSet.getString(2);
					String prenom = resultSet.getString(3);
					String email = resultSet.getString(4);
                    LocalDate naissance = LocalDate.parse( resultSet.getString(5),formatter);
					Client client=new Client(id, name, prenom, email, naissance);
					lClient.add(client);
                }
                return lClient;
            } catch (SQLException e) {
				throw new RuntimeException(e);
			}

	}

}
