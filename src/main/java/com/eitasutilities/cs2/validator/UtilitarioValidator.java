package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.entities.enums.Dificuldade;
import com.eitasutilities.cs2.entities.enums.Lado;
import com.eitasutilities.cs2.entities.enums.Tipo;
import com.eitasutilities.cs2.exceptions.LinkInvalidoException;
import com.eitasutilities.cs2.exceptions.UuidException;
import com.eitasutilities.cs2.repositories.UtilitarioRepository;
import com.eitasutilities.cs2.utils.YouTubeUtils;
import org.springframework.stereotype.Component;

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

    public Utilitario validarDTO(UUID id, UtilitarioDTO utilitarioDTO) {
        validarEnums(utilitarioDTO);
        validarCampos(utilitarioDTO);
        YouTubeUtils.validarHostYoutube(utilitarioDTO.link());
        Utilitario utilitario = utilitarioDTO.mapearParaUtilitario();
        normalizarLink(utilitario);
        UtilitarioDTO dtoNormalizado = utilitarioDTO.mapearParaUtilitarioDTO(utilitario);
        validarLink(id, dtoNormalizado);
        return utilitario;
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
        youtubeValidator.validarVideoExistente(link);
    }
    public void validarLink(UUID id, UtilitarioDTO utilitario) {
        repository.findByLink(utilitario.link())
                .filter(u -> id == null || !id.equals(u.getId()))
                .ifPresent(u -> {
                    throw new LinkInvalidoException("Já existe um vídeo vinculado ao link informado!");
                });

        validarYoutube(utilitario.link());
    }

    private void normalizarLink(Utilitario utilitario) {
        String linkNormalizado = YouTubeUtils.normalizarLink(utilitario.getLink());
        utilitario.setLink(linkNormalizado);
    }
}
