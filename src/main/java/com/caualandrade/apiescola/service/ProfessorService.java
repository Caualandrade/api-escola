package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.professor.ProfessorDTO;
import com.caualandrade.apiescola.dto.professor.ProfessorDadosCompletoDTO;
import com.caualandrade.apiescola.model.ProfessorModel;
import com.caualandrade.apiescola.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public ProfessorModel insertProfessor(ProfessorDTO professorDTO){
        var professor = new ProfessorModel();
        if(!emailExistente(professorDTO.email())){
            BeanUtils.copyProperties(professorDTO, professor);
            professorRepository.save(professor);
            return professor;
        }
       return null;
    }

    public Page<ProfessorDadosCompletoDTO> getAllProfessores(Pageable pageable){
        return professorRepository.findAll(pageable).map(ProfessorDadosCompletoDTO::new);
    }

    public ProfessorDadosCompletoDTO getProfessorById(Long id){
        var professor = professorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Professor com ID "+id+" não encontrado."));
        return new ProfessorDadosCompletoDTO(professor);
    }
    public void deleteProfessorById(Long id){
        var professor = professorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Professor com ID "+id+" não encontrado."));
        professorRepository.delete(professor);
    }
    public ProfessorDadosCompletoDTO updateProfessorById(Long id, ProfessorDTO professorDTO){
        var professor = professorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Professor com ID "+id+" não encontrado."));
        BeanUtils.copyProperties(professorDTO, professor);
        professorRepository.save(professor);
        return new ProfessorDadosCompletoDTO(professor);
    }

    public Boolean emailExistente(String email){
        List<ProfessorModel> professores = professorRepository.findAll();
        for(ProfessorModel professor : professores){
            if(professor.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

}
