package ont.paarma.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ont.paarma.model.User;
@Service
public class UserService{

	public User add(User addedUser) {
		addedUser.setId(3);
		return addedUser;
	}
}
