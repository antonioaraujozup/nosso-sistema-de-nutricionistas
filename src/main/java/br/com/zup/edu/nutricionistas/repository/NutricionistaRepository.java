package br.com.zup.edu.nutricionistas.repository;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutricionistaRepository extends JpaRepository<Nutricionista,Long> {
}
