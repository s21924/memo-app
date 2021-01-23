package com.example.memoapp.logic;

import com.example.memoapp.model.Memo;
import com.example.memoapp.model.MemoGroup;
import com.example.memoapp.model.MemoRepository;
import com.example.memoapp.model.TaskRepository;
import com.example.memoapp.model.projection.GroupReadModel;
import com.example.memoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;


public class MemoService {
    private MemoRepository repository;
    private TaskRepository taskRepository;

    MemoService(final MemoRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }

    GroupReadModel createGroup(final GroupWriteModel source, final MemoGroup memoGroup) {
        Memo result = repository.save(source.toGroup(memoGroup));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndMemo_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        Memo result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Memo with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
