package br.com.appcompras.orderservice.dto.request;

import java.util.UUID;

public record AceitePedidoRequest(
        UUID idShopper
) {
}
