package com.fredrikpedersen.experis_vin_lotteri.services;

import com.fredrikpedersen.experis_vin_lotteri.dtos.Dto;
import com.fredrikpedersen.experis_vin_lotteri.exceptions.NoSuchPersistedEntityException;
import com.fredrikpedersen.experis_vin_lotteri.mappers.DtoMapper;
import com.fredrikpedersen.experis_vin_lotteri.persistance.models.PersistableEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
abstract class CrudServiceTest<D extends Dto, E extends PersistableEntity, S extends CrudServiceImpl<D, E>> {

    protected final JpaRepository<E, Long> repository;
    protected final DtoMapper<D, E> mapper;

    protected CrudServiceTest(final DtoMapper<D, E> mapper, final JpaRepository<E, Long> repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected void findByIdReturnsFoundEntity(final E expectedPersistedEntity, final D mappedDto, final S classUnderTest) {
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(expectedPersistedEntity));
        when(mapper.toDto(expectedPersistedEntity)).thenReturn(mappedDto);

        final D actualResult = classUnderTest.findById(id);

        assertEquals(mappedDto, actualResult);
        verify(repository).findById(id);
        verify(mapper).toDto(expectedPersistedEntity);
    }

    protected void findByIdEntityNotFoundThrowsException(final S classUnderTest) {
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchPersistedEntityException.class, () -> classUnderTest.findById(id));
        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }

    protected void saveEntityIsSaved(final E expectedPersistedEntity, final D toBeSavedDTO, final S classUnderTest) {
        when(mapper.toEntity(toBeSavedDTO)).thenReturn(expectedPersistedEntity);
        setupSaveAndReturnDto(toBeSavedDTO, expectedPersistedEntity);

        final D actualResult = classUnderTest.save(toBeSavedDTO);

        assertEquals(toBeSavedDTO, actualResult);
        verify(mapper).toEntity(toBeSavedDTO);
        verifySaveAndReturnDto(expectedPersistedEntity);
    }

    protected void deleteByIdEntityNotFound(final S classUnderTest) {
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchPersistedEntityException.class, () -> classUnderTest.deleteById(id));
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    protected void deleteByIdEntityFound(final S crudService, final E persistableEntity) {
        final Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(persistableEntity));

        crudService.deleteById(id);

        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    protected void setupSaveAndReturnDto(final D dto, final E entity) {
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);
    }

    protected void verifySaveAndReturnDto(final E entity) {
        verify(repository).save(entity);
        verify(mapper).toDto(entity);
    }
}