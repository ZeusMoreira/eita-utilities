package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.EnumValidator;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import com.eitasutilities.cs2.exceptions.LinkInvalidoException;
import com.eitasutilities.cs2.repositories.UtilitariosRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UtilitariosValidator {
    private final UtilitariosRepository repository;
    private final YoutubeValidator youtubeValidator;

    public UtilitariosValidator(UtilitariosRepository repository, YoutubeValidator youtubeValidator) {
        this.repository = repository;
        this.youtubeValidator = youtubeValidator;
    }

    public void validar(UtilitarioDTO utilitario) {
        validarEnums(utilitario);
        validarLink(utilitario);
    }

    public void validarEnums(UtilitarioDTO utilitario) {
        EnumValidator.validarEnum(utilitario.dificuldade(), Dificuldade.class, "Dificuldade");
        EnumValidator.validarEnum(utilitario.lado(), Lado.class, "Lado");
        EnumValidator.validarEnum(utilitario.tipo(), Tipo.class, "Tipo");
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
