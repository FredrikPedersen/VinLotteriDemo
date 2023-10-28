package com.fredrikpedersen.experis_vin_lotteri.services;

import com.fredrikpedersen.experis_vin_lotteri.dtos.Dto;
import com.fredrikpedersen.experis_vin_lotteri.exceptions.NoSuchPersistedEntityException;
import com.fredrikpedersen.experis_vin_lotteri.mappers.DtoMapper;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.PersistableEntity;
import com.fredrikpedersen.experis_vin_lotteri.persistance.repositories.PersistableEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class CrudServiceImpl<T extends Dto, R extends PersistableEntity> implements CrudService<T> {

    protected final DtoMapper<T, R> dtoMapper;
    protected final PersistableEntityRepository<R> repository;

    @Override
    public List<T> findAll() {
        return repository.findAll().stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public T findById(final Long id) {
        return repository.findById(id)
                .map(dtoMapper::toDto)
                .orElseThrow(() ->
                        new NoSuchPersistedEntityException(String.format("Could not find persisted entity with ID %s", id))
                );
    }

    @Override
    @Transactional
    public T save(final T dtoObject) {
        return saveAndReturnDto(dtoMapper.toEntity(dtoObject));
    }

    @Override
    @Transactional
    public T update(final T dtoObject, final Long id) {
        return repository.findById(id)
                .map(persistableEntity -> {
                    final R mappedEntity = dtoMapper.toEntity(dtoObject);
                    mappedEntity.setId(persistableEntity.getId());
                    mappedEntity.setCreatedDate(persistableEntity.getCreatedDate());
                    return saveAndReturnDto(mappedEntity);
                }).orElseThrow(() ->
                        new NoSuchPersistedEntityException(String.format("Could not find persisted entity for %s with ID %s",
                                dtoObject.getClass().getSimpleName(), id))
                );
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        repository.findById(id).orElseThrow(() ->
                new NoSuchPersistedEntityException(String.format("Could not find persisted entity with ID %s", id))
        );

        repository.deleteById(id);
    }

    private T saveAndReturnDto(final R persistableEntity) {
        final R savedEntity = repository.save(persistableEntity);
        return dtoMapper.toDto(savedEntity);
    }
}