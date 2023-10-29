package com.fredrikpedersen.experis_vin_lotteri.web;

import com.fredrikpedersen.experis_vin_lotteri.dtos.BuyTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.CreateLotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryDto;
import com.fredrikpedersen.experis_vin_lotteri.dtos.LotteryTicketDto;
import com.fredrikpedersen.experis_vin_lotteri.services.lottery.LotteryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LotteryDto> getNonExpiredLotteries() {
        return lotteryService.findAllNonExpiredLotteries();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LotteryDto createNew(@RequestBody final CreateLotteryDto createLotteryDto) {
        return lotteryService.createNew(createLotteryDto);
    }

    @PostMapping("/closeLottery/{lotteryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<LotteryTicketDto> closeLottery(@PathVariable final Long lotteryId) {
        return lotteryService.closeLottery(lotteryId);
    }

    @GetMapping("/tickets/{lotteryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<LotteryTicketDto> getAvailableTicketsForLottery(@PathVariable final Long lotteryId) {
        return lotteryService.findAllAvailableTicketForLottery(lotteryId);
    }

    @PostMapping("/tickets/buy/")
    @ResponseStatus(HttpStatus.CREATED)
    public LotteryTicketDto buyTicket(@RequestBody final BuyTicketDto buyTicketDto) {
        return lotteryService.buyTicket(buyTicketDto);
    }
}