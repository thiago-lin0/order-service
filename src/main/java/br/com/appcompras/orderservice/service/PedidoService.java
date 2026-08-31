package br.com.appcompras.orderservice.service;

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

}
