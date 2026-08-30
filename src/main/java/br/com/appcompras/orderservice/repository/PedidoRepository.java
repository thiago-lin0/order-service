package br.com.appcompras.orderservice.repository;

import br.com.appcompras.orderservice.enums.StatusPedidos;
import br.com.appcompras.orderservice.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    // O Spring monta a query SQL sozinho pelo nome do método!
    // Retorna todos os pedidos que estão com status CRIADO (para o shopper ver no app)
    List<Pedido> findByStatusPedidoOrderByCriadoEmDesc(StatusPedidos statusPedido);
}
