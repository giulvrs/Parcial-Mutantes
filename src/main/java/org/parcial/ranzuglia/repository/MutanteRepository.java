package org.parcial.ranzuglia.repository;

import org.parcial.ranzuglia.entities.Mutante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MutanteRepository extends JpaRepository<Mutante, Long> {
    Optional<Mutante> findByDna(String[] dna);

    @Query("SELECT SUM(m.count) FROM Mutante m WHERE m.isMutant = ?1")
    Long sumByCountAndIsMutantEqual(boolean isMutant);

}
