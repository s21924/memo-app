package com.example.memoapp.model.projection;

import com.example.memoapp.model.Task;
import com.example.memoapp.model.Memo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private int id;
    private String description;
    /**
     * Deadline form the latest task in group.
     */
    private LocalDateTime deadline;
    private Set<MemoReadModel> tasks;

    public GroupReadModel(Memo source) {
        id = source.getId();
        description = source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        tasks = source.getTasks().stream()
                .map(MemoReadModel::new)
                .collect(Collectors.toSet());
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<MemoReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<MemoReadModel> tasks) {
        this.tasks = tasks;
    }
}
