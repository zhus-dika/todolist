package com.todolist.webapp.repository;

import com.todolist.webapp.domain.Todoitem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * Spring Data  repository for the Todoitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TodoitemRepository extends JpaRepository<Todoitem, Long> {

    @Query("select todoitem from Todoitem todoitem where todoitem.user.login = ?#{principal.username}")
    Page<Todoitem> findByUserIsCurrentUser(Pageable pageable);

    @Query("select todoitem from Todoitem todoitem where todoitem.user.login = ?#{principal.username} and todoitem.status = ?1")
    Page<Todoitem> findByUserCurrentUserAndStatus(Pageable pageable, String status);
}
