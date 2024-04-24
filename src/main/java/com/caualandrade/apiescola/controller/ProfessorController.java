package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.professor.ProfessorDTO;
import com.caualandrade.apiescola.dto.professor.ProfessorDadosCompletoDTO;
import com.caualandrade.apiescola.service.ProfessorService;
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
@RequestMapping("/professores")
@Tag(name = "Professor")
public class ProfessorController {

    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Operation(summary = "Realiza o cadastro do professor", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar o professor")
    })
    @PostMapping
    @Transactional
    public ResponseEntity insertProfessor(@RequestBody @Valid ProfessorDTO professorDTO, UriComponentsBuilder uriComponentsBuilder) {
        try {
            var professor = professorService.insertProfessor(professorDTO);
            var URI = uriComponentsBuilder.path("/professores/{id}").buildAndExpand(professor.getId()).toUri();
            return ResponseEntity.created(URI).body(new ProfessorDadosCompletoDTO(professor));
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Professor já cadastrado");
        }catch (ValidationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Realiza a busca dos dados de todos os professores cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<ProfessorDadosCompletoDTO>> getAllProfessores(Pageable pageable) {
        return ResponseEntity.ok(professorService.getAllProfessores(pageable));
    }

    @Operation(summary = "Realiza a busca dos dados do professor referente ao ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDadosCompletoDTO> getProfessorById(@PathVariable Long id){
        try{
            var professor = professorService.getProfessorById(id);
            return ResponseEntity.ok(professor);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza os dados do professor referente ao ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProfessorDadosCompletoDTO> updateProfessorById(@PathVariable Long id, @RequestBody @Valid ProfessorDTO professorDTO){
        try{
            var professor = professorService.updateProfessorById(id,professorDTO);
            return ResponseEntity.ok(professor);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (ValidationException e){
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Deleta os dados do professor referente ao ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProfessorById(@PathVariable Long id){
        try {
            professorService.deleteProfessorById(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
