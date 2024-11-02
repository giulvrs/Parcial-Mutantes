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
    private int consecutivesColumn;
    private final int[] consecutivesRow;
    private final int[] consecutivesLRDiagonal;
    private final int[] consecutivesRLDiagonal;
    private final int diagSize;

    public DnaPatron(String[] dna) {
        int size = dna.length;

        consecutivesColumn = 1;
        consecutivesRow = new int[size];
        consecutivesRLDiagonal = new int[2*(size - 4) + 1];
        consecutivesLRDiagonal = new int[2*(size - 4) + 1];
        diagSize = size - 4;


        Arrays.fill(consecutivesRow, 1);
        Arrays.fill(consecutivesRLDiagonal, 1);
        Arrays.fill(consecutivesLRDiagonal, 1);
    }

    public int checkColumn(String[] dna, int i, int j) {
        if(j > 0 && dna[i].charAt(j) == dna[i].charAt(j-1)) {
            consecutivesColumn++;
            if(isSequence(consecutivesColumn)) return 1;
        } else {
            consecutivesColumn = 1;
        }
        return 0;
    }

    public int checkRow(String[] dna, int i, int j) {
        if(i > 0 && dna[i].charAt(j) == dna[i-1].charAt(j)) {
            consecutivesRow[j]++;
            if(isSequence(consecutivesRow[j])) return 1;
        } else {
            consecutivesRow[j] = 1;
        }
        return 0;
    }

    public int checkDiagonalLR(String[] dna, int i, int j) {
        if(i > 0 && j > 0 && isAValidDiagonalChar(diagSize, i, j)){
            int diagElemIndex = diagSize - (i-j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j - 1)) {
                consecutivesLRDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesLRDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesLRDiagonal[diagElemIndex] = 1;
            }
        }
        return 0;
    }

    public int checkDiagonalRL(String[] dna, int i, int j) {
        if( i > 0 && j < dna.length - 1 && isAValidDiagonalChar(diagSize, (dna.length - 1) - i, j)) {
            int diagElemIndex = diagSize - ((dna.length - i - 1) - j);

            if(dna[i].charAt(j) == dna[i - 1].charAt(j + 1)) {
                consecutivesRLDiagonal[diagElemIndex]++;
                if(isSequence(consecutivesRLDiagonal[diagElemIndex])) return 1;
            }

            else {
                consecutivesRLDiagonal[diagElemIndex] = 1;
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
