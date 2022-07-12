package com.ufes.delivery.imposto;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class MetodoISS implements IMetodoImposto {

	@Override
	public void calcula( Pedido pedido ) {
		double percentualISS = 0.02;
		double valorISS = percentualISS * pedido.getValorTotal();
		pedido.add( new Imposto( "ISS", percentualISS, valorISS ) );
	}

}
