package ar.com.billetera.api.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import ar.com.billetera.api.entities.Billetera;
import ar.com.billetera.api.entities.Cuenta;
import ar.com.billetera.api.models.response.SaldoResponse;
import ar.com.billetera.api.services.BilleteraService;
import ar.com.billetera.api.services.UsuarioService;

@RestController
public class BilleteraController {
    
    @Autowired
    BilleteraService billeteraService;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/billeteras/{id}/saldos/{moneda}")
    public ResponseEntity<?> consultarSaldo(@PathVariable Integer id,
            @PathVariable String moneda) {
       
        SaldoResponse saldo = new SaldoResponse();

        saldo.saldo = billeteraService.consultarSaldo(id, moneda);
        saldo.moneda = moneda;

        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/billeteras/{id}/saldos")   
    public ResponseEntity<List<SaldoResponse>> consultarSaldo(@PathVariable Integer id) {

        Billetera billetera = new Billetera();

        billetera = billeteraService.buscarPorId(id);

        List<SaldoResponse> saldos = new ArrayList<>();

        for (Cuenta cuenta : billetera.getCuentas()) {

            SaldoResponse saldo = new SaldoResponse();

            saldo.saldo = cuenta.getSaldo();
            saldo.moneda = cuenta.getMoneda();
            saldos.add(saldo);
        }
        return ResponseEntity.ok(saldos);
    }

    
}
