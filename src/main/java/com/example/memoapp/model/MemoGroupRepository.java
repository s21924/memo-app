package com.example.memoapp.model;

import java.util.List;
import java.util.Optional;

public interface MemoGroupRepository {
    List<MemoGroup> findAll();

    Optional<MemoGroup> findById(Integer id);

    MemoGroup save(MemoGroup entity);
}
