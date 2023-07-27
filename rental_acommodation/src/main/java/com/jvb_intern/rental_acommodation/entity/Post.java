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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Post")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "accommodate_id")
    private Long accommodateId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "photo")
    private String photo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToOne(mappedBy = "post")
    private PostDeletion postDeletion;

    @OneToOne(mappedBy = "post")
    private Accommodate accommodate;

    @OneToOne(mappedBy = "post")
    private Notification notification;

}
