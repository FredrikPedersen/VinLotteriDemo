package com.fredrikpedersen.experis_vin_lotteri.dtos;

import lombok.*;
import org.springframework.lang.NonNull;
@Builder
public record LotteryTicketDto (@NonNull Integer ticketNumber, String soldToUsername) implements Dto { }
