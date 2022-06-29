package com.ufes.delivery.model.state;

import com.ufes.delivery.model.EventoPedido;
import com.ufes.delivery.model.ItemPedido;
import com.ufes.delivery.model.Pedido;

import java.time.LocalDateTime;

/**
 *
 * @author clayton
 */
public abstract class PedidoState {

    protected Pedido pedido;
    private String nomeEstado;

    public PedidoState(Pedido pedido, String nomeEstado) {
        this.pedido = pedido;
        this.nomeEstado = nomeEstado;
        pedido.add(new EventoPedido(LocalDateTime.now(), nomeEstado));
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void pagar() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "pago"));
    }

    public void cancelar() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "cancelado"));
    }


    public void preparar() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "preparado"));
    }

    public void sairParaEntrega() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "sair para entrega"));
    }

    public void entregar() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "entregue"));    
    }

    public void concluir() {
        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "concluído"));    
       }

    public void reembolsar() {

        throw new RuntimeException(getMensagemFalha(getNomeEstado(), "reembolsado"));    
    }

    public void incluir(ItemPedido item) {
        throw new RuntimeException("Itens não podem ser adicionados ao pedido no estado \'" + getNomeEstado() + "\'");
    }

    public void removeItem(String nome) {
        throw new RuntimeException("Itens não podem ser removidos do pedido no estado \'" + getNomeEstado() + "\'");
    }

    public void avaliar(int nota) {
        if (getNomeEstado().toLowerCase().contains("entregue") || getNomeEstado().toLowerCase().contains("reembolsado")) {
            if (nota >= 1 && nota <= 5) {
                pedido.add(new EventoPedido(LocalDateTime.now(), "Avaliação do pedido: " + nota));
            } else {
                throw new RuntimeException("Informe uma nota entre 1 e 5");
            }
        } else {
            throw new RuntimeException(getMensagemFalha(getNomeEstado(), "avaliado"));
        }
    }

    private String getMensagemFalha(String nomeEstado, String estado) {
        return "Este pedido não pode ser " + estado + " no estado \'" + nomeEstado + "\'";
    }

    @Override
    public String toString() {
        return getNomeEstado();
    }
}
