package net.slipp.dao.users;

import net.slipp.domain.users.User;

public interface UserDao {

	User findById(String userId);

	void create(User user);

	void update(User user);

}