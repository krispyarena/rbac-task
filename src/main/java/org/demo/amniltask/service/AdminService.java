package org.demo.amniltask.service;

import org.demo.amniltask.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    public void saveAdmin(Admin admin);

    public Admin findAdminByEmail(String email);

    public Admin findAdminByPhone(String phone);
}
