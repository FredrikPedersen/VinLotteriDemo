package com.fredrikpedersen.experis_vin_lotteri.dtos;

import lombok.*;
import org.springframework.lang.NonNull;

@Builder
public record WineDto(@NonNull String name, @NonNull Integer price) implements Dto { }
