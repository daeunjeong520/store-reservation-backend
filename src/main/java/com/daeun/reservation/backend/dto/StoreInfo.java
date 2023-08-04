package com.daeun.reservation.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreInfo {

    private Long storeId;
    private String managerName;
    private String name;
    private String location;
    private String description;

    public static StoreInfo from(StoreDto storeDto) {
        return StoreInfo.builder()
                .storeId(storeDto.getStoreId())
                .managerName(storeDto.getManagerName())
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .build();
    }
}
