package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDadosCompletosDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaNotasDTO;
import com.caualandrade.apiescola.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matricula")
public class MatriculaController {

    private MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @Operation(summary = "Realiza o cadastro da matricula", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "matricula cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar a matricula")
    })
    @PostMapping
    @Transactional
    public ResponseEntity insertMatricula(@RequestBody @Valid MatriculaDTO matriculaDTO, UriComponentsBuilder uriComponentsBuilder) {
        try {
            var matricula = matriculaService.insertMatricula(matriculaDTO);
            var URI = uriComponentsBuilder.path("/matriculas/{id}").buildAndExpand(matricula.getId()).toUri();
            return ResponseEntity.created(URI).body(new MatriculaDadosCompletosDTO(matricula));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Aluno já matriculado nessa turma");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @Operation(summary = "Realiza a busca dos dados de todas as matriculas cadastradas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<MatriculaDadosCompletosDTO>> getAllMatriculas(Pageable pageable) {
        return ResponseEntity.ok(matriculaService.getAllMatriculas(pageable));
    }

    @Operation(summary = "Realiza a adição das notas do aluno", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adição realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a adição dos dados")
    })
    @PutMapping( "/{id}")
    @Transactional
    public ResponseEntity<MatriculaDadosCompletosDTO> addNota(@PathVariable Long id,@RequestBody @Valid MatriculaNotasDTO matriculaNotasDTO){
        try {
            var matricula = matriculaService.addNota(id, matriculaNotasDTO);
            return ResponseEntity.ok(matricula);
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }


}
