package br.com.appcompras.orderservice.controller;

import br.com.appcompras.orderservice.dto.request.AceitePedidoRequest;
import br.com.appcompras.orderservice.dto.request.PedidoRequest;
import br.com.appcompras.orderservice.dto.response.PedidoResponse;
import br.com.appcompras.orderservice.mapper.PedidioMapper;
import br.com.appcompras.orderservice.model.Pedido;
import br.com.appcompras.orderservice.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/disponiveis")
    public ResponseEntity<List<PedidoResponse>> listarDisponiveis() {
        List<PedidoResponse> disponiveis = pedidoService.listarPedidosDisponiveis();
        return ResponseEntity.ok(disponiveis);
    }

    @PutMapping("/{idPedido}/aceitar")
    public ResponseEntity<PedidoResponse> aceitarPedido(
            @PathVariable UUID idPedido,
            @RequestBody AceitePedidoRequest request) {

        PedidoResponse response = pedidoService.aceitarPedido(idPedido, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{idPedido}/itens/{idItem}")
    public ResponseEntity<PedidoResponse> atualizarItem(
            @PathVariable UUID idPedido,
            @PathVariable Long idItem,
            @RequestParam String status) {

        PedidoResponse response = pedidoService.atualizarStatusItem(idPedido, idItem, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{idPedido}/caixa")
    public ResponseEntity<PedidoResponse> irParaOCaixa(@PathVariable UUID idPedido) {

        PedidoResponse response = pedidoService.irParaOCaixa(idPedido);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable UUID idPedido) {
        PedidoResponse response = pedidoService.buscarPorId(idPedido);
        return ResponseEntity.ok(response);
    }
}
