package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.RegisterStore;
import com.daeun.reservation.backend.dto.StoreDto;
import com.daeun.reservation.backend.dto.StoreInfo;
import com.daeun.reservation.backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 상점 등록
    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public RegisterStore.Response registerStore(
            @RequestBody @Valid RegisterStore.Request storeRequest
    ) {
        StoreDto storeDto = storeService.registerStore(
                storeRequest.getName(),
                storeRequest.getLocation(),
                storeRequest.getDescription()
        );
        return RegisterStore.Response.from(storeDto);
    }

    // 상점 리스트 조회(기본, 가나다순)
    @GetMapping
    public List<StoreInfo> getStores(@RequestParam(required = false) Boolean isOrder) {
        List<StoreDto> storeDtos;
        if(isOrder == null) {
               storeDtos = storeService.getStores();
        }else {
               storeDtos = storeService.getStoresOrderByName();
        }

       return storeDtos.stream()
               .map(StoreInfo::from)
               .collect(Collectors.toList());
    }

    // 상점 상세조회
    @GetMapping("/{storeId}")
    public StoreInfo getStore() {
        // TODO
        return null;
    }
}
