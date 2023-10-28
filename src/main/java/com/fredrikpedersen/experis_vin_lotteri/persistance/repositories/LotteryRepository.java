package com.fredrikpedersen.experis_vin_lotteri.persistance.repositories;

import com.fredrikpedersen.experis_vin_lotteri.persistance.models.Lottery;
import org.springframework.stereotype.Repository;

@Repository
public interface LotteryRepository extends PersistableEntityRepository<Lottery> {
}
