package com.fredrikpedersen.experis_vin_lotteri.web;

import com.fredrikpedersen.experis_vin_lotteri.dtos.BuyTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.services.lottery.LotteryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping
    public List<LotteryDto> getNonExpiredLotteries() {
        return lotteryService.findAllNonExpiredLotteries();
    }

    @GetMapping("/tickets/{lotteryId}")
    public List<LotteryTicketDto> getAvailableTicketsForLottery(@PathVariable final Long lotteryId) {
        return lotteryService.findAllAvailableTicketForLottery(lotteryId);
    }

    @PostMapping("/tickets/buy/")
    public LotteryTicketDto buyTicket(@RequestBody final BuyTicketDto buyTicketDto) {
        return lotteryService.buyTicket(buyTicketDto);
    }
}
