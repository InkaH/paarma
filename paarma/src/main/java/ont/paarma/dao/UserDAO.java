package ont.paarma.dao;

import org.springframework.stereotype.Repository;

import ont.paarma.model.User;

@Repository
public class UserDAO {
	
public User addUser(User user) {
		
		return new User(1, "firstName", "lastName");
	}
}
