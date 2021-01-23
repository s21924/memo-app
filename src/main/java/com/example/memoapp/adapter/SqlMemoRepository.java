package com.example.memoapp.adapter;

import com.example.memoapp.model.Memo;
import com.example.memoapp.model.MemoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 interface SqlMemoRepository extends MemoRepository, JpaRepository<Memo, Integer> {
   @Override
   @Query("select distinct g from Memo g join fetch g.tasks")
   List<Memo> findAll();

   @Override
   boolean existsByDoneIsFalseAndMemoGroup_Id(Integer memoGroupId);

}
