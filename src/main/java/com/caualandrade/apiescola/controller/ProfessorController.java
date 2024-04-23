package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.professor.ProfessorDTO;
import com.caualandrade.apiescola.dto.professor.ProfessorDadosCompletoDTO;
import com.caualandrade.apiescola.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity insertProfessor(@RequestBody @Valid ProfessorDTO professorDTO, UriComponentsBuilder uriComponentsBuilder) {
        var professor = professorService.insertProfessor(professorDTO);
        var URI = uriComponentsBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
        return ResponseEntity.created(URI).body(new ProfessorDadosCompletoDTO(professor));
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorDadosCompletoDTO>> getAllProfessores(Pageable pageable) {
        return ResponseEntity.ok(professorService.getAllProfessores(pageable));
    }

}
