package com.caualandrade.apiescola.dto.aluno;

import com.caualandrade.apiescola.model.MatriculaModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AlunoDTO(@NotBlank String nome, List<MatriculaModel> matricula) {
}
