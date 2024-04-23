package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.aluno.AlunoDTO;
import com.caualandrade.apiescola.dto.aluno.AlunoDadosCompletoDTO;
import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.repository.AlunoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoModel insertAluno(AlunoDTO alunoDTO){
        var aluno = new AlunoModel();
        BeanUtils.copyProperties(alunoDTO, aluno);
        alunoRepository.save(aluno);
        return aluno;
    }

    public Page<AlunoDadosCompletoDTO> getAlunos(Pageable pageable){
        return alunoRepository.findAll(pageable).map(AlunoDadosCompletoDTO::new);
    }



}
