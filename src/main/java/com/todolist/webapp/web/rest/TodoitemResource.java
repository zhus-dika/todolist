package com.todolist.webapp.web.rest;

import com.todolist.webapp.domain.Todoitem;
import com.todolist.webapp.service.TodoitemService;
import com.todolist.webapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.todolist.webapp.domain.Todoitem}.
 */
@RestController
@RequestMapping("/api")
public class TodoitemResource {

    private final Logger log = LoggerFactory.getLogger(TodoitemResource.class);

    private static final String ENTITY_NAME = "todoitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TodoitemService todoitemService;

    public TodoitemResource(TodoitemService todoitemService) {
        this.todoitemService = todoitemService;
    }

    /**
     * {@code POST  /todoitems} : Create a new todoitem.
     *
     * @param todoitem the todoitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new todoitem, or with status {@code 400 (Bad Request)} if the todoitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/todoitems")
    public ResponseEntity<Todoitem> createTodoitem(@Valid @RequestBody Todoitem todoitem) throws URISyntaxException {
        log.debug("REST request to save Todoitem : {}", todoitem);
        if (todoitem.getId() != null) {
            throw new BadRequestAlertException("A new todoitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Todoitem result = todoitemService.save(todoitem);
        return ResponseEntity.created(new URI("/api/todoitems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /todoitems} : Updates an existing todoitem.
     *
     * @param todoitem the todoitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated todoitem,
     * or with status {@code 400 (Bad Request)} if the todoitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the todoitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/todoitems")
    public ResponseEntity<Todoitem> updateTodoitem(@Valid @RequestBody Todoitem todoitem) throws URISyntaxException {
        log.debug("REST request to update Todoitem : {}", todoitem);
        if (todoitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Todoitem result = todoitemService.save(todoitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, todoitem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /todoitems} : get all the todoitems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of todoitems in body.
     */
    @GetMapping("/todoitems")
    public ResponseEntity<List<Todoitem>> getAllTodoitems(Pageable pageable) {
        log.debug("REST request to get a page of Todoitems");
        Page<Todoitem> page = todoitemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /todoitems/:id} : get the "id" todoitem.
     *
     * @param id the id of the todoitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the todoitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/todoitems/{id}")
    public ResponseEntity<Todoitem> getTodoitem(@PathVariable Long id) {
        log.debug("REST request to get Todoitem : {}", id);
        Optional<Todoitem> todoitem = todoitemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(todoitem);
    }

    /**
     * {@code DELETE  /todoitems/:id} : delete the "id" todoitem.
     *
     * @param id the id of the todoitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/todoitems/{id}")
    public ResponseEntity<Void> deleteTodoitem(@PathVariable Long id) {
        log.debug("REST request to delete Todoitem : {}", id);
        todoitemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
