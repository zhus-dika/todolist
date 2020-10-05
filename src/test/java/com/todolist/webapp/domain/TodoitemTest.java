package com.todolist.webapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.todolist.webapp.web.rest.TestUtil;

public class TodoitemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Todoitem.class);
        Todoitem todoitem1 = new Todoitem();
        todoitem1.setId(1L);
        Todoitem todoitem2 = new Todoitem();
        todoitem2.setId(todoitem1.getId());
        assertThat(todoitem1).isEqualTo(todoitem2);
        todoitem2.setId(2L);
        assertThat(todoitem1).isNotEqualTo(todoitem2);
        todoitem1.setId(null);
        assertThat(todoitem1).isNotEqualTo(todoitem2);
    }
}
