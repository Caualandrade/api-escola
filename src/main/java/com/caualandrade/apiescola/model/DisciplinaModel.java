package com.caualandrade.apiescola.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "disciplina")
@Table(name = "disciplinas")
public class DisciplinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    @JsonIgnore
    private ProfessorModel professor;

    @OneToMany(mappedBy = "disciplina")
    private List<MatriculaModel> matricula;

    public DisciplinaModel() {
    }

    public DisciplinaModel(Long id, String nome, ProfessorModel professor, List<MatriculaModel> matricula) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
        this.matricula = matricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ProfessorModel getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorModel professor) {
        this.professor = professor;
    }

    public List<MatriculaModel> getMatricula() {
        return matricula;
    }

    public void setMatricula(List<MatriculaModel> matricula) {
        this.matricula = matricula;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisciplinaModel that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
