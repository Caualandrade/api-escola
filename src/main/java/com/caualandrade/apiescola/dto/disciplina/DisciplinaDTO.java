package com.caualandrade.apiescola.dto.disciplina;

import com.caualandrade.apiescola.dto.professor.ProfessorDTO;
import com.caualandrade.apiescola.model.MatriculaModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DisciplinaDTO(@NotBlank String nome) {
}
