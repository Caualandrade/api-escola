package com.caualandrade.apiescola.dto.disciplina;

import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.model.ProfessorModel;

import java.util.List;

public record DisciplinaDadosCompletosDTO(Long id, String disciplinaNome, ProfessorModel professorNome) {

    public DisciplinaDadosCompletosDTO(DisciplinaModel disciplinaModel){
        this(disciplinaModel.getId(), disciplinaModel.getNome(), disciplinaModel.getProfessor());
    }

}
