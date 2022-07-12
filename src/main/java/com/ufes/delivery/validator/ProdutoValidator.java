package com.ufes.delivery.validator;

import com.ufes.delivery.model.Produto;

public interface ProdutoValidator {

    static void valida(Produto produto){
        if(produto == null){
            throw new RuntimeException("Instância de produto inválida!");
        }else{
            if(produto.getNome().isEmpty() || produto.getNome() == null){
                throw new RuntimeException("Nome do produto inválido!");
            }
            if(produto.getPrecoUnitario() < 0){
                throw new RuntimeException("Produto com preço unitário inválido!");
            }
            if(produto.getQuantidadeEmEstoque() < 0){
                throw new RuntimeException("Produto com quantidade em estoque inválida!");
            }
        }
    }
}
