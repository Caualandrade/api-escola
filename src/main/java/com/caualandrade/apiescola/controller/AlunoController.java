package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.aluno.AlunoDTO;
import com.caualandrade.apiescola.dto.aluno.AlunoDadosCompletoDTO;
import com.caualandrade.apiescola.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity insertAluno(@RequestBody @Valid AlunoDTO alunoDTO, UriComponentsBuilder uriBuilder){
        var aluno = alunoService.insertAluno(alunoDTO);
        var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new AlunoDadosCompletoDTO(aluno));
    }

    @GetMapping
    public ResponseEntity<Page<AlunoDadosCompletoDTO>> getAllAlunos(Pageable pageable) {
        var page = alunoService.getAlunos(pageable);
        return ResponseEntity.ok(page);
    }

    

}
