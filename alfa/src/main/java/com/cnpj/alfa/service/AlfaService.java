package com.cnpj.alfa.service;

import com.cnpj.alfa.model.Company;
import com.cnpj.alfa.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de serviço da aplicação
 */
@Service
public class AlfaService {

    /**
     * URL da apli para busca de informações
     */
    private static final String BRASIL_API_CNPJ_URL
            = "https://brasilapi.com.br/api/cnpj/v1/{cnpj}";

    /**
     * Classe que mapeia objetos e facilita a leitura do Json de resposta da api
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Classe que realiza transações com banco de dados
     */
    private final CompanyRepository companyRepository;

    /**
     * Construtor da classe
     *
     * @param companyRepository Classe que implementa os métodos para
     * persistência no banco de dados
     */
    public AlfaService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Método que busca um cnpj através de uma API
     *
     * @param cnpj número cnpj o qual se deseja informações
     *
     * @return JsonNode com as informações recebidas da api
     */
    public JsonNode getApiData(String cnpj) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(BRASIL_API_CNPJ_URL,
                String.class, cnpj);
        JsonNode jsonNode = objectMapper.readTree(data);
        return jsonNode;
    }

    /**
     * Método que persiste a entidade company no banco de dados.
     *
     * @param company entidade que será persistida no banco de dados
     *
     * @return a entidade persistida
     */
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
}
