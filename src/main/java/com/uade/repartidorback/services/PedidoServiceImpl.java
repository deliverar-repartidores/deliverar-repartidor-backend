package com.uade.repartidorback.services;

import com.uade.repartidorback.entities.Orden;
import com.uade.repartidorback.entities.User;
import com.uade.repartidorback.enums.EstadoEnum;
import com.uade.repartidorback.models.InfoResponse;
import com.uade.repartidorback.models.LoginRequest;
import com.uade.repartidorback.repositories.PedidoRepository;
import com.uade.repartidorback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public ResponseEntity obtenerPedidos () {
        List<Orden> pedidosDisponibles = pedidoRepository.findPedidosByStatus(EstadoEnum.DISPONIBLE.name());
        String message = "Pedidos encontrados";
        if(pedidosDisponibles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new InfoResponse(HttpStatus.NOT_FOUND.value(), pedidosDisponibles,"No hay pedidos disponibles"));
        }
        return ResponseEntity.ok(new InfoResponse(HttpStatus.OK.value(),pedidosDisponibles,message));
    }

    @Override
    public ResponseEntity obtenerOrdenesCompletadas(String idUser, String estado) {
        List<Orden> ordenes = pedidoRepository.findOrdensByUser_IdAndStatus(idUser, EstadoEnum.ENTREGADO.name());
        String message = "Pedidos encontrados";
        if(ordenes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new InfoResponse(HttpStatus.NOT_FOUND.value(), ordenes,"No hay pedidos disponibles"));
        }
        return ResponseEntity.ok(new InfoResponse(HttpStatus.OK.value(),ordenes,message));
    }

    @Override
    public ResponseEntity nuevoPedidoEnCurso(Orden orden) {
//        if (pedidoRepository.existsByNroPedido(orden.getNroPedido())) {
//            return new ResponseEntity
//                    (new InfoResponse(HttpStatus.CONFLICT.value(),orden.getNroPedido(),"Error: email ya registrado"),
//                            HttpStatus.CONFLICT);
//        }
        pedidoRepository.save(orden);
        return ResponseEntity.created(null).body(new InfoResponse(HttpStatus.CREATED.value(), orden,"Orden registrada"));
    }

}
