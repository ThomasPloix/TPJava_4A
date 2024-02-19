package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			ps.close();
			connection.close();
			if (resultSet.next()) {
				long idCreer= resultSet.getLong(1);
				return idCreer;
			}else throw new DaoException("Erreur lors de la creation du vehicule");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_VEHICLE_QUERY);
			long id= vehicle.getId();
			ps.setLong(1, id);
			ps.executeUpdate();
			ps.close();
			connection.close();
			return id;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Vehicle findById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, id);
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			ps.close();
			connection.close();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
				String constructeur = resultSet.getString(2);
				int nbDeplace = resultSet.getInt(3);
				return new Vehicle(id,constructeur, nbDeplace);
			} else throw new DaoException("Erreur lors de la recherche");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(FIND_VEHICLES_QUERY, Statement.RETURN_GENERATED_KEYS);

			 ResultSet resultSet = ps.getGeneratedKeys();){
			ps.executeQuery();
			List<Vehicle> lVehicule= new ArrayList<>();
			do {
				int id = resultSet.getInt(1);
				String constructeur = resultSet.getString(2);
				int nbDeplace = resultSet.getInt(3);
				lVehicule.add(new Vehicle(id, constructeur, nbDeplace));
			} while (resultSet.next());
			return lVehicule;
		} catch (SQLException e) {
			throw new RuntimeException(e);
	}
	}

}
