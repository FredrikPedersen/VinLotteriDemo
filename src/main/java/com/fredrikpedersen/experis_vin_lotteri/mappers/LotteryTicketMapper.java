package com.fredrikpedersen.experis_vin_lotteri.mappers;

import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.LotteryTicket;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LotteryTicketMapper extends DtoMapper<LotteryTicketDto, LotteryTicket> {
}
