package org.demo.amniltask.service.impl;

import org.demo.amniltask.model.Admin;
import org.demo.amniltask.repository.AdminRepository;
import org.demo.amniltask.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Admin findAdminByPhone(String phone) {
        return adminRepository.findByPhone(phone);
    }
}
