package com.example.memoapp.model.projection;

import com.example.memoapp.model.Task;

public class MemoReadModel {
    private String description;
    private boolean done;

    MemoReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }
}
