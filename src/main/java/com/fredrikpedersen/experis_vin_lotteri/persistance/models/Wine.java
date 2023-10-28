package com.fredrikpedersen.experis_vin_lotteri.persistance.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "wines")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Wine extends PersistableEntity {

    @NonNull
    @ManyToOne
    @JoinColumn(name = "lottery_id", nullable = false)
    private Lottery lottery;

    @NonNull
    private String name;

    @NonNull
    private Integer price;
}
