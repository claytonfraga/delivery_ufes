package com.ufes.delivery;

import com.ufes.delivery.builder.CestaTopBuilder;
import com.ufes.delivery.model.Cliente;
import com.ufes.delivery.builder.DiretorCesta;
import com.ufes.delivery.model.Estabelecimento;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;
import com.ufes.delivery.dao.ProdutoDAO;

/**
 *
 * @author clayton
 */
public class Principal {

    public static void main(String[] args) {
        try {

            ProdutoDAO dao = ProdutoDAO.getInstance();

            Cliente cliente = new Cliente("Fulano", 1000.0);
            Estabelecimento vendedor = new Estabelecimento("Casa XPTO");
            DiretorCesta diretor = new DiretorCesta();
            Pedido pedido = diretor.build(new CestaTopBuilder(cliente, vendedor));
            pedido.incluir(new ItemPedido(pedido, dao.buscaProdutoPorCodigo(16), 0.6));

            pedido.concluir();
            pedido.pagar();
            pedido.preparar();
            pedido.sairParaEntrega();
            pedido.entregar();
            pedido.avaliar(5);
            System.out.println(pedido);

        } catch (Exception e) {
            System.out.println("Falha: " + e.getMessage());
        }
    }

}
