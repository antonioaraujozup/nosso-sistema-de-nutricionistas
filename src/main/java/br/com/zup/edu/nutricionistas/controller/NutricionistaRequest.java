package br.com.zup.edu.nutricionistas.controller;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class NutricionistaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    @CPF
    private String cpf;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String crn;

    public Nutricionista paraNutricionista() {
        return new Nutricionista(nome,cpf,dataNascimento,email,crn);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }
}
