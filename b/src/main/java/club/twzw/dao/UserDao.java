package club.twzw.dao;

import java.util.List;

import club.twzw.bean.User;

public interface UserDao {
	public List<User> findAll();
}