package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificar")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(
            summary = "Enviar una notificación",
            description = "Permite a un usuario o bibliotecario enviar una notificación. "
                    + "La notificación puede ser un mensaje relacionado con préstamos, devoluciones o recordatorios."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (requiere ROLE_USER o ROLE_LIBRARIAN)",
                    content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public void enviarNotificacion(
            @Parameter(
                    description = "Objeto con la información de la notificación a enviar",
                    required = true,
                    schema = @Schema(implementation = NotificacionDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de notificación",
                                    value = "{ \"usuarioId\": \"12345\", \"mensaje\": \"Recuerde devolver el libro mañana\" }"
                            )
                    }
            )
            @RequestBody NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
    }
}
