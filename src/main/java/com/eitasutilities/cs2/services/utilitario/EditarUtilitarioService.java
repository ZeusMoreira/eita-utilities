package com.eitasutilities.cs2.services.utilitario;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.exceptions.NaoEncontradoException;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.validator.UtilitarioValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EditarUtilitarioService {

    private final UtilitarioRepository repository;
    private final UtilitarioValidator validator;

    public EditarUtilitarioService(UtilitarioRepository repository, UtilitarioValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Utilitario editar(String id, UtilitarioDTO utilitarioDTO) {
        UUID idValido = validarEhGerarUUID(id);
        Utilitario utilitario = validarDadosUtilitario(idValido, utilitarioDTO);

        return salvar(utilitario);
    }

    public Utilitario salvar(Utilitario utilitarioEntidade) {
        return repository.save(utilitarioEntidade);
    }

    private UUID validarEhGerarUUID(String id) {
        validator.validarId(id);
        return UUID.fromString(id);
    }

    private Utilitario validarExistenciaUtilitario(UUID id) {
        Optional<Utilitario> utilitarioEntidade = repository.findById(id);

        if(utilitarioEntidade.isEmpty()) {
            throw new NaoEncontradoException("Não existe utilitário relacionado ao Id: " + id);
        }

        return utilitarioEntidade.get();
    }

    private Utilitario validarDadosUtilitario(UUID idValido, UtilitarioDTO utilitarioDTO) {
        Utilitario utilitarioEncontrado = validarExistenciaUtilitario(idValido);

        Utilitario utilitario = validator.validarDTO(idValido, utilitarioDTO);
        utilitario.setId(utilitarioEncontrado.getId());
        utilitario.setDataCriacao(utilitarioEncontrado.getDataCriacao());

        return utilitario;
    }

}
