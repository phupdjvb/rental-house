package com.jvb_intern.rental_acommodation.entity;


import javax.persistence.CascadeType;
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

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "area")
    private String area;

    @Column(name = "square")
    private Double square;

    @Column(name = "room_status")
    private String roomStatus;

    @Column(name = "price_category")
    private String priceCategory;

    @Column(name = "parking")
    private String parking;

    @Column(name = "wifi")
    private String wifi; // có hoặc không

    @Column(name = "conditioner")
    private String conditioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;
}