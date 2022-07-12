package com.ufes.delivery.validator;

import com.ufes.delivery.model.Produto;

public interface ProdutoValidator {

    static void valida(Produto produto){
        if(produto == null){
            throw new RuntimeException("Instância de produto inválida!");
        }else{
            if( produto.getNome() == null || produto.getNome().isEmpty()){
                throw new RuntimeException("Nome do produto inválido!");
            }
            if(produto.getPrecoUnitario() < 0){
                throw new RuntimeException("Produto não pode ter preço unitário negativo!");
            }
            if(produto.getQuantidadeEmEstoque() < 0){
                throw new RuntimeException("Produto não pode ter quantidade em estoque negativa!");
            }
        }
    }
}
