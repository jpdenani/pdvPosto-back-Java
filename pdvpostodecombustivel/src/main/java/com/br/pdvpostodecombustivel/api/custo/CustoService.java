package com.br.pdvpostodecombustivel.api.custo;

import com.br.pdvpostodecombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostodecombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostodecombustivel.api.domain.entity.Custo;
import com.br.pdvpostodecombustivel.api.domain.entity.Produto;
import com.br.pdvpostodecombustivel.api.domain.repository.CustoRepository;
import com.br.pdvpostodecombustivel.api.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
//injetar automaticamente os repositories dentro do service.

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
//transformar listas usando stream

@Service
public class CustoService {

    @Autowired
    private CustoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public CustoResponse create(CustoRequest req) {
        System.out.println("📦 Criando custo para produto ID: " + req.produtoId());


        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));


        if (repository.existsByProduto(produto)) {
            throw new RuntimeException("Já existe um custo cadastrado para este produto!");
        }

        Custo custo = new Custo(
                req.imposto(),
                req.custoVariavel(),
                req.custoFixo(),
                req.margemLucro(),
                req.dataProcessamento(),
                produto
        );

        custo = repository.save(custo);
        System.out.println(" Custo criado com sucesso! ID: " + custo.getId());

        return mapToResponse(custo);
    }

    @Transactional(readOnly = true)
    public CustoResponse getById(long id) {
        Custo custo = repository.findByIdWithProduto(id)
                .orElseThrow(() -> new RuntimeException("Custo não encontrado!"));
        return mapToResponse(custo);
    }

    @Transactional(readOnly = true)
    public List<CustoResponse> list() {
        return repository.findAllWithProduto().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustoResponse update(long id, CustoRequest req) {
        System.out.println("🔄 Atualizando custo ID: " + id);

        Custo custo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custo não encontrado!"));


        if (req.produtoId() != null && !custo.getProduto().getId().equals(req.produtoId())) {
            Produto novoProduto = produtoRepository.findById(req.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
            custo.setProduto(novoProduto);
        }

        custo.setImposto(req.imposto());
        custo.setCustoVariavel(req.custoVariavel());
        custo.setCustoFixo(req.custoFixo());
        custo.setMargemLucro(req.margemLucro());
        custo.setDataProcessamento(req.dataProcessamento());

        custo = repository.save(custo);
        System.out.println(" Custo atualizado com sucesso!");

        return mapToResponse(custo);
    }

    @Transactional
    public boolean delete(long id) {
        System.out.println("🗑️ Deletando custo ID: " + id);

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        System.out.println(" Custo deletado com sucesso!");
        return true;
    }

    private CustoResponse mapToResponse(Custo custo) {
        return new CustoResponse(
                custo.getId(),
                custo.getProduto().getId(),
                custo.getProduto().getNome(),
                custo.getImposto(),
                custo.getCustoVariavel(),
                custo.getCustoFixo(),
                custo.getMargemLucro(),
                custo.getDataProcessamento()
        );
    }
}