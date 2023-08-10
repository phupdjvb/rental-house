package com.jvb_intern.rental_acommodation.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Admin")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(name = "email")
    private String admindEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(mappedBy = "admin")
    private List<PostDeletion> postDeletions;

}
