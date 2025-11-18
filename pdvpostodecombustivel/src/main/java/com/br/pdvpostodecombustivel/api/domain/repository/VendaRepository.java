package com.br.pdvpostodecombustivel.api.domain.repository;

import com.br.pdvpostodecombustivel.api.domain.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim); //vendas por período

    @Query("SELECT v FROM Venda v WHERE v.usuarioVendedor = ?1")
    List<Venda> findByUsuario(String usuario); //vendas por usuário
}