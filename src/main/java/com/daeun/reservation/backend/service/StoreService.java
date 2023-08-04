package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.StoreDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.StoreRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 상점등록
    @Transactional
    public StoreDto registerStore(String name, String location, String description) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User findUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new StoreReservationException(ErrorCode.USER_NOT_FOUND));

        Store store = Store.builder()
                .user(findUser)
                .name(name)
                .location(location)
                .description(description)
                .build();

        // 상점 등록
        return StoreDto.fromEntity(storeRepository.save(store));
    }

    // 상점 리스트 조회(기본)
    public List<StoreDto> getStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 상점 리스트 조회(가나다순)
    public List<StoreDto> getStoresOrderByName() {
        return storeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 상점 상세조회
    
}
