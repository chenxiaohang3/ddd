package club.twzw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import club.twzw.bean.User;
import club.twzw.service.UserService;

@Service("UserService")					
public class UserServiceImpl implements UserService {
    
    public List<User> findAll() {
        System.out.println("��ѯ�����û�������");
        return null;//service.findAll();
    }
}