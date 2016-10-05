package ont.paarma.service;

import ont.paarma.model.User;

public class UserServiceImpl implements UserService{

	@Override
	public User add(User addedUser) {
		addedUser.setId(3);
		return addedUser;
	}
}
