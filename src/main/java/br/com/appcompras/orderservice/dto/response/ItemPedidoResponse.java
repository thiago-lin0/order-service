package br.com.appcompras.orderservice.dto.response;

import lombok.Builder;

@Builder
public record ItemPedidoResponse(
         Long idItem,
         String descricaoProduto,
         Integer quantidade,
         Boolean aceitaSubstituicao,
         String statusItem) {
}
