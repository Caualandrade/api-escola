package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDTO;
import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosCompletosDTO;
import com.caualandrade.apiescola.model.DisciplinaModel;
import com.caualandrade.apiescola.repository.DisciplinaRepository;
import com.caualandrade.apiescola.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(!disciplinaExiste(disciplinaDTO.nome())){
            BeanUtils.copyProperties(disciplinaDTO, disciplina);
            disciplinaRepository.save(disciplina);
            return disciplina;
        }
        return null;
    }

    public Page<DisciplinaDadosCompletosDTO> getAllDisciplinas(Pageable pageable){
        return disciplinaRepository.findAll(pageable).map(DisciplinaDadosCompletosDTO::new);
    }

    public DisciplinaDadosCompletosDTO addProfessorNaDisciplina(Long idDisciplina, Long idProfessor){
        var disciplina = disciplinaRepository.findById(idDisciplina);
        var professor = professorRepository.findById(idProfessor);
        if(disciplina.isPresent() && professor.isPresent()){
            disciplina.get().setProfessor(professor.get());
            disciplinaRepository.save(disciplina.get());
            return new DisciplinaDadosCompletosDTO(disciplina.get());
        }
        return null;
    }

    public void deleteDisciplina(Long idDisciplina){
        var disciplina = disciplinaRepository.findById(idDisciplina).orElseThrow(() -> new EntityNotFoundException("Disciplina com ID "+idDisciplina+" não encontrado"));
        disciplinaRepository.delete(disciplina);
    }

    public Boolean disciplinaExiste(String nome){
        List<DisciplinaModel> disciplinas = disciplinaRepository.findAll();
        for(DisciplinaModel disciplina : disciplinas){
            if(disciplina.getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }

}
