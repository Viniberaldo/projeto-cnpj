package com.cnpj.alfa.controller;

import com.cnpj.alfa.model.Company;
import com.cnpj.alfa.service.AlfaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador da aplicação
 */
@RestController
public class AlfaController {

    /**
     * Classe de serviço da aplicação
     */
    @Autowired
    private final AlfaService alfaService;

    /**
     * Construtor do controlador
     *
     * @param alfaService
     *   Classe de serviço utilizada no controlador
     */
    public AlfaController(AlfaService alfaService) {
        this.alfaService = alfaService;
    }

    /**
     * Método que faz a busca de um CNPJ na api BRASIL API e retorna o resultado
     * com informações pré-definidas no método.
     *
     * @param cnpj
     *   número do cnpj usado na busca
     *
     * @return
     *   String com os dados obtidos do JSON de resposta da api Brasil API
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     *   Exceção ao processar o JSON recebido
     */
    @GetMapping("/api-data/{cnpj}")
    public String getApiData(@PathVariable String cnpj) throws
            JsonProcessingException {
        JsonNode data = alfaService.getApiData(cnpj);
        String razaoSocial = data.path("razao_social").asText();
        String cidade = data.path("municipio").asText();
        String status = data.path("descricao_situacao_cadastral").asText();
        String statusDate = data.path("data_situacao_cadastral").asText();
        return "Razao Social: " + razaoSocial + ", Cidade: " + cidade + ", "
                + "Situacao Cadastral: " + status + ", "
                + "Data Situacao Cadastral: " + statusDate + ", cnpj: " + cnpj;
    }

    /**
     * Método que persiste a entidade Company no banco de dados.
     *
     * @param data
     *  objeto do tipo Company que será persistido no banco de dados
     *
     * @return
     *  ResponseEntity com o resultado da operação.
     */
    @PostMapping("/api-data/save")
    public ResponseEntity<Void> saveData(@RequestBody Company data) {
        alfaService.saveCompany(data);
        return ResponseEntity.ok().build();
    }

}
