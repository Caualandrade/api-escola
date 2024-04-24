package com.caualandrade.apiescola.model;

import com.caualandrade.apiescola.infra.StatusAluno;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "matricula")
@Table(name = "matriculas")
public class MatriculaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    @JsonIgnore
    private AlunoModel aluno;

    @ManyToOne
    @JoinColumn(name = "id_disciplina")
    @JsonIgnore
    private DisciplinaModel disciplina;

    private Double primeiraNota;

    private Double segundaNota;

    private Double terceiraNota;

    private Double totalNota;

    private StatusAluno status;

    public MatriculaModel() {
    }

    public MatriculaModel(Long id, AlunoModel aluno, DisciplinaModel disciplina, Double primeiraNota, Double segundaNota, Double terceiraNota, Double totalNota, StatusAluno status) {
        this.id = id;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.primeiraNota = primeiraNota;
        this.segundaNota = segundaNota;
        this.terceiraNota = terceiraNota;
        this.totalNota = totalNota;
        this.status = status;
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

    public Double getPrimeiraNota() {
        return primeiraNota;
    }

    public void setPrimeiraNota(Double primeiraNota) {
        this.primeiraNota = primeiraNota;
    }

    public Double getSegundaNota() {
        return segundaNota;
    }

    public void setSegundaNota(Double segundaNota) {
        this.segundaNota = segundaNota;
    }

    public Double getTerceiraNota() {
        return terceiraNota;
    }

    public void setTerceiraNota(Double terceiraNota) {
        this.terceiraNota = terceiraNota;
    }

    public Double getTotalNota() {
        return totalNota;
    }

    public void setTotalNota(Double totalNota) {
        this.totalNota = totalNota;
    }

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        this.status = status;
    }

    public Double calcularNotaTotal(Double primeiraNota, Double segundaNota, Double terceiraNota){
        var notaTotal = (primeiraNota + segundaNota + terceiraNota)/3;
        if(notaTotal>=6){
            this.setStatus(StatusAluno.valueOf("APROVADO"));
        }
        return notaTotal;
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
