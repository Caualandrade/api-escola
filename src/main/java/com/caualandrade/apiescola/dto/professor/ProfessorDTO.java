package com.caualandrade.apiescola.dto.professor;

import com.caualandrade.apiescola.model.DisciplinaModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProfessorDTO(@NotBlank String nome,
                           @NotBlank String email,
                           List<DisciplinaModel> disciplinas) {
}
