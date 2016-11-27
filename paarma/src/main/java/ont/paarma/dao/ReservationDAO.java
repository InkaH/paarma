package ont.paarma.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
		System.out.println("reservation in DAO add method: " + reservation.toString());
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);	        
		KeyHolder idHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO reservations (userId, startDate, endDate, numPeriods, tablenum, tablePrice) "
				+ "VALUES (:userId, :startDateSql, :endDateSql, :numPeriods, :table, :tablePrice)";
		namedParameterJdbcTemplate.update(
				sql, parameters, idHolder);

		int generatedId = (idHolder.getKey().intValue());
		reservation.setId(generatedId);
		return reservation;
	}

	public Reservation findById(int id) {
		Reservation result = new Reservation();	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String sql = "SELECT * FROM reservations WHERE id=:id";
		try{
			result = namedParameterJdbcTemplate.queryForObject(
					sql,params, new ReservationMapper());	
			return result;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Reservation> findAll(int id) {
		SqlParameterSource params = new MapSqlParameterSource("id", Integer.valueOf(id));
		String sql = "SELECT * FROM reservations WHERE userId=:id";
		ReservationMapper mapper = new ReservationMapper();
		List<Reservation> reservations = new ArrayList<Reservation>();
		try{
			reservations  = namedParameterJdbcTemplate.query(sql,
					params, mapper);	
			return reservations;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Reservation updateReservation(Reservation reservation) {
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
		String sql = "UPDATE reservations SET tablenum=:table, startDate=:startDateSql, "
				+ "endDate=:endDateSql, numPeriods=:numPeriods"
				+ " WHERE id=:id";
		namedParameterJdbcTemplate.update(
				sql, parameters);
		return reservation;
	}

	public void deleteReservation(int id){
		SqlParameterSource params = new MapSqlParameterSource("id", Integer.valueOf(id));
		String sql = "DELETE FROM reservations WHERE id=:id";
		namedParameterJdbcTemplate.update(sql, params);
	}

	private static final class ReservationMapper implements RowMapper<Reservation> {

		public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation reservation = new Reservation();
			reservation.setId(rs.getInt("id"));
			reservation.setUserId(rs.getInt("userId"));
			reservation.setStartDateFromSql(rs.getDate("startDate"));
			reservation.setEndDateFromSql(rs.getDate("endDate"));
			reservation.setNumPeriods(rs.getInt("numPeriods"));
			reservation.setTable(rs.getString("tablenum"));
			reservation.setTablePrice(rs.getBigDecimal("tablePrice"));
			reservation.setTotalPrice(rs.getBigDecimal("tablePrice"));
			return reservation;
		}
	}	
}
