package com.vinidev.alfa.front.beans;

import io.github.pixee.security.BoundedLineReader;
import io.github.pixee.security.HostValidator;
import io.github.pixee.security.Urls;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
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
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.json.JSONObject;

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
        this.searchString = searchString;
        String endpoint = "http://localhost:8080/api-data/" + searchString;

        URL url = Urls.create(endpoint, Urls.HTTP_PROTOCOLS, HostValidator.DENY_COMMON_INFRASTRUCTURE_TARGETS);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = BoundedLineReader.readLine(in, 5_000_000)) != null) {
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
                            case "cnpj" ->
                                setSearchString(value);
                        }
                    }
                }
                LOGGER.log(Level.INFO, "Updated properties: " + apiResponse);
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

    public void save() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8080/api-data/save");

        Company company = new Company();
        company.setRazaoSocial(this.razaoSocial);
        company.setCnpj(this.searchString);
        company.setCity(this.cidade);
        company.setStatus(this.situacao);
        company.setStatusDate(this.data);
        company.setAddress(this.endereco);
        company.setPhone(this.telefone);

        JSONObject json = new JSONObject();
        json.put("cnpj", this.searchString);
        json.put("razaoSocial", this.razaoSocial);
        json.put("city", this.cidade);
        json.put("status", this.situacao);
        json.put("statusDate", this.data);
        json.put("address", this.endereco);
        json.put("phone", this.telefone);

        StringEntity entity = new StringEntity(json.toString());
        LOGGER.log(Level.INFO, "Json enviado: " + json.toString());
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        HttpContext context = HttpClientContext.create();
        CloseableHttpResponse response = client.execute(post, context);

        if (response.getCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getCode());
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Sucesso", "Os dados foram salvos com sucesso.");
        facesContext.addMessage(null, message);
    }
}
