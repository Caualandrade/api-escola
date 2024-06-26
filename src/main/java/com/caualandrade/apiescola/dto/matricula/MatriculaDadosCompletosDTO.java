package com.caualandrade.apiescola.dto.matricula;

import com.caualandrade.apiescola.infra.StatusAluno;
import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.model.MatriculaModel;

public record MatriculaDadosCompletosDTO(Long id,
                                         String nome_aluno,
                                         String nome_disciplina,
                                         Double primeiraNota,
                                         Double segundaNota,
                                         Double terceiraNota,
                                         Double notaTotal,
                                         StatusAluno statusAluno) {

    public MatriculaDadosCompletosDTO(MatriculaModel matriculaModel){
        this(matriculaModel.getId(),
                matriculaModel.getAluno().getNome(),
                matriculaModel.getDisciplina().getNome(),
                matriculaModel.getPrimeiraNota(),
                matriculaModel.getSegundaNota(),
                matriculaModel.getTerceiraNota(),
                matriculaModel.getTotalNota(),
                matriculaModel.getStatus());
    }


}
