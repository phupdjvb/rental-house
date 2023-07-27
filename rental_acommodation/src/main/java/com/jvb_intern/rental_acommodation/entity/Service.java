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
@Table(name = "Service")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "service")
    private List<AccommodateService> accommodateServices;
}
