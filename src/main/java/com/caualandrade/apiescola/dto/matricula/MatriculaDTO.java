package com.caualandrade.apiescola.dto.matricula;

import com.caualandrade.apiescola.dto.aluno.AlunoDTO;
import com.caualandrade.apiescola.infra.StatusAluno;
import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.model.DisciplinaModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatriculaDTO(@NotNull Long id_aluno,
                           @NotNull Long id_disciplina,
                           Float nota,
                           StatusAluno statusAluno) {
}
