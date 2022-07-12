package com.ufes.delivery.builder;

import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;

/**
 *
 * @author clayton
 */
public class CestaEconomicaBuilder extends CestaBuilder {

	public CestaEconomicaBuilder( Cliente cliente, Estabelecimento vendedor ) {
		super( cliente, vendedor );
	}

	@Override
	public void addOrigemAnimal() {
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 30 ), 6 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 32 ), 7.5 ) );
	}

	@Override
	public void addGraos() {
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 3 ), 3 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 11 ), 4.5 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 9 ), 1.5 ) );
	}

	@Override
	public void addIndustrializados() {
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 6 ), 0.6 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 17 ), 0.9 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 33 ), 0.75 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 2 ), 3 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 34 ), 6 ) );
	}

	@Override
	public void addLegumesEFrutas() {
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 35 ), 6 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 36 ), 6 ) );
		pedido.incluir( new ItemPedido( pedido, dao.buscaProdutoPorCodigo( 37 ), 7.5 ) );
	}

	@Override
	public void addOutros() {
		throw new UnsupportedOperationException();
	}

}
