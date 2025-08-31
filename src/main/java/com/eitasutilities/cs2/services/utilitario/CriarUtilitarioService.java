package com.eitasutilities.cs2.services.utilitario;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.validator.UtilitarioValidator;
import org.springframework.stereotype.Service;

@Service
public class CriarUtilitarioService {

    private final UtilitarioRepository repository;
    private final UtilitarioValidator validator;

    public CriarUtilitarioService(UtilitarioRepository repository, UtilitarioValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Utilitario salvar(UtilitarioDTO dto) {
        Utilitario utilitario = validator.validarDTO(null, dto);
        return repository.save(utilitario);
    }
}
