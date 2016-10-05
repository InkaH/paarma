package ont.paarma.service;

import ont.paarma.model.User;

public class UserServiceImpl implements UserService{

	@Override
	public User add(User addedUser) {
		addedUser.setId(1);
		return addedUser;
	}
}
