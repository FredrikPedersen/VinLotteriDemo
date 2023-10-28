package com.fredrikpedersen.experis_vin_lotteri.dtos;

import lombok.*;
import org.springframework.lang.NonNull;
@Builder
public record BuyTicketDto(@NonNull String phonenumber,
                           @NonNull String username,
                           @NonNull Long ticketId) implements Dto {

}
