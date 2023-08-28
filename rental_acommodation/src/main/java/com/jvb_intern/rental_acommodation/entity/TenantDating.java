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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tenant_Dating")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TenantDating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datingId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "booking_time")
    private LocalTime bookingTime;

    @Column(name = "is_sending")
    private Boolean isSending;

    @Column(name = "confirm_status")
    private Boolean confirmStatus;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    @Column(name = "is_canceled_by_landlord")
    private Boolean isCanceledByLandlord;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @Column(name = "post_id")
    private Long postId;

}
