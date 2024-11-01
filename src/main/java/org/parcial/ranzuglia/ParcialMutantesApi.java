package org.parcial.ranzuglia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParcialMutantesApi {

	public static void main(String[] args) {
		SpringApplication.run(ParcialMutantesApi.class, args);
	}

			/*
			List<String> dna_example = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
			List<String> dna_example2 = Arrays.asList(
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA",
					"AAAAAAAAAA");
			List<String> dna_example3 = Arrays.asList("ATGCGAA", "CAGTGCA", "TTATGTA", "AGAAGGA", "CCCCTAA", "TCACTGA", "AAAAAAA"); //no reompe
			List<String> dna_example4 = Arrays.asList("CACAGCAATC", "GGCACTGTGT'", "CGCTGCAGCG", "GTTCATGCTG", "GCAAGAAGAA", "TCTACGTATC", "GAAGACGCCT", "TCCAGTCGTA", "CCTAGATATC", "GCTCAGCGAG");


			Mutante mutante1 = 	Mutante.builder()
					.dna(dna_example)
					.mutant(personaService.isMutant(dna_example))
					.build();
			Persona persona2 = Persona.builder()
					.dna(dna_example2)
					.mutant(personaService.isMutant(dna_example2))
					.build();
			Persona persona3 = Persona.builder()
					.dna(dna_example3)
					.mutant(personaService.isMutant(dna_example3))
					.build();
			Persona persona4 = Persona.builder()
					.dna(dna_example4)
					.mutant(personaService.isMutant(dna_example4))
					.build();

			personaRepository.save(persona1);
			personaRepository.save(persona2);
			personaRepository.save(persona3);
			personaRepository.save(persona4);
			*/
}
