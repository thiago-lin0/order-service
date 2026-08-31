package br.com.appcompras.orderservice.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PedidoRequest(
        UUID idCliente,
        String enderecoEntrega,
        BigDecimal orcamentoEstimado,
        List<ItemPedidoRequest> itens) {
}
