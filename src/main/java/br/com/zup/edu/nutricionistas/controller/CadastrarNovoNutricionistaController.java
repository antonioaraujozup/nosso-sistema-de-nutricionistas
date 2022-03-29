package br.com.zup.edu.nutricionistas.controller;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import br.com.zup.edu.nutricionistas.repository.NutricionistaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/nutricionistas")
public class CadastrarNovoNutricionistaController {

    private final NutricionistaRepository repository;

    public CadastrarNovoNutricionistaController(NutricionistaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid NutricionistaRequest request, UriComponentsBuilder uriComponentsBuilder) {

        Nutricionista novoNutricionista = request.paraNutricionista();

        repository.save(novoNutricionista);

        URI location = uriComponentsBuilder
                .path("/nutricionistas/{id}")
                .buildAndExpand(novoNutricionista.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
