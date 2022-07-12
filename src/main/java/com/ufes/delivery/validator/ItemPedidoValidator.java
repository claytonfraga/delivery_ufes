package com.ufes.delivery.validator;

import com.ufes.delivery.model.ItemPedido;

import java.util.List;


public interface ItemPedidoValidator {

    static void valida(ItemPedido itemPedido) {

        if (itemPedido.getQuantidade() < 0.0) {
            throw new RuntimeException("A quantidade de um item não pode ser menor que 0!");
        }
    }
}
