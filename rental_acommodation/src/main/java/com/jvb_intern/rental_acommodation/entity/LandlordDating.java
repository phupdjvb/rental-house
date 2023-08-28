package com.jvb_intern.rental_acommodation.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Landlord_Dating")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class LandlordDating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datingId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "is_received")
    private Boolean isReceived;

    @Column(name = "confirm_status")
    private Boolean confirmStatus;

    @Column(name = "is_canceled_by_landlord")
    private Boolean isCanceledByLandlord;

    @Column(name = "is_canceled_by_tenant")
    private Boolean isCanceledByTenant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "post_id")
    private Long postId;
}
