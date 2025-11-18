package com.br.pdvpostodecombustivel.api.domain.repository;

import com.br.pdvpostodecombustivel.api.domain.entity.Custo;
import com.br.pdvpostodecombustivel.api.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustoRepository extends JpaRepository<Custo, Long> {

    /*retorna todos os custos já carregando o produto associado na mesma consulta usando JOIN FETCH
    (evita lazy loading)
     */

    @Query("SELECT c FROM Custo c JOIN FETCH c.produto")
    List<Custo> findAllWithProduto();

    /*
    busca um único Custo, pelo ID, junto com seu produto, na mesma consulta
     */

    @Query("SELECT c FROM Custo c JOIN FETCH c.produto WHERE c.id = :id")
    Optional<Custo> findByIdWithProduto(@Param("id") Long id);

    /*
    Procura o custo de um produto específico, usando o ID do produto
     */

    @Query("SELECT c FROM Custo c JOIN FETCH c.produto WHERE c.produto.id = :produtoId")
    Optional<Custo> findByProdutoId(@Param("produtoId") Long produtoId);

    boolean existsByProduto(Produto produto);
}