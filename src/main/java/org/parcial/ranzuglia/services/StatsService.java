package org.parcial.ranzuglia.services;

import org.parcial.ranzuglia.dtos.StatsDto;
import org.parcial.ranzuglia.repository.MutanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private MutanteRepository mutanteRepository;

    @Autowired
    public StatsService(MutanteRepository mutanteRepository) {
        this.mutanteRepository = mutanteRepository;
    }

    public StatsDto getStats() {
        Long countMutantTests = mutanteRepository.sumByCountAndIsMutantEqual(true);
        Long countHumanTests = mutanteRepository.sumByCountAndIsMutantEqual(false);

        if(countHumanTests == null) countHumanTests = 0L;
        if(countMutantTests == null) countMutantTests = 0L;

        StatsDto statsDto = new StatsDto();
        statsDto.setCountMutantDna(countMutantTests);
        statsDto.setCountHumanDna(countHumanTests);
        statsDto.setRatio(countHumanTests == 0 ? countMutantTests : (float) countMutantTests / countHumanTests);

        return statsDto;
    }
}
