package org.demo.amniltask.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="student")

@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String address;
}
