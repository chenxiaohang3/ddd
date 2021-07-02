package club.twzw.service;

import org.springframework.stereotype.Service;

import club.twzw.bean.User;

import java.util.List;

public interface UserService {
	public List<User> findAll();

}
