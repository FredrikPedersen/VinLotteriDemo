package com.fredrikpedersen.experis_vin_lotteri.mappers;

public interface DtoMapper<DTO, ENTITY> {

    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
