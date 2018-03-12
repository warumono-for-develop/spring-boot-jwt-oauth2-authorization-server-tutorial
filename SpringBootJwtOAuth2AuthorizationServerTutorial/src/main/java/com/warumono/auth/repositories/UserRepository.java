package com.warumono.auth.repositories;

import com.warumono.auth.entities.User;

public interface UserRepository extends AbstractRepository<User>
{
	User findByUsername(String username);
}
