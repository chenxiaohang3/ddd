package club.twzw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import club.twzw.bean.User;
import club.twzw.service.UserService;

@Service("UserService")					
public class UserServiceImpl implements UserService {
    
    public List<User> findAll() {
        System.out.println("查询所有用户。。。");
        return null;//service.findAll();
    }
}
