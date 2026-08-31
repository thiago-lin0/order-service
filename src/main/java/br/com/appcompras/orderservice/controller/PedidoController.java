package br.com.appcompras.orderservice.controller;

import br.com.appcompras.orderservice.dto.request.PedidoRequest;
import br.com.appcompras.orderservice.dto.response.PedidoResponse;
import br.com.appcompras.orderservice.mapper.PedidioMapper;
import br.com.appcompras.orderservice.model.Pedido;
import br.com.appcompras.orderservice.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest request){
        PedidoResponse response = pedidoService.criarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
