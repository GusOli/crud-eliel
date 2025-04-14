package com.example.crudprova.service;

import com.example.crudprova.model.Pessoa;
import com.example.crudprova.model.Trabalho;
import com.example.crudprova.model.TrabalhoDTO;
import com.example.crudprova.repository.PessoaRepository;
import com.example.crudprova.repository.TrabalhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabalhoService {

    @Autowired
    private TrabalhoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<TrabalhoDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public TrabalhoDTO getById(Long id) {
        return repository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));
    }

    public TrabalhoDTO create(TrabalhoDTO dto) {
        Trabalho trabalho = new Trabalho();
        trabalho.setTitulo(dto.getTitulo());
        trabalho.setDescricao(dto.getDescricao());
        if (dto.getPessoaId() != null) {
            Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            trabalho.setPessoa(pessoa);
        }
        return convertToDTO(repository.save(trabalho));
    }

    public TrabalhoDTO update(Long id, TrabalhoDTO dto) {
        Trabalho trabalho = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));
        trabalho.setTitulo(dto.getTitulo());
        trabalho.setDescricao(dto.getDescricao());
        if (dto.getPessoaId() != null) {
            Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
            trabalho.setPessoa(pessoa);
        } else {
            trabalho.setPessoa(null);
        }
        return convertToDTO(repository.save(trabalho));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private TrabalhoDTO convertToDTO(Trabalho trabalho) {
        TrabalhoDTO dto = new TrabalhoDTO();
        dto.setId(trabalho.getId());
        dto.setTitulo(trabalho.getTitulo());
        dto.setDescricao(trabalho.getDescricao());
        if (trabalho.getPessoa() != null) {
            dto.setPessoaId(trabalho.getPessoa().getId());
        }
        return dto;
    }
}