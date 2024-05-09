package com.caualandrade.apiescola.service;

import com.caualandrade.apiescola.dto.matricula.MatriculaDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaDadosCompletosDTO;
import com.caualandrade.apiescola.dto.matricula.MatriculaNotasDTO;
import com.caualandrade.apiescola.infra.StatusAluno;
import com.caualandrade.apiescola.model.AlunoModel;
import com.caualandrade.apiescola.model.MatriculaModel;
import com.caualandrade.apiescola.repository.AlunoRepository;
import com.caualandrade.apiescola.repository.DisciplinaRepository;
import com.caualandrade.apiescola.repository.MatriculaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    /*
    public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, DisciplinaRepository disciplinaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }
     */

    public MatriculaModel insertMatricula(MatriculaDTO matriculaDTO) {

        var matricula = new MatriculaModel();
        var aluno = alunoRepository.findById(matriculaDTO.id_aluno());
        var disciplina = disciplinaRepository.findById(matriculaDTO.id_disciplina());

        if (aluno.isPresent() && disciplina.isPresent()) {
            if (AlunoEstaMatriculado(matriculaDTO.id_aluno(), matriculaDTO.id_disciplina()).equals(false)) {
                matricula.setAluno(aluno.get());
                matricula.setDisciplina(disciplina.get());
                matricula.setPrimeiraNota(0.0);
                matricula.setSegundaNota(0.0);
                matricula.setTerceiraNota(0.0);
                matricula.setTotalNota(matricula.calcularNotaTotal(matricula.getPrimeiraNota(), matricula.getSegundaNota(), matricula.getTerceiraNota()));
                matricula.setStatus(StatusAluno.valueOf("NAO_APROVADO"));
                matriculaRepository.save(matricula);
                return matricula;
            }
        }
        return null;
    }

    public Page<MatriculaDadosCompletosDTO> getAllMatriculas(Pageable pageable) {
        return matriculaRepository.findAll(pageable).map(MatriculaDadosCompletosDTO::new);
    }

    public MatriculaDadosCompletosDTO addNota(Long idMatricula, MatriculaNotasDTO matriculaNotasDTO) {

        var matricula = matriculaRepository.findById(idMatricula);

        if (matricula.isPresent()) {
                if (notaDentroDoEscopo(matriculaNotasDTO.nota1(), matriculaNotasDTO.nota2(), matriculaNotasDTO.nota3())) {
                    matricula.get().setPrimeiraNota(matriculaNotasDTO.nota1());
                    matricula.get().setSegundaNota(matriculaNotasDTO.nota2());
                    matricula.get().setTerceiraNota(matriculaNotasDTO.nota3());
                    Double total = matricula.get().calcularNotaTotal(matricula.get().getPrimeiraNota(),
                            matricula.get().getSegundaNota(),
                            matricula.get().getTerceiraNota());
                    String totalFormatado = formatarNumero(total);
                    matricula.get().setTotalNota(Double.valueOf(totalFormatado));
                    matriculaRepository.save(matricula.get());
                    return new MatriculaDadosCompletosDTO(matricula.get());
                }
        }
        return null;
    }

    public Boolean AlunoEstaMatriculado(Long idAluno, Long idDisciplina) {
        List<MatriculaModel> matriculas = matriculaRepository.findAll();
        for (MatriculaModel matricula : matriculas) {
            if (matricula.getAluno().getId().equals(idAluno) && matricula.getDisciplina().getId().equals(idDisciplina)) {
                return true;
            }
        }
        return false;
    }

    public Boolean notaDentroDoEscopo(Double nota1, Double nota2, Double nota3) {
        Double total = (nota1 + nota2 + nota3) / 3;
        if (total <= 10) {
            return true;
        }
        return false;
    }

    public String formatarNumero(Double num) {
        DecimalFormat df = new DecimalFormat("#.##");
        String resultado = df.format(num);
        resultado = resultado.replace(",", ".");
        return resultado;
    }



}
