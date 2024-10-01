package org.demo.amniltask.model;

import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name="admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fname;
    private String lname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    private String password;
    private String confirmPassword;

    private String role;
}
