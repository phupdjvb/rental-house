package com.jvb_intern.rental_acommodation.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "Landlord")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landlordId;

    @Column(name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String landlordEmail;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "varification_code")
    private String varificationCode;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<TenantDating> tenantDatings;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<LandlordDating> landlordDatings;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL)
    private List<Accommodate> accommodates;
}
