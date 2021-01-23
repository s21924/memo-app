package com.example.memoapp.model.projection;

import com.example.memoapp.model.Memo;
import com.example.memoapp.model.MemoGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GroupWriteModel {
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    @Valid
    private List<MemoWriteModel> tasks = new ArrayList<>();

    public GroupWriteModel() {
        tasks.add(new MemoWriteModel());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<MemoWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(final List<MemoWriteModel> tasks) {
        this.tasks = tasks;
    }

    public Memo toGroup(final MemoGroup memoGroup) {
        var result = new Memo();
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        result.setMemoGroup(memoGroup);
        return result;
    }
}