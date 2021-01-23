package com.example.memoapp.adapter;

import com.example.memoapp.model.MemoGroup;
import com.example.memoapp.model.MemoGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 interface SqlMemoGroupRepository extends MemoGroupRepository, JpaRepository<MemoGroup, Integer> {

    @Override
    @Query("select distinct p from MemoGroup p join fetch p.steps")
    List<MemoGroup> findAll();





}
