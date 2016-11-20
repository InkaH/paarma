package ont.paarma.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ont.paarma.model.User;

@Repository
public class UserDAO {

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public User addUser(User user) {
		
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);	        
		KeyHolder idHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO users (firstname, lastname) VALUES (:firstName, :lastName)";

		namedParameterJdbcTemplate.update(
				sql, parameters, idHolder);
		
		int generatedId = (idHolder.getKey().intValue());
		user.setId(generatedId);
		return user;
	}

	public User findById(int id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		String sql = "SELECT * FROM users WHERE id=:id";
		User result = namedParameterJdbcTemplate.queryForObject(
				sql,params, new UserMapper());	
		System.out.println(result);
		return result;
	}
	
	public User updateUser(User user) {
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
		String sql = "UPDATE users SET firstName=:firstName, lastName=:lastName"
				+ " WHERE id=:id";

		namedParameterJdbcTemplate.update(
				sql, parameters);
		return user;
	}
	
	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setFirstName(rs.getString("firstname"));
			user.setLastName(rs.getString("lastname"));
			return user;
		}
	}

//	public List<User> findAll() {
//
//		Map<String, Object> params = new HashMap<String, Object>();
//		String sql = "SELECT * FROM users";
//		List<User> result = namedParameterJdbcTemplate.query(sql, params, new UserMapper());
//		return result;
//
//	}
}