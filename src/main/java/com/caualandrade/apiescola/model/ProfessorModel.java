package com.caualandrade.apiescola.model;

import com.caualandrade.apiescola.dto.disciplina.DisciplinaDadosParaProfessorDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SimpleTimeZone;

@Entity(name = "professor")
@Table(name = "professores")
public class ProfessorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<DisciplinaModel> disciplinas;

    public ProfessorModel() {
    }

    public ProfessorModel(Long id, String nome, String email, List<DisciplinaModel> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.disciplinas = disciplinas;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DisciplinaModel> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<DisciplinaModel> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<String> nomeDasDisciplinas(){
        List<String> nomeDasDisciplinas = new ArrayList<>();
        for (DisciplinaModel disciplina : disciplinas) {
            nomeDasDisciplinas.add(disciplina.getNome());
        }
        return nomeDasDisciplinas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessorModel that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
