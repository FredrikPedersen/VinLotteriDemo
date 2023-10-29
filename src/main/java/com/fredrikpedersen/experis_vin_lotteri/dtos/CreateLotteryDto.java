package com.fredrikpedersen.experis_vin_lotteri.dtos;

import lombok.Builder;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CreateLotteryDto(@NonNull LocalDateTime startTime,
                               @NonNull LocalDateTime endTime,
                               @NonNull List<WineDto> wines) implements Dto {
}
