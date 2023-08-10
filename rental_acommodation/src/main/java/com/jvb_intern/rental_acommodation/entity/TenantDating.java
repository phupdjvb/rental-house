package com.jvb_intern.rental_acommodation.entity;

import java.time.LocalDate;

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
@Table(name = "Tenant_Dating")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class TenantDating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datingId;

    @Column(name = "dating_time")
    private LocalDate datingTime;

    @Column(name = "is_sending")
    private Boolean isSending;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

}
