package com.todolist.webapp.service;

import com.todolist.webapp.domain.Todoitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Todoitem}.
 */
public interface TodoitemService {

    /**
     * Save a todoitem.
     *
     * @param todoitem the entity to save.
     * @return the persisted entity.
     */
    Todoitem save(Todoitem todoitem);

    /**
     * Get all the todoitems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Todoitem> findAll(Pageable pageable);

    Page<Todoitem> findAllByCurrentUser(Pageable pageable);


    /**
     * Get the "id" todoitem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Todoitem> findOne(Long id);

    /**
     * Delete the "id" todoitem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
