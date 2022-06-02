package br.com.zup.edu.nutricionistas.controller;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import br.com.zup.edu.nutricionistas.repository.NutricionistaRepository;
import br.com.zup.edu.nutricionistas.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarNutricionistaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private NutricionistaRepository nutricionistaRepository;
    
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.nutricionistaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um nutricionista com dados válidos")
    void deveCadastrarUmNutricionistaComDadosValidos() throws Exception {
        
        // Cenário
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(
                "Ana Silva", 
                "ana@email.com.br",
                "123456",
                "462.788.480-07",
                LocalDate.of(1990, Month.JUNE, 20));

        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        mockMvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        redirectedUrlPattern("http://localhost/nutricionistas/*")
                );

        // Asserts
        List<Nutricionista> nutricionistas = nutricionistaRepository.findAll();

        assertEquals(1, nutricionistas.size());

    }

    @Test
    @DisplayName("Não deve cadastrar um nutricionista com dados nulos")
    void naoDeveCadastrarUmNutricionistaComDadosNulos() throws Exception {

        // Cenário
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(
                "",
                "",
                "",
                null,
                null);

        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(5, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo nome não deve estar em branco",
                "O campo dataNascimento não deve ser nulo",
                "O campo cpf não deve estar em branco",
                "O campo CRN não deve estar em branco",
                "O campo email não deve estar em branco"
        ));

    }

    @Test
    @DisplayName("Não deve cadastrar um nutricionista com email inválido")
    void naoDeveCadastrarUmNutricionistaComEmailInvalido() throws Exception {

        // Cenário
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(
                "Ana Silva",
                "anaemail.com.br",
                "123456",
                "462.788.480-07",
                LocalDate.of(1990, Month.JUNE, 20));

        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo email deve ser um endereço de e-mail bem formado"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um nutricionista com cpf inválido")
    void naoDeveCadastrarUmNutricionistaComCpfInvalido() throws Exception {

        // Cenário
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(
                "Ana Silva",
                "ana@email.com.br",
                "123456",
                "462.788.480-01",
                LocalDate.of(1990, Month.JUNE, 20));

        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo cpf número do registro de contribuinte individual brasileiro (CPF) inválido"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um nutricionista com data de nascimento no presente ou futuro")
    void naoDeveCadastrarUmNutricionistaComDataDeNascimentoNoPresenteOuFuturo() throws Exception {

        // Cenário
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(
                "Ana Silva",
                "ana@email.com.br",
                "123456",
                "462.788.480-07",
                LocalDate.of(2023, Month.JUNE, 20));

        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        // Asserts
        assertEquals(1, mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo dataNascimento deve ser uma data passada"
        ));
    }

}