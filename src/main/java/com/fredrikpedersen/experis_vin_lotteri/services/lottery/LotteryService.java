package com.fredrikpedersen.experis_vin_lotteri.services.lottery;

import com.fredrikpedersen.experis_vin_lotteri.dtos.BuyTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.services.CrudService;

import java.util.List;

public interface LotteryService extends CrudService<LotteryDto> {

    List<LotteryDto> findAllNonExpiredLotteries();
    List<LotteryTicketDto> findAllAvailableTicketForLottery(Long lotteryId);
    LotteryTicketDto buyTicket(BuyTicketDto buyTicketDto);
}
