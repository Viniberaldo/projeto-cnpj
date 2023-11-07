package com.cnpj.alfa.repository;

import com.cnpj.alfa.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author viniberaldo
 */
public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
