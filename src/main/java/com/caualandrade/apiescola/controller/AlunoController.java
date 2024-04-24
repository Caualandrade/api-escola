package com.caualandrade.apiescola.controller;

import com.caualandrade.apiescola.dto.aluno.AlunoDTO;
import com.caualandrade.apiescola.dto.aluno.AlunoDadosCompletoDTO;
import com.caualandrade.apiescola.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Aluno")
public class AlunoController {

    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }


    @Operation(summary = "Realiza o cadastro do aluno", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar o aluno")
    })
    @PostMapping
    @Transactional
    public ResponseEntity insertAluno(@RequestBody @Valid AlunoDTO alunoDTO, UriComponentsBuilder uriBuilder) {
        try {
            var aluno = alunoService.insertAluno(alunoDTO);
            var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
            return ResponseEntity.created(uri).body(new AlunoDadosCompletoDTO(aluno));
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Aluno já cadastrado no sistema");
        }
    }


    @Operation(summary = "Realiza a busca dos dados de todos os alunos cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<AlunoDadosCompletoDTO>> getAllAlunos(Pageable pageable) {
        var page = alunoService.getAllAlunos(pageable);
        return ResponseEntity.ok(page);
    }


    @Operation(summary = "Realiza a busca dos dados do aluno referente ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @GetMapping("{id}")
    public ResponseEntity<AlunoDadosCompletoDTO> getAlunoById(@PathVariable Long id) {
        try {
            var aluno = alunoService.getAlunoById(id);
            return ResponseEntity.ok(aluno);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Deleta os dados do aluno referente ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity deleteAlunoById(@PathVariable Long id) {
        try {
            alunoService.deleteAlunoById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualiza os dados do aluno referente ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos dados")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AlunoDadosCompletoDTO> updateAlunoById(@PathVariable Long id, @RequestBody @Valid AlunoDTO alunoDTO) {
        try{
            var aluno = alunoService.updateAlunoById(id,alunoDTO);
            return ResponseEntity.ok(new AlunoDadosCompletoDTO(aluno));
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
