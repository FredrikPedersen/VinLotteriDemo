package com.fredrikpedersen.experis_vin_lotteri.dtos;

import lombok.*;
import org.springframework.lang.NonNull;
@Builder
public record BuyTicketDto(@NonNull String phonenumber,
                           @NonNull Long ticketId) implements Dto {

}
