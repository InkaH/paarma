package ont.paarma.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ont.paarma.model.Reservation;
import ont.paarma.model.User;

@Repository
public class ReservationDAO {
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public Reservation addReservation(Reservation reservation) {
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);	        
		KeyHolder idHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO reservations (userId, startDate, numPeriods, tablenum, tablePrice) "
				+ "VALUES (:userId, :startDate, :numPeriods, :table, :tablePrice)";
		namedParameterJdbcTemplate.update(
				sql, parameters, idHolder);

		int generatedId = (idHolder.getKey().intValue());
		reservation.setId(generatedId);
		reservation.setTotalPrice();
		return reservation;
	}

	public Reservation findById(int id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM reservations WHERE id=:id";
		Reservation result = namedParameterJdbcTemplate.queryForObject(
				sql,params, new ReservationMapper());	
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Reservation> findAll(int id) {
		SqlParameterSource params = new MapSqlParameterSource("id", Integer.valueOf(id));
		System.out.println("hakuid "+id);
		String sql = "SELECT * FROM reservations WHERE userId=:id";
		ReservationMapper mapper = new ReservationMapper();
		List<Reservation> reservations  = namedParameterJdbcTemplate.query(sql,
				params, mapper);	
		return reservations;
	}

	public Reservation updateReservation(Reservation reservation) {
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
		String sql = "UPDATE reservations SET firstName=:firstName, lastName=:lastName"
				+ " WHERE id=:id";

		namedParameterJdbcTemplate.update(
				sql, parameters);
		return reservation;
	}

	public void deleteReservation(int id){
		
	}

	private static final class ReservationMapper implements RowMapper<Reservation> {

		public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation reservation = new Reservation();
			reservation.setId(rs.getInt("id"));
			reservation.setStartDate(rs.getString("startDate"));
			reservation.setNumPeriods(rs.getInt("numPeriods"));
			reservation.setTable(rs.getInt("tablenum"));
			reservation.setTablePrice(rs.getBigDecimal("tablePrice"));
			return reservation;
		}
	}	
}
