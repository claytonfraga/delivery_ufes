package com.ufes.delivery.imposto;

import com.ufes.delivery.model.Pedido;
import java.util.ArrayList;

/**
 *
 * @author clayton
 */
public class ProcessadoraImposto {

    private ArrayList<IMetodoImposto> metodosImpostos;

    public ProcessadoraImposto() {
        metodosImpostos = new ArrayList<>();
        metodosImpostos.add(new MetodoISS());
        metodosImpostos.add(new MetodoICMS());
    }

    public void processar(Pedido pedido) {
        for (IMetodoImposto metodoImposto : metodosImpostos) {
            metodoImposto.calcula(pedido);
        }
    }

}
