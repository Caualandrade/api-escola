package com.caualandrade.apiescola.model;

import com.caualandrade.apiescola.infra.StatusAluno;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "matricula")
@Table(name = "tb_matricula")
public class MatriculaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    //@JsonBackReference
    @JsonIgnore
    private AlunoModel aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    //@JsonBackReference
    @JsonIgnore
    private DisciplinaModel disciplina;

    private Float nota;

    private StatusAluno situacao;

    public MatriculaModel() {
    }

    public MatriculaModel(Long id, AlunoModel aluno, DisciplinaModel disciplina, Float nota, StatusAluno situacao) {
        this.id = id;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public StatusAluno getSituacao() {
        return situacao;
    }

    public void setSituacao(StatusAluno situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatriculaModel that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
