package com.example.memoapp.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "MemoGroup step's description must not be empty")
    private String description;
    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name = "memoGroup_id")
    private MemoGroup memoGroup;

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

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(final int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    MemoGroup getProject() {
        return memoGroup;
    }

    public void setProject(final MemoGroup memoGroup) {
        this.memoGroup = memoGroup;
    }
}
