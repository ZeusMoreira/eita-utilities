package com.eitasutilities.cs2.validator;

import com.eitasutilities.cs2.exceptions.CampoObrigatorioVazioException;
import com.eitasutilities.cs2.exceptions.EnumInvalidaException;
import org.springframework.stereotype.Component;

@Component
public class EnumValidator {

    public static <E extends Enum<E>> void validarEnum(String valor, Class<E> enumClass, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new CampoObrigatorioVazioException(nomeCampo + " não pode ser nulo ou vazio!");
        }

        try {
            Enum.valueOf(enumClass, valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumInvalidaException(nomeCampo + " informado(a) não é válido(a)!");
        }
    }

}
