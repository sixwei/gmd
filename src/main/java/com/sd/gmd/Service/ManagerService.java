package com.sd.gmd.Service;


import com.sd.gmd.domain.Brand;
import com.sd.gmd.domain.Products;
import com.sd.gmd.domain.Users;
import com.sd.gmd.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public List<Users> getUsers(){
        return managerRepository.getAllUser();
    }

    public List<Brand> getBrands(){
        return managerRepository.getAllBrand();
    }
}
