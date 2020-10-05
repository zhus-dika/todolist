package com.todolist.webapp.web.rest;

import com.todolist.webapp.TodolistApp;
import com.todolist.webapp.domain.Todoitem;
import com.todolist.webapp.domain.User;
import com.todolist.webapp.repository.TodoitemRepository;
import com.todolist.webapp.service.TodoitemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.todolist.webapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TodoitemResource} REST controller.
 */
@SpringBootTest(classes = TodolistApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TodoitemResourceIT {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TASK = "AAAAAAAAAA";
    private static final String UPDATED_TASK = "BBBBBBBBBB";

    @Autowired
    private TodoitemRepository todoitemRepository;

    @Autowired
    private TodoitemService todoitemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTodoitemMockMvc;

    private Todoitem todoitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Todoitem createEntity(EntityManager em) {
        Todoitem todoitem = new Todoitem()
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS)
            .task(DEFAULT_TASK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        todoitem.setUser(user);
        return todoitem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Todoitem createUpdatedEntity(EntityManager em) {
        Todoitem todoitem = new Todoitem()
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS)
            .task(UPDATED_TASK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        todoitem.setUser(user);
        return todoitem;
    }

    @BeforeEach
    public void initTest() {
        todoitem = createEntity(em);
    }

    @Test
    @Transactional
    public void createTodoitem() throws Exception {
        int databaseSizeBeforeCreate = todoitemRepository.findAll().size();
        // Create the Todoitem
        restTodoitemMockMvc.perform(post("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(todoitem)))
            .andExpect(status().isCreated());

        // Validate the Todoitem in the database
        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeCreate + 1);
        Todoitem testTodoitem = todoitemList.get(todoitemList.size() - 1);
        assertThat(testTodoitem.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTodoitem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTodoitem.getTask()).isEqualTo(DEFAULT_TASK);
    }

    @Test
    @Transactional
    public void createTodoitemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = todoitemRepository.findAll().size();

        // Create the Todoitem with an existing ID
        todoitem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTodoitemMockMvc.perform(post("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(todoitem)))
            .andExpect(status().isBadRequest());

        // Validate the Todoitem in the database
        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = todoitemRepository.findAll().size();
        // set the field null
        todoitem.setCreated(null);

        // Create the Todoitem, which fails.


        restTodoitemMockMvc.perform(post("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(todoitem)))
            .andExpect(status().isBadRequest());

        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = todoitemRepository.findAll().size();
        // set the field null
        todoitem.setStatus(null);

        // Create the Todoitem, which fails.


        restTodoitemMockMvc.perform(post("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(todoitem)))
            .andExpect(status().isBadRequest());

        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTodoitems() throws Exception {
        // Initialize the database
        todoitemRepository.saveAndFlush(todoitem);

        // Get all the todoitemList
        restTodoitemMockMvc.perform(get("/api/todoitems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(todoitem.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].task").value(hasItem(DEFAULT_TASK.toString())));
    }
    
    @Test
    @Transactional
    public void getTodoitem() throws Exception {
        // Initialize the database
        todoitemRepository.saveAndFlush(todoitem);

        // Get the todoitem
        restTodoitemMockMvc.perform(get("/api/todoitems/{id}", todoitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(todoitem.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.task").value(DEFAULT_TASK.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTodoitem() throws Exception {
        // Get the todoitem
        restTodoitemMockMvc.perform(get("/api/todoitems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTodoitem() throws Exception {
        // Initialize the database
        todoitemService.save(todoitem);

        int databaseSizeBeforeUpdate = todoitemRepository.findAll().size();

        // Update the todoitem
        Todoitem updatedTodoitem = todoitemRepository.findById(todoitem.getId()).get();
        // Disconnect from session so that the updates on updatedTodoitem are not directly saved in db
        em.detach(updatedTodoitem);
        updatedTodoitem
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS)
            .task(UPDATED_TASK);

        restTodoitemMockMvc.perform(put("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTodoitem)))
            .andExpect(status().isOk());

        // Validate the Todoitem in the database
        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeUpdate);
        Todoitem testTodoitem = todoitemList.get(todoitemList.size() - 1);
        assertThat(testTodoitem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTodoitem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTodoitem.getTask()).isEqualTo(UPDATED_TASK);
    }

    @Test
    @Transactional
    public void updateNonExistingTodoitem() throws Exception {
        int databaseSizeBeforeUpdate = todoitemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTodoitemMockMvc.perform(put("/api/todoitems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(todoitem)))
            .andExpect(status().isBadRequest());

        // Validate the Todoitem in the database
        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTodoitem() throws Exception {
        // Initialize the database
        todoitemService.save(todoitem);

        int databaseSizeBeforeDelete = todoitemRepository.findAll().size();

        // Delete the todoitem
        restTodoitemMockMvc.perform(delete("/api/todoitems/{id}", todoitem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Todoitem> todoitemList = todoitemRepository.findAll();
        assertThat(todoitemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
