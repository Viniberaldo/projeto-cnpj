package com.cnpj.alfa.repository;

import com.cnpj.alfa.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que implementa os métodos para operações com banco de dados.
 */
public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
