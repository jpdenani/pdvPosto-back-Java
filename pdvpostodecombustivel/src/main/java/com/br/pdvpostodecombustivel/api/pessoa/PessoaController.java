package com.br.pdvpostodecombustivel.api.pessoa;


import com.br.pdvpostodecombustivel.api.pessoa.dto.PessoaRequest;
import com.br.pdvpostodecombustivel.api.pessoa.dto.PessoaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponse create(@RequestBody PessoaRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public PessoaResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping(params = "cpfCnpj")
    public PessoaResponse getByCpf(@RequestParam String cpfCnpj) {
        return service.getByCpfCnpj(cpfCnpj);
    }

    @GetMapping
    public Page<PessoaResponse> list(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @PutMapping("/{id}")
    public PessoaResponse update(@PathVariable Long id, @RequestBody PessoaRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    public PessoaResponse patch(@PathVariable Long id, @RequestBody PessoaRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean excluido = service.excluirPessoa(id); // chama o service
        if (excluido) {
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}