package com.fredrikpedersen.experis_vin_lotteri.persistance.repositories;

import com.fredrikpedersen.experis_vin_lotteri.persistance.models.PersistableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistableEntityRepository<T extends PersistableEntity> extends JpaRepository<T, Long> {
}
