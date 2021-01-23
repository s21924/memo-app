package com.example.memoapp.model;

import java.util.List;
import java.util.Optional;
public interface MemoRepository {
    List<Memo> findAll();

    Optional<Memo> findById(Integer id);

    Memo save(Memo entity);

    boolean existsByDoneIsFalseAndMemoGroup_Id(Integer projectId);
}

