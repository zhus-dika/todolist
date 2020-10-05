package com.todolist.webapp.service.impl;

import com.todolist.webapp.service.TodoitemService;
import com.todolist.webapp.domain.Todoitem;
import com.todolist.webapp.repository.TodoitemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Todoitem}.
 */
@Service
@Transactional
public class TodoitemServiceImpl implements TodoitemService {

    private final Logger log = LoggerFactory.getLogger(TodoitemServiceImpl.class);

    private final TodoitemRepository todoitemRepository;

    public TodoitemServiceImpl(TodoitemRepository todoitemRepository) {
        this.todoitemRepository = todoitemRepository;
    }

    @Override
    public Todoitem save(Todoitem todoitem) {
        log.debug("Request to save Todoitem : {}", todoitem);
        return todoitemRepository.save(todoitem);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Todoitem> findAll(Pageable pageable) {
        log.debug("Request to get all Todoitems");
        return todoitemRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Todoitem> findOne(Long id) {
        log.debug("Request to get Todoitem : {}", id);
        return todoitemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Todoitem : {}", id);
        todoitemRepository.deleteById(id);
    }
}
