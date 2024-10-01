package org.demo.amniltask.service.impl;

import org.demo.amniltask.configuration.CustomDetails;
import org.demo.amniltask.model.Admin;
import org.demo.amniltask.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {
            throw new UsernameNotFoundException(email + " not found");
        }

        return new CustomDetails(admin);
    }
}
