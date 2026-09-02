package br.com.appcompras.orderservice.service;

import br.com.appcompras.orderservice.dto.request.AceitePedidoRequest;
import br.com.appcompras.orderservice.dto.request.ItemPedidoRequest;
import br.com.appcompras.orderservice.dto.request.PedidoRequest;
import br.com.appcompras.orderservice.dto.response.PedidoResponse;
import br.com.appcompras.orderservice.enums.StatusItem;
import br.com.appcompras.orderservice.enums.StatusPedidos;
import br.com.appcompras.orderservice.mapper.PedidioMapper;
import br.com.appcompras.orderservice.model.ItemPedido;
import br.com.appcompras.orderservice.model.Pedido;
import br.com.appcompras.orderservice.model.Usuario;
import br.com.appcompras.orderservice.repository.PedidoRepository;
import br.com.appcompras.orderservice.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request){

        Usuario cliente = usuarioRepository.findById(request.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não Encontrado!"));

        // Usando o seu Mapper estático
        Pedido novoPedido = PedidioMapper.toPedido(request);
        novoPedido.setCliente(cliente);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        return PedidioMapper.toResponse(pedidoSalvo);
    }

    // Método para listar pedidos aguardando um shopper
    public List<PedidoResponse> listarPedidosDisponiveis() {
        List<Pedido> pedidos = pedidoRepository.findByStatusPedidoOrderByCriadoEmDesc(StatusPedidos.CRIADO);

        return pedidos.stream()
                .map(PedidioMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Método para o Shopper aceitar a corrida
    @Transactional
    public PedidoResponse aceitarPedido(UUID idPedido, AceitePedidoRequest request) {

        // 1. Valida se o pedido existe
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        // 2. Garante que ninguém pegou a corrida antes
        if (pedido.getStatusPedido() != StatusPedidos.CRIADO) {
            throw new RuntimeException("Este pedido já foi aceito ou cancelado.");
        }

        // 3. Busca o Shopper no banco
        Usuario shopper = usuarioRepository.findById(request.idShopper())
                .orElseThrow(() -> new RuntimeException("Shopper não encontrado!"));

        // 4. Faz o Match!
        pedido.setShopper(shopper);
        pedido.setStatusPedido(StatusPedidos.EM_ANDAMENTO);

        // 5. Salva e retorna
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return PedidioMapper.toResponse(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponse atualizarStatusItem(UUID idPedido, Long idItem, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        // Busca o item específico dentro da lista do pedido
        ItemPedido item = pedido.getItens().stream()
                .filter(i -> i.getIdItem().equals(idItem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado neste pedido!"));

        // Atualiza o status (ex: de PENDENTE para ENCONTRADO)
        item.setStatusItem(StatusItem.valueOf(novoStatus.toUpperCase()));

        return PedidioMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse irParaOCaixa(UUID idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        if (pedido.getStatusPedido() != StatusPedidos.EM_ANDAMENTO) {
            throw new RuntimeException("O pedido precisa estar em andamento para ir ao caixa.");
        }

        pedido.setStatusPedido(StatusPedidos.NO_CAIXA);
        return PedidioMapper.toResponse(pedidoRepository.save(pedido));
    }

    public PedidoResponse buscarPorId(UUID idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
        return PedidioMapper.toResponse(pedido);
    }

}
