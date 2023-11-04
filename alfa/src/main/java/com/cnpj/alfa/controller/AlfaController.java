package com.cnpj.alfa.controller;

import com.cnpj.alfa.service.AlfaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author viniberaldo
 */
@RestController
public class AlfaController {

    private final AlfaService alfaService;

    /**
     * documentar
     *
     * @param alfaService
     */
    public AlfaController(AlfaService alfaService) {
        this.alfaService = alfaService;
    }

    /**
     * Documentar
     *
     * @return
     */
    @GetMapping("/api-data/{cnpj}")
    public String getApiData(@PathVariable int cnpj) throws
            JsonProcessingException {
        JsonNode data = alfaService.getApiData(cnpj);
        String razaoSocial = data.path("razao_social").asText();
        String cidade = data.path("municipio").asText();
        String status = data.path("descricao_situacao_cadastral").asText();
        String statusDate = data.path("data_situacao_cadastral").asText();
        return "Razao Social: " + razaoSocial + ", Cidade: " + cidade + ", "
                + "Situacao Cadastral: " + status + ", "
                + "Data Situacao Cadastral: " + statusDate;
    }

    private String selectData(String data) {
        // implement the logic to select the information you want to return
        return data;
    }

}
