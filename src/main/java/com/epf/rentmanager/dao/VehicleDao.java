package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places,modele) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places ,modele FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places,modele FROM Vehicle;";
	private static final String COUNT_VEHICULES_QUERY="SELECT COUNT(*) FROM Vehicle;";
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

		){
			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getSeats());
			ps.setString(3, vehicle.getModele());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				long idCreer= resultSet.getLong(1);
				vehicle.setId(idCreer);
				return idCreer;
			}else throw new DaoException("Erreur lors de la creation du vehicule");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(DELETE_VEHICLE_QUERY);){
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }

	public Vehicle findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(FIND_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt(1);
				String constructeur = resultSet.getString(2);
				int nbDeplace = resultSet.getInt(3);
				String modele = resultSet.getString(4);
				return new Vehicle(id, constructeur, nbDeplace,modele);
			} else throw new DaoException("Erreur lors de la recherche");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> lVehicule= new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet resultSet = ps.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String constructeur = resultSet.getString(2);
				int nbDeplace = resultSet.getInt(3);
				String modele = resultSet.getString(4);
				lVehicule.add(new Vehicle( id, constructeur, nbDeplace, modele));
			}
			return lVehicule;
		} catch (SQLException e) {
			throw new RuntimeException(e);
	}
	}
	public int countVehiculeDao() throws DaoException {
		int countVehicule=0;
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_VEHICULES_QUERY);
			 ResultSet resultSet = ps.executeQuery()) {
			if(resultSet.next()){
				countVehicule=resultSet.getInt(1);
				return countVehicule;
			}else throw new DaoException("Erreur lors de la methode countVehicule");

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}
