package com.caualandrade.apiescola.dto.matricula;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatriculaNotasDTO (
                                 @NotNull Double nota1,
                                 @NotNull Double nota2,
                                 @NotNull Double nota3){
}
