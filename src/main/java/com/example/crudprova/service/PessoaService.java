package com.example.crudprova.service;

import com.example.crudprova.model.Pessoa;
import com.example.crudprova.model.PessoaDTO;
import com.example.crudprova.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<PessoaDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PessoaDTO getById(Long id) {
        return repository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public PessoaDTO create(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());
        return convertToDTO(repository.save(pessoa));
    }

    public PessoaDTO update(Long id, PessoaDTO dto) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        pessoa.setNome(dto.getNome());
        pessoa.setEmail(dto.getEmail());
        return convertToDTO(repository.save(pessoa));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private PessoaDTO convertToDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setEmail(pessoa.getEmail());
        return dto;
    }
}