package com.fredrikpedersen.experis_vin_lotteri.persistance.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "lotteries")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Lottery extends PersistableEntity {

    @NonNull
    @OneToMany(mappedBy = "lottery")
    private List<LotteryTicket> lotteryTickets;

    @NonNull
    @OneToMany(mappedBy = "lottery")
    private List<Wine> wines;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;
}
