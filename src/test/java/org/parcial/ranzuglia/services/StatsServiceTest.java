package org.parcial.ranzuglia.services;

import org.parcial.ranzuglia.dtos.StatsDto;
import org.parcial.ranzuglia.repository.MutanteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
    @Mock
    private MutanteRepository mutanteRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testWithPositiveNumbers() {
        when(mutanteRepository.sumByCountAndIsMutantEqual(true)).thenReturn(1L);
        when(mutanteRepository.sumByCountAndIsMutantEqual(false)).thenReturn(2L);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(2, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(1, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals((float)1/2, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullHumanCount() {
        when(mutanteRepository.sumByCountAndIsMutantEqual(true)).thenReturn(1L);
        when(mutanteRepository.sumByCountAndIsMutantEqual(false)).thenReturn(null);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(0, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(1, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(1, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullMutantCount() {
        when(mutanteRepository.sumByCountAndIsMutantEqual(true)).thenReturn(null);
        when(mutanteRepository.sumByCountAndIsMutantEqual(false)).thenReturn(2L);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(2, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(0, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(0, responseStatsDto.getRatio());
    }

    @Test
    void testWithNullMutantCountAndNullHumanCount() {
        when(mutanteRepository.sumByCountAndIsMutantEqual(true)).thenReturn(null);
        when(mutanteRepository.sumByCountAndIsMutantEqual(false)).thenReturn(null);

        StatsDto responseStatsDto = statsService.getStats();

        Assertions.assertEquals(0, responseStatsDto.getCountHumanDna());
        Assertions.assertEquals(0, responseStatsDto.getCountMutantDna());
        Assertions.assertEquals(0, responseStatsDto.getRatio());
    }


}
