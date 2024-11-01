package org.parcial.ranzuglia.services;

import org.parcial.ranzuglia.dtos.MutanteDto;
import org.parcial.ranzuglia.entities.Mutante;
import org.parcial.ranzuglia.repository.MutanteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutanteServiceTest {
    @Mock
    private MutanteRepository mutanteRepository;

    @InjectMocks
    private MutanteService mutanteService;

    private void testHelper(String[] dna, boolean expectedResult) {
        try {
            Assertions.assertEquals(expectedResult,mutanteService.isMutant(dna));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


 

    @Test
    void test4x4MatrixIsMutantByColAndDiagonal(){
        String[] dna = {
                "AAAA",
                "AAGC",
                "CTAC",
                "AAGA"
        };
        testHelper(dna, true);
    }

    @Test
    void test4x4MatrixIsMutantByRows(){
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAGC"
        };
        testHelper(dna, true);
    }

      @Test
    void test4x4MatrixIsNotMutant(){
        String[] dna = {
                "ATGC",
                "CAGT",
                "TCAT",
                "AGCB",
        };
        testHelper(dna, false);
    }

       @Test
    void test6x6MatrixIsMutantByDiagonals(){

        String[] dna = {
                "ATAAGA",
                "CATCGC",
                "GCATGT",
                "AGCAGG",
                "CCACBC",
                "GTACCA"
        };

        testHelper(dna, true);

    }

    @Test
    void test6x6MatrixIsNotMutant(){
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TCATGT",
                "AGCBAG",
                "CCADBC",
                "TCACTA"
        };
        testHelper(dna, false);
    }


    @Test
    void testLessThan4DimMatrixIsNotMutant() {
        String[] dna = {
                "TCG",
                "CAT",
                "GGA",
        };
        testHelper(dna, false);
    }

    @Test
    void test6x6MatrixIsMutantByRightToLeftDiagonal(){
        String[] dna = {
                "ATGCGA",
                "ATGAAC",
                "ATAAGT",
                "ABABAG",
                "CCTABC",
                "TCBTAA"
        };
        testHelper(dna, true);
    }

    //Test analyzeDna() method

    @Test
    void testDnaAnalyzerWithMutantDna() throws Exception{
        String[] dna = {
                "AAAA",
                "ATGA",
                "ATAA",
                "ABAB",
        };

        MutanteDto mutanteDto = new MutanteDto();
        mutanteDto.setDna(dna);

        when(mutanteRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertEquals(true, mutanteService.analyzeDna(mutanteDto));

    }

    @Test
    void testDnaAnalyzerWithHumanDna4x4Matrix() throws Exception{
        String[] dna = {
                "AAAB",
                "ATGA",
                "ATAA",
                "ABAB",
        };

        MutanteDto mutanteDto = new MutanteDto();
        mutanteDto.setDna(dna);

        when(mutanteRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {mutanteService.analyzeDna(mutanteDto);});

    }

    @Test
    void testDnaAnalyzerWithNLessThan4Dna() throws Exception{
        String[] dna = {
                "AGT",
                "AAA",
                "ATA",
        };

        MutanteDto mutanteDto = new MutanteDto();
        mutanteDto.setDna(dna);

        when(mutanteRepository.findByDna(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(Exception.class, () -> {mutanteService.analyzeDna(mutanteDto);});

    }

    @Test
    void testDnaAnalyzerAlreadyInDB() throws Exception{
        String[] dna = {
                "AGT",
                "AAA",
                "ATA",
        };

        MutanteDto mutanteDto = new MutanteDto();
        mutanteDto.setDna(dna);

        Mutante mutante = new Mutante();
        mutante.setMutant(true);

        when(mutanteRepository.findByDna(any())).thenReturn(Optional.of(mutante));
        Assertions.assertTrue(mutanteService.analyzeDna(mutanteDto));

    }
}
