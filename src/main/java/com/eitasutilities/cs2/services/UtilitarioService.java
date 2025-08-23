package com.eitasutilities.cs2.services;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.repositories.UtilitariosRepository;
import com.eitasutilities.cs2.validator.UtilitariosValidator;
import org.springframework.stereotype.Service;

@Service
public class UtilitarioService {
    private final UtilitariosRepository repository;

    private final UtilitariosValidator validator;

    public UtilitarioService(UtilitariosRepository repository, UtilitariosValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Utilitario salvar(UtilitarioDTO utilitario) {
        validator.validar(utilitario);
        Utilitario utilitarioEntidade = utilitario.mapearParaUtilitario();
        return repository.save(utilitarioEntidade);
    }


}
