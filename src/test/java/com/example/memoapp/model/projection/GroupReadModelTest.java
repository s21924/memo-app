package com.example.memoapp.model.projection;

import com.example.memoapp.model.Memo;
import com.example.memoapp.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group when no task deadlines")
    void contructor_noDeadlines_createsNullDeadline() {
        //given
        var source = new Memo();
        source.setDescription("foo");
        source.setTasks(Set.of(new Task("bar", null)));

        //when
        var result = new GroupReadModel(source);

        //then

        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);

    }

}