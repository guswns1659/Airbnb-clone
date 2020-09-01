package com.titanic.airbnbclone.domain.accommodation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Picture {

    @Id
    @GeneratedValue
    @Column(name = "picture_id")
    private Long id;

    @Lob
    @Column(name = "picture_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    @JsonIgnore
    private Accommodation accommodation;
}
