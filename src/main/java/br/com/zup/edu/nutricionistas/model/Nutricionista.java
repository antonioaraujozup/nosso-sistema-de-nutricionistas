package br.com.zup.edu.nutricionistas.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Nutricionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String crn;

    public Nutricionista(String nome, String cpf, LocalDate dataNascimento, String email, String crn) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.crn = crn;
    }

    @Deprecated
    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    public Nutricionista() {
    }

    public Long getId() {
        return id;
    }

}
