package com.ufes.delivery.builder;

import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class DiretorCesta {

	public Pedido build( CestaBuilder builder ) {
		if( builder == null ) {
			throw new OperacaoInvalidaException( "Informe uma classe Builder válida!" );
		}

		builder.addOrigemAnimal();
		builder.addGraos();
		builder.addIndustrializados();
		builder.addLegumesEFrutas();
		builder.addOutros();

		return builder.getPedido();
	}

}
