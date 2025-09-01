package com.eitasutilities.cs2.controller.utilitario;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.services.utilitario.CriarUtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilitarios")
@Tag(name = "Criar Utilitário", description = "Operação relacionada a criação de utilitários")
public class CriarUtilitarioController {

    private final CriarUtilitarioService service;

    @Operation(
            summary = "Cria um novo utilitário",
            description = "Recebe os dados do utilitário e cria um novo registro.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilitário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "422", description = "Há dados obrigatórios não preenchidos")
            }
    )
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid UtilitarioDTO utilitario) {
        Utilitario output = service.salvar(utilitario);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
