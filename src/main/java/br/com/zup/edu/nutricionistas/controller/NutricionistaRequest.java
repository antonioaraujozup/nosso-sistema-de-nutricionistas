package br.com.zup.edu.nutricionistas.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class NutricionistaRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String CRN;

    @NotBlank
    @CPF
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public NutricionistaRequest(String nome, String email, String CRN, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.CRN = CRN;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCRN() {
        return CRN;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
