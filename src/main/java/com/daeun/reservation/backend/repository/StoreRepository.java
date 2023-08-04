package com.daeun.reservation.backend.repository;

import com.daeun.reservation.backend.domain.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findAll(Sort sort);
}
