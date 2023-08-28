package com.jvb_intern.rental_acommodation.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tenant")

public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    @Column(name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String tenantEmail;

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

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<NotificationDating> notificationDating;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TenantDating> tenantDatings;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<LandlordDating> landlordDatings;

    @OneToOne(mappedBy = "tenant")
    private TenantCriteria tenantCriteria;
}
