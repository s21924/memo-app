package com.example.memoapp.logic;

import com.example.memoapp.TaskConfigurationProperties;
import com.example.memoapp.model.*;
import com.example.memoapp.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemoGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalArgumentException when configuration ok and no projects for a given id")
    void createGroup_configurationOk_And_noProjects_throws_IllegalArgumentException() {
        //given
        var mockRepository = mock(MemoGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new MemoGroupService(mockRepository, null, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }


    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {
        //given
        var mockGroupRepository = groupRepositoryReturning(true);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        // system under test
        var toTest = new MemoGroupService(null, mockGroupRepository, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");

    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and projects for a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(MemoGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        MemoRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new MemoGroupService(mockRepository, null, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup() {
        //given
        var today = LocalDate.now().atStartOfDay();
        // and
        var project = (projectWith("abc", Set.of(-1, -2)));
        var mockRepository = mock(MemoGroupRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional
                        .of(project));
        // and
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        var serviceWithInMemRepo = new MemoService(inMemoryGroupRepo, null);
        int countBeforeCall = inMemoryGroupRepository().count();
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new MemoGroupService(mockRepository, inMemoryGroupRepo, serviceWithInMemRepo, mockConfig);
        //when
        GroupReadModel result = toTest.createGroup(today, 1);
        //then
        assertThat(result.getDescription()).isEqualTo("abc");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getTasks()).allMatch(task -> task.getDescription().equals("abc"));
        assertThat(countBeforeCall+1) .isNotEqualTo(inMemoryGroupRepo.count());

    }

    private MemoGroup projectWith (String projectDescription, Set<Integer> daysToDeadline) {
        Set<ProjectStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("abc");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(MemoGroup.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getSteps()).thenReturn(steps);
        return result;
    }

    private MemoRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(MemoRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndMemoGroup_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties configurationReturning(final boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private InMemoryGroupRepository inMemoryGroupRepository() {
        return new InMemoryGroupRepository();
    }
    private static class InMemoryGroupRepository implements MemoRepository {
            private int index = 0;
            private Map<Integer, Memo> map = new HashMap<>();

            public int count() {
                return map.values().size();
            }

            @Override
            public List<Memo> findAll() {
                return new ArrayList<>(map.values());
            }

            @Override
            public Optional<Memo> findById(Integer id) {
                return Optional.ofNullable(map.get(id));
            }

            @Override
            public Memo save(Memo entity) {
                if (entity.getId() == 0) {
                    try {
                        var field = Memo.class.getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(entity, ++index);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                    map.put(entity.getId(), entity);
                }
                return entity;
            }

            @Override
            public boolean existsByDoneIsFalseAndMemoGroup_Id(Integer projectId) {
                return map.values().stream()
                        .filter(group -> !group.isDone())
                        .anyMatch(group -> group.getMemoGroup() != null && group.getMemoGroup().getId() == projectId);
            }
        };



}