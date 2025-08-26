package com.eitasutilities.cs2.services.utilitario;

import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.exceptions.UuidException;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.validator.UtilitarioValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ExcluirUtilitarioService {
    private final UtilitarioRepository repository;

    private final UtilitarioValidator validator;

    public ExcluirUtilitarioService(UtilitarioRepository repository, UtilitarioValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void excluir(String id) {
        validator.validarId(id);
        UUID idValido = UUID.fromString(id);
        if(ehIdVinculadoAhUmUtilitario(idValido)) {
            repository.deleteById(idValido);
        }
    }

    private boolean ehIdVinculadoAhUmUtilitario(UUID id) {
        Optional<Utilitario> utilitario = repository.findById(id);

        if(utilitario.isEmpty()) {
            throw new UuidException("Não existe utilitário relacionado ao Id: " + id);
        }

        return true;
    }

}
