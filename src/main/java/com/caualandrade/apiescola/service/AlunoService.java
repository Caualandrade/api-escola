package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.aluno.AlunoDTO;
import com.caualandrade.apiescola.dto.aluno.AlunoDadosCompletoDTO;
import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoModel insertAluno(AlunoDTO alunoDTO){
        var aluno = new AlunoModel();
        if(!emailExistente(alunoDTO.email())){
            BeanUtils.copyProperties(alunoDTO, aluno);
            alunoRepository.save(aluno);
            return aluno;
        }
        return null;
    }

    public Page<AlunoDadosCompletoDTO> getAllAlunos(Pageable pageable){
        return alunoRepository.findAll(pageable).map(AlunoDadosCompletoDTO::new);
    }

    public AlunoDadosCompletoDTO getAlunoById(Long id){
        var aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno com ID " + id + " não encontrado."));
        return new AlunoDadosCompletoDTO(aluno);
    }

    public void deleteAlunoById(Long id){
        var aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno com ID " + id + " não encontrado."));
        alunoRepository.delete(aluno);
    }

    public AlunoModel updateAlunoById(Long id, AlunoDTO alunoDTO){
        var aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno com ID " + id + " não encontrado."));
        BeanUtils.copyProperties(alunoDTO,aluno);
        alunoRepository.save(aluno);
        return aluno;
    }

    public Boolean emailExistente(String email){
        List<AlunoModel> alunos = alunoRepository.findAll();
        for (AlunoModel aluno : alunos) {
            if (aluno.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
