package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDadosCompletosDTO;
import com.caualandrade.apiescola.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity insertMatricula(@RequestBody @Valid MatriculaDTO matriculaDTO, UriComponentsBuilder uriComponentsBuilder){
        var matricula = matriculaService.insertMatricula(matriculaDTO);
        var URI = uriComponentsBuilder.path("/matriculas/{id}").buildAndExpand(matricula.getId()).toUri();
        return ResponseEntity.created(URI).body(new MatriculaDadosCompletosDTO(matricula));
    }

    @GetMapping
    public ResponseEntity<Page<MatriculaDadosCompletosDTO>> getAllMatriculas(Pageable pageable){
        return ResponseEntity.ok(matriculaService.getAllMatriculas(pageable));
    }


}
