package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_ID_QUERY = "SELECT id, client_id,vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
		) {

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDateDebut()));
			ps.setDate(4, Date.valueOf(reservation.getDateFin()));

			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				long idCreer = resultSet.getLong(1);
				return idCreer;
			} else throw new DaoException("Erreur lors de la creation de la réservation");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(DELETE_RESERVATION_QUERY);) {
			int id= reservation.getId();
			ps.setInt(1, id);
			ps.executeUpdate();
			return id;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Reservation findById(int id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(FIND_RESERVATIONS_BY_ID_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			ps.setLong(1, id);
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
				int client_id = resultSet.getInt(2);
				int vehicle_id = resultSet.getInt(3);
				LocalDate debut = LocalDate.parse(resultSet.getString(4));
				LocalDate fin = LocalDate.parse(resultSet.getString(4));
				return new Reservation(id, client_id, vehicle_id, debut, fin);
			} else throw new DaoException("Erreur lors de la recherche de la reservatio par ID");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			ps.setLong(1, clientId);
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			List<Reservation> lReservation= new ArrayList<>();
			do {
				int id = resultSet.getInt(1);
				int vehicle_id = resultSet.getInt(2);
				LocalDate debut = LocalDate.parse(resultSet.getString(3));
				LocalDate fin = LocalDate.parse(resultSet.getString(4));
				lReservation.add(new Reservation(id, (int) clientId, vehicle_id, debut, fin));
			} while(resultSet.next());
			return lReservation;
			} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservatio par ID");
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps =
					 connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			ps.setLong(1, vehicleId);
			ps.executeQuery();
			ResultSet resultSet = ps.getGeneratedKeys();
			List<Reservation> lReservation= new ArrayList<>();
			do {
				int id = resultSet.getInt(1);
				int clientId = resultSet.getInt(2);
				LocalDate debut = LocalDate.parse(resultSet.getString(3));
				LocalDate fin = LocalDate.parse(resultSet.getString(4));
				lReservation.add(new Reservation(id, clientId,(int) vehicleId, debut, fin));
			} while(resultSet.next());
			return lReservation;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservatio par ID");
		}
	}

	public List<Reservation> findAll() throws DaoException {
		return new ArrayList<Reservation>();
	}
}

