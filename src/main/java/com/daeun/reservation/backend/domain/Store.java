package com.daeun.reservation.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 매장명

    @Column(name = "location", nullable = false)
    private String location; // 상점위치

    @Column(name = "description", nullable = false, length = 500)
    private String description; // 상점설명

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
