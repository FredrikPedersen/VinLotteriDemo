package com.fredrikpedersen.experis_vin_lotteri.dtos;

import com.fredrikpedersen.experis_vin_lotteri.persistance.models.LotteryTicket;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Wine;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LotteryDto (@NonNull Long id,
                          @NonNull List<LotteryTicketDto> lotteryTickets,
                          @NonNull List<WineDto> wines,
                          @NonNull LocalDateTime startTime,
                          @NonNull LocalDateTime endTime) implements Dto {
}
