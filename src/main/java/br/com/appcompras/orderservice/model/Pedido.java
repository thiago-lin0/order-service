package br.com.appcompras.orderservice.model;

import br.com.appcompras.orderservice.enums.StatusPedidos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_shopper")
    private Usuario shopper; // fica nulo até alguem aceitar a corrida

    @Column(nullable = false)
    private String enderecoDeEntrega;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal orcamentoEstimado;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFinalPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedidos statusPedido = StatusPedidos.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;



}
