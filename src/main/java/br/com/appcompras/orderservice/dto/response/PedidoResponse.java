package br.com.appcompras.orderservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record PedidoResponse(
        UUID idPedido,
        String nomeCliente, // Evita retornar o objeto Usuario inteiro
        String enderecoEntrega,
        BigDecimal orcamentoEstimado,
        String statusPedido,
        LocalDateTime criadoEm,
        List<ItemPedidoResponse>itens
) {
}
