package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDTO;
import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity insertDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO, UriComponentsBuilder uriComponentsBuilder){
        var disciplina = disciplinaService.insertDisciplina(disciplinaDTO);
        var URI = uriComponentsBuilder.path("/disciplinas/{id}").buildAndExpand(disciplina.getId()).toUri();
        return ResponseEntity.created(URI).body(new DisciplinaDadosCompletosDTO(disciplina));
    }

    @GetMapping
    public ResponseEntity<Page<DisciplinaDadosCompletosDTO>> getAllDisciplinas(Pageable pageable){
        return ResponseEntity.ok(disciplinaService.getAllDisciplinas(pageable));
    }

    @PutMapping("/{idDisciplina}/professor/{idProfessor}")
    @Transactional
    public ResponseEntity<DisciplinaDadosCompletosDTO> addProfessorNaDisciplina(@PathVariable Long idDisciplina, @PathVariable Long idProfessor){
        return ResponseEntity.ok(disciplinaService.addProfessorNaDisciplina(idDisciplina,idProfessor));
    }


}
