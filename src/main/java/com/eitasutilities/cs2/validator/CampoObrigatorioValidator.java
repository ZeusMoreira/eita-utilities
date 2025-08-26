package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.exceptions.CampoObrigatorioVazioException;

public class CampoObrigatorioValidator {
    public static  void validarCampo(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new CampoObrigatorioVazioException(nomeCampo + " não pode ser nulo ou vazio!");
        }
    }

}
