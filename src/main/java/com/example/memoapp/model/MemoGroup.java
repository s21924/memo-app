package com.example.memoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "memo_groups")
public class MemoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "MemoGroup's description must not be empty")
    private String description;
    @OneToMany(mappedBy = "memoGroup")
    private Set<Memo> memo_group;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memoGroup")
    private Set<ProjectStep> steps;

    public MemoGroup() {
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    Set<Memo> getMemo_group() {
        return memo_group;
    }

    void setMemo_group(final Set<Memo> groups) {
        this.memo_group = memo_group;
    }

    public Set<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(final Set<ProjectStep> steps) {
        this.steps = steps;
    }
}
