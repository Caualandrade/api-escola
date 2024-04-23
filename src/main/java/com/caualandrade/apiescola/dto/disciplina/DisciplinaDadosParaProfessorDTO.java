package com.caualandrade.apiescola.dto.disciplina;

import com.caualandrade.apiescola.model.DisciplinaModel;

public record DisciplinaDadosParaProfessorDTO(Long id,
                                              String nome) {

    public DisciplinaDadosParaProfessorDTO (DisciplinaModel disciplina){
        this(disciplina.getId(),disciplina.getNome());
    }

}
