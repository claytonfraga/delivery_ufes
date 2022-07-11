package com.ufes.delivery.desconto;

import com.ufes.delivery.model.Pedido;

import java.util.ArrayList;

/**
 *
 * @author clayton
 */
public class ProcessadoraDesconto {

    private ArrayList<IMetodoDesconto> metodosDescontos;

    public ProcessadoraDesconto() {
        metodosDescontos = new ArrayList<>();
        metodosDescontos.add(new MetodoDescontoPercentual());
    }

    public void processar(Pedido pedido) {
        for (IMetodoDesconto metodoDesconto : metodosDescontos) {
            metodoDesconto.calcula(pedido);
        }
    }

}
