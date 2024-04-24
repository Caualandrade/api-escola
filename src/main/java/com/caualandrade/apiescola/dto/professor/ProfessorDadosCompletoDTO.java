package com.caualandrade.apiescola.dto.professor;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosParaProfessorDTO;
import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.model.ProfessorModel;

import java.util.List;

public record ProfessorDadosCompletoDTO(Long id, String nome, String email, List<String> disciplinas) {

    public ProfessorDadosCompletoDTO(ProfessorModel professorModel) {
        this(professorModel.getId(), professorModel.getNome(), professorModel.getEmail(),professorModel.nomeDasDisciplinas());
    }

}
