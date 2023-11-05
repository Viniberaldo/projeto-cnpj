package com.vinidev.alfa.front.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Named("bean")
@RequestScoped
public class SearchBean {

    private String searchString;
    private String razaoSocial;
    private String cidade;
    private String situacao;
    private String data;
    private String endereco;
    private String telefone;

    public String getSearchString() {
        return searchString;
    }

    public String setSearchString() {
        // Get the value entered in the text field
        String searchString = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get("searchString");

        // Set the searchString property
        this.searchString = searchString;

        // Redirect to the second page
        return "search.xhtml?faces-redirect=true";
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

    public void search() {
        String endpoint = "http://localhost:8080/api-data/" + searchString;

        Client client = ClientBuilder.newClient();
        String apiResponse = client.target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        // Process the API response
        if (apiResponse != null && !apiResponse.isEmpty()) {
            String[] responseParts = apiResponse.split(", ");
            for (String part : responseParts) {
                String[] keyValue = part.split(": ");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    switch (key) {
                        case "Razao Social":
                            razaoSocial = value;
                            break;
                        case "Cidade":
                            cidade = value;
                            break;
                        case "Situacao Cadastral":
                            situacao = value;
                            break;
                        case "Data Situacao Cadastral":
                            data = value;
                            break;
                    }
                }
            }
        } else {
            // Handle empty or invalid response
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid response from API"));
        }

        client.close();
    }

    public void save() {
        // Save the form data to the database or perform any other necessary actions
        // You can access the form fields using the getter methods (e.g., razaoSocial, cidade, situacao, data, endereco, telefone)

        // Clear the form fields after saving
        razaoSocial = null;
        cidade = null;
        situacao = null;
        data = null;
        endereco = null;
        telefone = null;

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Data saved successfully"));
    }
}
