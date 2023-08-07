package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    private String managerName;
    private Long storeId;
    private String storeName;
    private String location;
    private String description;

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .managerName(store.getUser().getUsername())
                .storeId(store.getStoreId())
                .storeName(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }

}
