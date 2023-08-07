package com.daeun.reservation.backend.domain;

import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "time_table_id")
    private TimeTable timeTable;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus reservationStatus; // 예약 상태
}