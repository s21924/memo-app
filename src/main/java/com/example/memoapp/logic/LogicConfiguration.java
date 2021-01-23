package com.example.memoapp.logic;

import com.example.memoapp.TaskConfigurationProperties;
import com.example.memoapp.model.MemoGroupRepository;
import com.example.memoapp.model.MemoRepository;
import com.example.memoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {
    @Bean
    MemoGroupService service (
            final MemoGroupRepository repository,
            final MemoRepository memoRepository,
            final MemoService memoService,
            final TaskConfigurationProperties config

            ) {
        return new MemoGroupService(repository, memoRepository, memoService, config);
    }

    @Bean
    MemoService memoService(
        final MemoRepository memoRepository,
        final TaskRepository taskRepository
    ){
        return new MemoService(memoRepository, taskRepository);
    }



}
