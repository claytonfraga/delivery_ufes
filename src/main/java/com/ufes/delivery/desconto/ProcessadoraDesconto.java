package com.ufes.delivery.desconto;

import java.util.ArrayList;

import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class ProcessadoraDesconto {

	private ArrayList<IMetodoDesconto> metodosDescontos;

	public ProcessadoraDesconto() {
		metodosDescontos = new ArrayList<>();
		metodosDescontos.add( new MetodoDescontoPercentual() );
	}

	public void processar( Pedido pedido ) {
		for( IMetodoDesconto metodoDesconto : metodosDescontos ) {
			metodoDesconto.calcula( pedido );
		}
	}

}
