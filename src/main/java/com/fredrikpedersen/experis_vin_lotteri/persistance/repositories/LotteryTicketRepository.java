package com.fredrikpedersen.experis_vin_lotteri.persistance.repositories;

import com.fredrikpedersen.experis_vin_lotteri.persistance.models.LotteryTicket;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryTicketRepository extends PersistableEntityRepository <LotteryTicket> {
}
