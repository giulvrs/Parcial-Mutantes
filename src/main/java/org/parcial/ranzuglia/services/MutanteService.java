package org.parcial.ranzuglia.services;

import org.parcial.ranzuglia.dtos.MutanteDto;
import org.parcial.ranzuglia.entities.Mutante;
import org.parcial.ranzuglia.repository.MutanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

import static java.lang.Math.abs;

@Service
public class MutanteService {
    @Autowired
    private MutanteRepository mutanteRepository;

    @Transactional
    public boolean analyzeDna(MutanteDto mutanteDto) throws Exception{
        String[] dna = mutanteDto.getDna();
        Mutante mutante;

        Optional<Mutante> mutantePruebaOptional = mutanteRepository.findByDna(dna);
        if(mutantePruebaOptional.isPresent()) {
            mutante = mutantePruebaOptional.get();
            mutante.setCount(mutante.getCount() + 1);
            mutanteRepository.save(mutante);

            if(mutante.isMutant()) return true;
            throw new Exception("No es mutante");
        }

        mutante = new Mutante();
        mutante.setCount(1);
        mutante.setDna(dna);

        if(dna.length < 4) {
            mutante.setMutant(false);
            mutanteRepository.save(mutante);
            throw new Exception("No es mutante");
        }

        mutante.setMutant(isMutant(dna));
        mutanteRepository.save(mutante);

        if(mutante.isMutant()) return true;
        throw new Exception("No es mutante");
    }

    public boolean isMutant(String[] dna) {
        int countSequence = 0;

        final DnaPatron dnaPatron = new DnaPatron(dna);


        for(int i = 0; i < dna.length; i++) {
            for(int j = 0; j < dna[i].length(); j++) {
                countSequence += dnaPatron.checkColumn(dna, i, j);
                countSequence += dnaPatron.checkRow(dna, i, j);
                countSequence += dnaPatron.checkDiagonalLR(dna, i, j);
                countSequence += dnaPatron.checkDiagonalRL(dna, i, j);
                if(countSequence > 1) return true;
            }
        }
        return false;
    }

}

class DnaPatron {
    private int consecutivesByCol;
    private final int[] consecutivesByRow;
    private final int[] consecutivesByLRDiagonal;
    private final int[] consecutivesByRLDiagonal;
    private final int diagRadius;

    public DnaPatron(String[] dna) {
        int size = dna.length;

        consecutivesByCol = 1;
        consecutivesByRow = new int[size];
        consecutivesByRLDiagonal = new int[2*(size - 4) + 1];
        consecutivesByLRDiagonal = new int[2*(size - 4) + 1];
        diagRadius = size - 4;


        Arrays.fill(consecutivesByRow, 1);
        Arrays.fill(consecutivesByRLDiagonal, 1);
        Arrays.fill(consecutivesByLRDiagonal, 1);
    }

    public int checkColumn(String[] dna, int i, int j) {
        if(j > 0 && dna[i].charAt(j) == dna[i].charAt(j-1)) {
            consecutivesByCol++;
            if(isSequence(consecutivesByCol)) return 1;
        } else {
            consecutivesByCol = 1;
        }
        return 0;
    }

    public int checkRow(String[] dna, int i, int j) {
        if(i > 0 && dna[i].charAt(j) == dna[i-1].charAt(j)) {
            consecutivesByRow[j]++;
            if(isSequence(consecutivesByRow[j])) return 1;
        } else {
            consecutivesByRow[j] = 1;
        }
        return 0;
    }

    public int checkDiagonalLR(String[] dna, int i, int j) {
        if(i > 0 && j > 0 && isAValidDiagonalChar(diagRadius, i, j)){
            int diagElemIndex = diagRadius - (i-j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j - 1)) {
                consecutivesByLRDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesByLRDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesByLRDiagonal[diagElemIndex] = 1;
            }
        }
        return 0;
    }

    public int checkDiagonalRL(String[] dna, int i, int j) {
        if( i > 0 && j < dna.length - 1 && isAValidDiagonalChar(diagRadius, (dna.length - 1) - i, j)) {
            int diagElemIndex = diagRadius - ((dna.length - i - 1) - j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j + 1)) {
                consecutivesByRLDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesByRLDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesByRLDiagonal[diagElemIndex] = 1;
            }

        }
        return 0;
    }


    private static boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }

    private static boolean isAValidDiagonalChar(int radius, int refPoint, int actPoint) {
        return abs(refPoint - actPoint) <= radius;
    }


}
