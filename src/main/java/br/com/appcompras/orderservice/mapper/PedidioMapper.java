package br.com.appcompras.orderservice.mapper;

import br.com.appcompras.orderservice.dto.request.ItemPedidoRequest;
import br.com.appcompras.orderservice.dto.request.PedidoRequest;
import br.com.appcompras.orderservice.dto.response.ItemPedidoResponse;
import br.com.appcompras.orderservice.dto.response.PedidoResponse;
import br.com.appcompras.orderservice.enums.StatusItem;
import br.com.appcompras.orderservice.enums.StatusPedidos;
import br.com.appcompras.orderservice.model.ItemPedido;
import br.com.appcompras.orderservice.model.Pedido;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PedidioMapper {

    public Pedido toPedido(PedidoRequest request){
        Pedido pedido = Pedido
                            .builder()
                            .enderecoDeEntrega(request.enderecoEntrega())
                            .orcamentoEstimado(request.orcamentoEstimado())
                            .statusPedido(StatusPedidos.CRIADO)
                            .build();

        // Mapeia os itens já injetando a referência do pedido pai neles
        if (request.itens() != null) {
            List<ItemPedido> itens = request.itens().stream()
                    .map(itemReq -> toItemPedido (itemReq, pedido))
                    .collect(Collectors.toList());
            pedido.setItens(itens);
        }

        return pedido;
    }

    private ItemPedido toItemPedido(ItemPedidoRequest request, Pedido pedidoPai) {
        return ItemPedido.builder()
                .pedido(pedidoPai)
                .descricaoProduto(request.descricaoProduto())
                .quantidade(request.quantidade())
                .aceitaSubstituicao(request.aceitaSubstituicao())
                .statusItem(StatusItem.PENDENTE)
                .build();
    }

    public PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .idPedido(pedido.getIdPedido())
                .nomeCliente(pedido.getCliente() != null ? pedido.getCliente().getName() : null)
                .enderecoEntrega(pedido.getEnderecoDeEntrega())
                .orcamentoEstimado(pedido.getOrcamentoEstimado())
                .statusPedido(pedido.getStatusPedido().name())
                .criadoEm(pedido.getCriadoEm())
                .itens(pedido.getItens() != null ?
                        pedido.getItens().stream().map(PedidioMapper::toResponse).collect(Collectors.toList())
                        : null)
                .build();
    }

    private ItemPedidoResponse toResponse(ItemPedido item) {
        return ItemPedidoResponse.builder()
                .idItem(item.getIdItem())
                .descricaoProduto(item.getDescricaoProduto())
                .quantidade(item.getQuantidade())
                .aceitaSubstituicao(item.getAceitaSubstituicao())
                .statusItem(item.getStatusItem().name())
                .build();
    }
}

