package com.jvb_intern.rental_acommodation.entity;


import  javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Accommodate")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Accommodate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accommodateId;

    @Column(name = "room_price")
    private Double roomPrice;

    @Column(name = "water_price")
    private Double waterPrice;

    @Column(name = "electric_price")
    private Double electricPrice;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "area")
    private Double area;

    @Column(name = "size")
    private Integer size;

    @Column(name = "room_status")
    private Boolean roomStatus;

    @Column(name = "price_category")
    private String priceCategory;

    @Column(name = "with_host")
    private String withHost;

    @Column(name = "parking")
    private boolean parking;

    @Column(name = "wifi")
    private boolean wifi;

    @Column(name = "conditioner")
    private boolean conditioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}