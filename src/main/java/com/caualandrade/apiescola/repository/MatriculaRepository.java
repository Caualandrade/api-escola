package com.caualandrade.apiescola.repository;

import com.caualandrade.apiescola.model.MatriculaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<MatriculaModel,Long> {
}
