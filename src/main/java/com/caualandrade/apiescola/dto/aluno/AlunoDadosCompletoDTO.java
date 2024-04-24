package com.caualandrade.apiescola.dto.aluno;

import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.model.MatriculaModel;

import java.util.List;

public record AlunoDadosCompletoDTO(Long id, String nome, String email, List<MatriculaModel> matriculas) {

    public AlunoDadosCompletoDTO(AlunoModel alunoModel) {
        this(alunoModel.getId(), alunoModel.getNome(),alunoModel.getEmail(),alunoModel.getMatricula());
    }

}
