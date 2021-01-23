package com.example.memoapp.logic;

import com.example.memoapp.TaskConfigurationProperties;
import com.example.memoapp.model.*;
import com.example.memoapp.model.projection.GroupReadModel;
import com.example.memoapp.model.projection.MemoWriteModel;
import com.example.memoapp.model.projection.GroupWriteModel;
import com.example.memoapp.model.projection.MemoGroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
public class MemoGroupService {
    private MemoGroupRepository repository;
    private MemoRepository taskGroupRepository;
    private MemoService memoService;
    private TaskConfigurationProperties config;

    public MemoGroupService(final MemoGroupRepository repository, final MemoRepository taskGroupRepository, final MemoService memoService, final TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.memoService = memoService;
        this.config = config;
    }

    public List<MemoGroup> readAll() {
        return repository.findAll();
    }

    public MemoGroup save(final MemoGroupWriteModel toSave) {
        return repository.save(toSave.toMemoGroup());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndMemoGroup_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new MemoWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toList())
                    );
                    return memoService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("MemoGroup with given id not found"));
    }
}


