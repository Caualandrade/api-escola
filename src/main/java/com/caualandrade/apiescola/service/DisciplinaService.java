package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDTO;
import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.repository.DisciplinaRepository;
import com.caualandrade.apiescola.repository.ProfessorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public DisciplinaModel insertDisciplina(DisciplinaDTO disciplinaDTO){
        var disciplina = new DisciplinaModel();
        BeanUtils.copyProperties(disciplinaDTO, disciplina);
        disciplinaRepository.save(disciplina);
        return disciplina;
    }

    public Page<DisciplinaDadosCompletosDTO> getAllDisciplinas(Pageable pageable){
        return disciplinaRepository.findAll(pageable).map(DisciplinaDadosCompletosDTO::new);
    }

    public DisciplinaDadosCompletosDTO addProfessorNaDisciplina(Long idDisciplina, Long idProfessor){
        var disciplina = disciplinaRepository.findById(idDisciplina).get();
        var professor = professorRepository.findById(idProfessor).get();
        disciplina.setProfessor(professor);
        disciplinaRepository.save(disciplina);
        return new DisciplinaDadosCompletosDTO(disciplina);
    }



}
