package com.ufes.delivery.model.state;

import com.ufes.delivery.dao.ProdutoDAO;
import com.ufes.delivery.exception.OperacaoInvalidaException;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

/**
 *
 * @author clayton
 */
public class PedidoCanceladoState extends PedidoState {

	public PedidoCanceladoState( Pedido pedido, String nomeEstado ) {
		super( pedido, nomeEstado );
	}

	@Override
	public void reembolsar() {
		try {
			if( super.pedido.getCliente().isReembolsar() ) {
				double valor = pedido.getValorFinal();
				pedido.setValorReembolsado( valor );
				Cliente cliente = pedido.getCliente();
				cliente.aumentaSaldo( valor );

				devolverProdutos();
			}

			pedido.setState( new ReembolsadoState( pedido ) );

		} catch ( Exception e ) {
			throw new OperacaoInvalidaException( e.getMessage() );
		}

	}

	private void devolverProdutos() {
		ProdutoDAO dao = ProdutoDAO.getInstance();

		for( ItemPedido item : pedido.getItens() ) {
			dao.adicionaEstoque( item.getCodigoProduto(), item.getQuantidade() );
		}
	}

}
