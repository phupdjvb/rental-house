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
@Table(name = "Notification_Dating")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class NotificationDating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiDatingId;

    @Column(name = "notification_name")
    private String notificationName;

    @Column(name = "dating_id")
    private Long datingId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

}
