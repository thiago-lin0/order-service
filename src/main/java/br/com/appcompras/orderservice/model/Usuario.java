package br.com.appcompras.orderservice.model;

import br.com.appcompras.orderservice.enums.StatusConta;
import br.com.appcompras.orderservice.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUsuario;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 100)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPerfil tipoPerfil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConta statusConta = StatusConta.ATIVO;
}
