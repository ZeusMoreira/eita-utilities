package com.eitasutilities.cs2.services.utilitario;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.validator.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarUtilitarioService {

    private final UtilitarioRepository repository;

    public List<Utilitario> buscar(String lado,
                                   String mapa,
                                   String titulo,
                                   String tipo,
                                   String dificuldade) {

        Utilitario utilitario = new Utilitario();
        utilitario.setDificuldade(mapearDificuldade(dificuldade));
        utilitario.setTipo(mapearTipo(tipo));
        utilitario.setLado(mapearLado(lado));
        utilitario.setMapa(mapa);
        utilitario.setTitulo(titulo);

        Example<Utilitario> utilitarioExample = Example.of(utilitario, criarMatcher());
        return repository.findAll(utilitarioExample);
    }

    private ExampleMatcher criarMatcher() {
        return ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    }

    private Dificuldade mapearDificuldade(String dificuldade) {
        if (dificuldade == null || dificuldade.isBlank()) return null;
        EnumValidator.validarEnum(dificuldade, Dificuldade.class, "Dificuldade");
        return Dificuldade.valueOf(dificuldade.toUpperCase());
    }

    private Tipo mapearTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) return null;
        EnumValidator.validarEnum(tipo, Tipo.class, "Tipo");
        return Tipo.valueOf(tipo.toUpperCase());
    }

    private Lado mapearLado(String lado) {
        if (lado == null || lado.isBlank()) return null;
        EnumValidator.validarEnum(lado, Lado.class, "Lado");
        return Lado.valueOf(lado.toUpperCase());
    }

}
