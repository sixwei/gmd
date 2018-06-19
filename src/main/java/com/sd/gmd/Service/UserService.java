package com.sd.gmd.Service;


import com.sd.gmd.domain.Users;
import com.sd.gmd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public boolean checkUser(Users user){

        String username = user.getUsername();
        Integer rid = user.getRid();

        List<Users> users = userRepository.checkUser(username,rid);
        if (users.size()==0 || users==null){

            return false;
        }
        else{
            Users amiuser = users.get(0);
            if (user.getPassword().equals(amiuser.getPassword())){
                return true;
            }else
                return false;
        }


    }

    public boolean addUser(Users user){

        long l = System.currentTimeMillis();
        Random random = new Random(l);
        user.setUid(random.nextInt());
        Integer result = userRepository.insertUser(user);
        if (result==null){
            return false;
        }
        else
            return true;
    }

    //得到登录用户的ID
    public int getUserId(String username){
        Users user = userRepository.getUserId(username);
        return user.getUid();

    }





}
