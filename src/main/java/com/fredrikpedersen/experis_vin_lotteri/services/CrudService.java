package com.fredrikpedersen.experis_vin_lotteri.services;


import com.fredrikpedersen.experis_vin_lotteri.dtos.Dto;

import java.util.List;

public interface CrudService<T extends Dto> {

    List<T> findAll();
    T findById(Long id);
    T save(T dtoObject);
    T update(T dtoObject, Long id);
    void deleteById(Long id);
}
