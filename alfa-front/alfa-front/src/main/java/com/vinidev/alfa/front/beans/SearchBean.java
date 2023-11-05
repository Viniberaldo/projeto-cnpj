package com.vinidev.alfa.front.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "SearchBean", eager = true)
@ApplicationScoped
public class SearchBean {

    private static final Logger LOGGER = Logger.getLogger(SearchBean.class.getName());
    private String searchString;
    private String razaoSocial;
    private String cidade;
    private String situacao;
    private String data;
    private String endereco;
    private String telefone;

    @PostConstruct
    public void init() {
        this.searchString = "";
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void search(String searchString) throws MalformedURLException, IOException {
        LOGGER.log(Level.INFO, "Searching for: {0}", searchString);
        String endpoint = "http://localhost:8080/api-data/" + searchString;

        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Process the API response
            String apiResponse = content.toString();
            if (apiResponse != null && !apiResponse.isEmpty()) {
                LOGGER.log(Level.INFO, "API response: {0}", apiResponse);
                String[] responseParts = apiResponse.split(", ");
                for (String part : responseParts) {
                    String[] keyValue = part.split(": ");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        switch (key) {
                            case "Razao Social" ->
                                setRazaoSocial(value);
                            case "Cidade" ->
                                setCidade(value);
                            case "Situacao Cadastral" ->
                                setSituacao(value);
                            case "Data Situacao Cadastral" ->
                                setData(value);
                        }
                    }
                }
                LOGGER.log(Level.INFO, "Updated properties: " + this.cidade);
                // Redirect to search.xhtml after processing the API response
                FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml?faces-redirect=true");
            } else {
                // Handle empty or invalid response
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error", "Invalid response from API"));
            }
        } else {
            // Handle error status code
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "Failed to call API: " + status));
        }
    }

    public void save() {
        // Save the form data to the database or perform any other necessary actions
        // You can access the form fields using the getter methods (e.g., razaoSocial, cidade, situacao, data, endereco, telefone)

        // Clear the form fields after saving
        LOGGER.log(Level.INFO, "Updated properties: " + this.razaoSocial);
        LOGGER.log(Level.INFO, "Updated properties: " + this.cidade);
        LOGGER.log(Level.INFO, "Updated properties: " + this.situacao);
        LOGGER.log(Level.INFO, "Updated properties: " + this.data);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Data saved successfully"));
    }
}
