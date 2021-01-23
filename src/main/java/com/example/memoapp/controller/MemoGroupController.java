package com.example.memoapp.controller;

import com.example.memoapp.logic.MemoGroupService;
import com.example.memoapp.model.MemoGroup;
import com.example.memoapp.model.MemoGroupRepository;
import com.example.memoapp.model.ProjectStep;
import com.example.memoapp.model.projection.MemoGroupWriteModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/memogroups")
class MemoGroupController {
    private final MemoGroupService service;
    private final MemoGroupRepository repository;

    MemoGroupController(final MemoGroupService service, MemoGroupRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    String showProjects(Model model) {
        model.addAttribute("memoGroup", new MemoGroupWriteModel());
        return "memoGroups";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("memoGroup") @Valid MemoGroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "memoGroups";
        }
        service.save(current);
        model.addAttribute("memoGroup", new MemoGroupWriteModel());
        model.addAttribute("memoGroups", getProjects());
        model.addAttribute("message", "Dodano grupę notatek!");
        return "memoGroups";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("memoGroup") MemoGroupWriteModel current) {
        current.getSteps().add(new ProjectStep());
        return "memoGroups";
    }

    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("memoGroup") MemoGroupWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            service.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Błąd podczas tworzenia grupy!");
        }
        return "memoGroups";
    }

    @GetMapping("/{id}")
    ResponseEntity<MemoGroup> readMemoGroup (@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ModelAttribute("memoGroups")
    List<MemoGroup> getProjects() {
        return service.readAll();
    }
}

