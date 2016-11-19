package ont.paarma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ont.paarma.dao.UserDAO;
import ont.paarma.model.User;
@Service
public class UserService{
	
	@Autowired
	private UserDAO userDAO;

	public User add(User user) {
		return userDAO.addUser(user);
	}
	
	public User findById(int id){
		return userDAO.findById(id);
	}
}
