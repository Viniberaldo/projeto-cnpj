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
 *
 * @author viniberaldo
 */
@RestController
public class AlfaController {

    @Autowired
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
     * @param cnpj
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
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
     *
     * @param data
     * @return
     */
    @PostMapping("/api-data/save")
    public ResponseEntity<Void> saveData(@RequestBody Company data) {
        alfaService.saveCompany(data);
        return ResponseEntity.ok().build();
    }

}
