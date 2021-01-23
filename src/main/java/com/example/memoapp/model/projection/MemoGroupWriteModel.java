package com.example.memoapp.model.projection;

import com.example.memoapp.model.MemoGroup;
import com.example.memoapp.model.ProjectStep;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MemoGroupWriteModel {
    @NotBlank(message = "MemoGroup's description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps = new ArrayList<>();

    public MemoGroupWriteModel() {
        steps.add(new ProjectStep());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<ProjectStep> steps) {
        this.steps = steps;
    }

    public MemoGroup toMemoGroup() {
        var result = new MemoGroup();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}

