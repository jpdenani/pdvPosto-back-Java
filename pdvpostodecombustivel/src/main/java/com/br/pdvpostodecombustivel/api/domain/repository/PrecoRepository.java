package com.br.pdvpostodecombustivel.api.domain.repository;

import com.br.pdvpostodecombustivel.api.domain.entity.Preco;
import com.br.pdvpostodecombustivel.api.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    Optional<Preco> findByValor(BigDecimal valor);
    Optional<Preco> findFirstByOrderByDataAlteracaoDesc(); //pega o preço pela última data de alteração
    List<Preco> findByDataAlteracao(LocalDate dataAlteracao); //preços alterados em uma data específica
    boolean existsByValor(BigDecimal valor);


    Optional<Preco> findFirstByProdutoOrderByDataAlteracaoDescHoraAlteracaoDesc(Produto produto);
    //pega pelo último preço de um produto específico


    Optional<Preco> findFirstByProduto_IdOrderByDataAlteracaoDescHoraAlteracaoDesc(Long produtoId);
}   //passar apenas o id do produto ao invés da entidade toda