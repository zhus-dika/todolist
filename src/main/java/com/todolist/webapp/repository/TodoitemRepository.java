package com.todolist.webapp.repository;

import com.todolist.webapp.domain.Todoitem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Todoitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TodoitemRepository extends JpaRepository<Todoitem, Long> {

    @Query("select todoitem from Todoitem todoitem where todoitem.user.login = ?#{principal.username}")
    List<Todoitem> findByUserIsCurrentUser();
}
