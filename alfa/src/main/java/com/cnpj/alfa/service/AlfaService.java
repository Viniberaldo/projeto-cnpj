package com.cnpj.alfa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * DOCUMENTAR
 *
 * @author viniberaldo
 */
@Service
public class AlfaService {

    private static final String BRASIL_API_CNPJ_URL
            = "https://brasilapi.com.br/api/cnpj/v1/{cnpj}";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Documentar
     *
     * @param cnpj
     * @return
     */
    public JsonNode getApiData(String cnpj) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(BRASIL_API_CNPJ_URL,
                String.class, cnpj);
        JsonNode jsonNode = objectMapper.readTree(data);
        return jsonNode;
    }
}
