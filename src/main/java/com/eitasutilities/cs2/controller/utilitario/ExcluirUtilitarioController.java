package com.eitasutilities.cs2.controller.utilitario;

import com.eitasutilities.cs2.services.utilitario.ExcluirUtilitarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilitarios")
@Tag(name = "Excluir Utilitário", description = "Operação relacionada a exclusão de utilitários")
public class ExcluirUtilitarioController {

    private final ExcluirUtilitarioService service;

    @Operation(
            summary = "Excluir um utilitário",
            description = "Recebe o id de um utilitário e deleta do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Utilitário excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Object> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
