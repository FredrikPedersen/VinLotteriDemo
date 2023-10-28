package com.fredrikpedersen.experis_vin_lotteri.mappers;

import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Lottery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotteryMapper extends DtoMapper<LotteryDto, Lottery> {
}
