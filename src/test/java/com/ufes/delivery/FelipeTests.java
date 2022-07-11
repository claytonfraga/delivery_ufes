package com.ufes.delivery;

import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Desconto;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.Pedido;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
/**
 *
 * @author felipe.girardi
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FelipeTests {

    public FelipeTests() {
    }

    @Test
    void CT001() {
        //arrange
        Cliente cliente = new Cliente("Felipe", 150.00);
        String textoObtido;
        String expected = "Cliente: Felipe, saldo de R$ 150,00";
        //act

        textoObtido = cliente.toString();

        //assert
        assertEquals(expected, textoObtido);

    }
 @Test
    void CT003() {
        //arrange
        Estabelecimento estabelecimento = new Estabelecimento("Pavuna");
        Cliente cliente = new Cliente("Felipe", 150.00);
        Pedido pedido = new Pedido(1,cliente,estabelecimento,LocalTime.now());
        Estabelecimento obtido;
        //act
        obtido = pedido.getEstabelecimento();
        //assert
        assertEquals(estabelecimento.getNome(),obtido.getNome());

    }
    
     @Test
    void CT004() {
        //arrange
        int numero = 1;
        Estabelecimento estabelecimento = new Estabelecimento("Pavuna");
        Cliente cliente = new Cliente("Felipe", 150.00);
        Pedido pedido = new Pedido(1,cliente,estabelecimento,LocalTime.now());
        int obtido;
        //act
        obtido = pedido.getNumero();
        //assert
        assertEquals(numero,obtido);
    }
    
    @Test
    void CT006() {
        //arrange
        LocalDate data = LocalDate.now(); 
        Estabelecimento estabelecimento = new Estabelecimento("Pavuna");
        Cliente cliente = new Cliente("Felipe", 150.00);
        Pedido pedido = new Pedido(1,cliente,estabelecimento,LocalTime.now());
        LocalDate obtido;
        //act
        obtido = pedido.getData();
        //assert
        assertEquals(data,obtido);
    }

@Test
    void CT007() {
        //arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        EventoPedido eventoPedido = new EventoPedido(LocalDateTime.now(), "Aprovado");
        String obtido = eventoPedido.getDataHora().format(formatter);
        String eventoPedidoObtido;
        String expected = "Evento no pedido: " + obtido + "; Aprovado";
        //act

        eventoPedidoObtido = eventoPedido.toString();

        //assert
        assertEquals(expected, eventoPedidoObtido);

    }

@Test
    void CT008() {
        //arrange
        String nome ;
        String expected = "Felipe";
        Desconto desconto = new Desconto("Felipe", 0.2 ,150.00);
        //act
        nome = desconto.getNome();
        //assert
        assertEquals(expected,nome);
    }

@Test
    void CT009() {
        //arrange
        Double percentual ;
        Double expected = 0.2;
        Desconto desconto = new Desconto("Felipe", 0.2 ,150.00);
        //act
        percentual = desconto.getPercentual();
        //assert
        assertEquals(expected,percentual);
    }

 @Test
    void CT010() {
        //arrange
        Desconto desconto = new Desconto("Felipe", 0.2 ,150.00);
        String descontoObtido;
        String expected = "Desconto: Felipe, (%):20,00, valor (R$): 150,00";
        //act

        descontoObtido = desconto.toString();

        //assert
        assertEquals(expected, descontoObtido);

    }
}

