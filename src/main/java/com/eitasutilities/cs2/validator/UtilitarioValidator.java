package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import com.eitasutilities.cs2.exceptions.LinkInvalidoException;
import com.eitasutilities.cs2.exceptions.UuidException;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
public class UtilitarioValidator {
    private final UtilitarioRepository repository;
    private final YoutubeValidator youtubeValidator;

    public UtilitarioValidator(UtilitarioRepository repository, YoutubeValidator youtubeValidator) {
        this.repository = repository;
        this.youtubeValidator = youtubeValidator;
    }

    public void validarId(String id) {
        if(id == null || id.isBlank()) {
            CampoObrigatorioValidator.validarCampo(id, "Id");
        } else {
            validarUuid(id);
        }
    }

    private void validarUuid(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new UuidException("O id informado não é um UUID válido!");
        }
    }


    public void validarDTO(UtilitarioDTO utilitario) {
        validarEnums(utilitario);
        validarCampos(utilitario);
        validarLink(utilitario);
    }

    public void validarEnums(UtilitarioDTO utilitario) {
        EnumValidator.validarEnum(utilitario.dificuldade(), Dificuldade.class, "Dificuldade");
        EnumValidator.validarEnum(utilitario.lado(), Lado.class, "Lado");
        EnumValidator.validarEnum(utilitario.tipo(), Tipo.class, "Tipo");
    }

    public void validarCampos(UtilitarioDTO utilitario) {
        CampoObrigatorioValidator.validarCampo(utilitario.mapa(), "Mapa");
        CampoObrigatorioValidator.validarCampo(utilitario.titulo(), "Título");
    }

    private void validarYoutube(String link) {
        validarHostYoutube(link);
        youtubeValidator.validarVideoExistente(link);
    }

    private void validarHostYoutube(String link) {
        try {
            URI uri = new URI(link);
            String host = uri.getHost();
            if (host == null ||
                    !(host.equalsIgnoreCase("youtube.com") ||
                            host.equalsIgnoreCase("www.youtube.com") ||
                            host.equalsIgnoreCase("youtu.be"))) {
                throw new LinkInvalidoException("O link informado não pertence ao YouTube.");
            }
        } catch (URISyntaxException e) {
            throw new LinkInvalidoException("O link informado não é uma URL válida.");
        }
    }

    public void validarLink(UtilitarioDTO utilitario) {
        if(utilitario.link() == null || utilitario.link().isBlank()) {
            throw new LinkInvalidoException("O campo referente ao link deve ser preenchido!");
        }

        repository.findByLink(utilitario.link())
                .filter(u -> utilitario.id() == null || !utilitario.id().equals(u.getId()))
                .ifPresent(u -> {
                    throw new LinkInvalidoException("Já existe um vídeo vinculado ao link informado!");
                });

        validarYoutube(utilitario.link());
    }

}
