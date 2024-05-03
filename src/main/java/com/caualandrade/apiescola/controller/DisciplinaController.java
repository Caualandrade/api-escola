package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDTO;
import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplina")
public class DisciplinaController {

    private DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Operation(summary = "Realiza o cadastro da disciplina", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disciplina cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar a disciplina")
    })
    @PostMapping
    @Transactional
    public ResponseEntity insertDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO, UriComponentsBuilder uriComponentsBuilder) {
        try {
            var disciplina = disciplinaService.insertDisciplina(disciplinaDTO);
            var URI = uriComponentsBuilder.path("/disciplinas/{id}").buildAndExpand(disciplina.getId()).toUri();
            return ResponseEntity.created(URI).body(new DisciplinaDadosCompletosDTO(disciplina));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Disciplina já cadastrada no sistema");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Realiza a busca dos dados de todas as disciplinas cadastradas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<DisciplinaDadosCompletosDTO>> getAllDisciplinas(Pageable pageable) {
        return ResponseEntity.ok(disciplinaService.getAllDisciplinas(pageable));
    }


    @Operation(summary = "Adiciona professor na disciplina", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adiação realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @PutMapping("/{idDisciplina}/professor/{idProfessor}")
    @Transactional
    public ResponseEntity<DisciplinaDadosCompletosDTO> addProfessorNaDisciplina(@PathVariable Long idDisciplina, @PathVariable Long idProfessor) {
        try {
            return ResponseEntity.ok(disciplinaService.addProfessorNaDisciplina(idDisciplina, idProfessor));
        } catch (NullPointerException | ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Deleta os dados do disciplina referente ao ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDisciplinaById(@PathVariable Long id) {
        try {
            disciplinaService.deleteDisciplina(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
