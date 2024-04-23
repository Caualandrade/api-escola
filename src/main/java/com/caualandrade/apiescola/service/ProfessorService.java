package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.professor.ProfessorDTO;
import com.caualandrade.apiescola.dto.professor.ProfessorDadosCompletoDTO;
import com.caualandrade.apiescola.model.ProfessorModel;
import com.caualandrade.apiescola.repository.ProfessorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public ProfessorModel insertProfessor(ProfessorDTO professorDTO){
        var professor = new ProfessorModel();
        BeanUtils.copyProperties(professorDTO, professor);
        professorRepository.save(professor);
        return professor;
    }

    public Page<ProfessorDadosCompletoDTO> getAllProfessores(Pageable pageable){
        return professorRepository.findAll(pageable).map(ProfessorDadosCompletoDTO::new);
    }

}
