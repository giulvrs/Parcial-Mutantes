package org.parcial.ranzuglia.dtos;

import org.parcial.ranzuglia.validations.ValidDna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MutanteDto {
    @ValidDna
    private String[] dna;
}
