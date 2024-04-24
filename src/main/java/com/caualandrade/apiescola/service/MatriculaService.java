package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.matricula.MatriculaDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDadosCompletosDTO;
import com.caualandrade.apiescola.infra.StatusAluno;
import com.caualandrade.apiescola.model.MatriculaModel;
import com.caualandrade.apiescola.repository.AlunoRepository;
import com.caualandrade.apiescola.repository.DisciplinaRepository;
import com.caualandrade.apiescola.repository.MatriculaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatriculaService {

    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public MatriculaModel insertMatricula(MatriculaDTO matriculaDTO) {

        var matricula = new MatriculaModel();
        var aluno = alunoRepository.findById(matriculaDTO.id_aluno()).get();
        var disciplina = disciplinaRepository.findById(matriculaDTO.id_disciplina()).get();

        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setPrimeiraNota(0.0);
        matricula.setSegundaNota(0.0);
        matricula.setTerceiraNota(0.0);
        matricula.setTotalNota(matricula.calcularNotaTotal(matricula.getPrimeiraNota(), matricula.getSegundaNota(), matricula.getTerceiraNota()));
        matricula.setStatus(StatusAluno.valueOf("NAO_APROVADO"));

        matriculaRepository.save(matricula);

        return matricula;
    }

    public Page<MatriculaDadosCompletosDTO> getAllMatriculas(Pageable pageable) {
        return matriculaRepository.findAll(pageable).map(MatriculaDadosCompletosDTO::new);
    }

}
