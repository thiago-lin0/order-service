package br.com.appcompras.orderservice.model;

import br.com.appcompras.orderservice.enums.StatusItem;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "itens_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String descricaoProduto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Boolean aceitaSubstituicao = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusItem statusItem = StatusItem.PENDENTE;
}
