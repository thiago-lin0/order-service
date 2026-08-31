package br.com.appcompras.orderservice.dto.request;

public record ItemPedidoRequest(
        String descricaoProduto,
        Integer quantidade,
        Boolean aceitaSubstituicao) {
}
