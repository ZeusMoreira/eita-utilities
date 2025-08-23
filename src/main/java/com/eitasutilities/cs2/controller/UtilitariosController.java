package com.eitasutilities.cs2.controller;

import com.eitasutilities.cs2.controller.dto.UtilitarioDTO;
import com.eitasutilities.cs2.entities.Utilitario;
import com.eitasutilities.cs2.services.UtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilitarios")
public class UtilitariosController {
    private final UtilitarioService service;

    public UtilitariosController(UtilitarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cria um novo utilitário",
            description = "Recebe os dados do utilitário e cria um novo registro.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody UtilitarioDTO utilitario) {
        Utilitario output = service.salvar(utilitario);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }

}
