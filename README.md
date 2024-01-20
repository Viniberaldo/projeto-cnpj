# projeto-cnpj :department_store:

Projeto para exercício de integração entre frontend, backend e containers.

---

# Visão Geral

A aplicação frontend construída com JSF, envia uma requisição HTTP para a aplicação backend, que é construída com o framework Spring. O backend, por sua vez, utiliza a [Brasil API](https://www.brasilapi.com.br/) para buscar informações correspondentes ao parâmetro inserido no frontend. A Brasil API retorna o CNPJ e outras informações detalhadas sobre a empresa pesquisada.

As informações recuperadas são então exibidas na página do frontend. Além disso, a aplicação frontend permite ao usuário adicionar manualmente informações complementares, como endereço e telefone, quando necessário.

Após o preenchimento dessas informações adicionais, o usuário pode clicar no botão "Salvar". Nesse momento, o frontend faz outra chamada ao backend, que recebe os dados via HTTP e os persiste no banco de dados (instância de postgres em container) para uso futuro.

Este fluxo de ação permite que a arquitetura de microsserviços (de maneira bem simplificada) seja estudada e experimentada.

# Guia de Configuração e Uso da Aplicação

Este é um guia passo a passo para configurar, implantar e usar a aplicação alfa. Certifique-se de seguir todas as instruções para uma experiência sem problemas.

## Requisitos

- [Tomcat](https://tomcat.apache.org/) versão 9.0.82
- [Docker](https://www.docker.com/)
- [JDK](https://www.oracle.com/java/technologies/javase-downloads.html) 17 ou superior

## Configuração do Tomcat

1. Certifique-se de ter o Tomcat 9.0.82 instalado em sua máquina.

2. Configure o Tomcat para executar na porta 8180.

3. Faça o deploy do projeto alfa-front-1.0-SNAPSHOT.war (localizado em /alfa-front/alfa-front/target) no Tomcat

## Configuração do Banco de Dados com Docker

1. Utilize Docker para subir um container com o PostgreSQL. Você pode usar o seguinte comando:

    ```shell
    sudo docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres -v pgdata:/var/lib/postgresql/data --name postgres postgres
    ```

## Execução da Aplicação Java

1. Certifique-se de ter o JDK 17 ou superior instalado em sua máquina.

2. Execute o arquivo alfa-0.0.1-SNAPSHOT.jar (localizado em alfa/target/) com o seguinte comando:

    ```shell
    java -jar alfa-0.0.1-SNAPSHOT.jar
    ```

## Interagindo com a Aplicação Web

1. Acesse o navegador com o endereço [http://localhost:8081/alfa-front-1.0-SNAPSHOT/](http://localhost:8081/alfa-front-1.0-SNAPSHOT/).

2. Insira um CNPJ válido no campo correspondente e clique em "Search".

3. Após a busca, os 4 primeiros campos do formulário de dados devem ser preenchidos, e o usuário pode preencher os campos de endereço e telefone.

4. Clique em "Salvar", e uma mensagem de sucesso será exibida.

## Verificando Dados no Banco de Dados

Para verificar se os dados foram persistidos no banco de dados, siga os passos abaixo:

1. Acesse o container do PostgreSQL com o seguinte comando:

    ```shell
    sudo docker exec -it postgres psql -U postgres
    ```

2. Depois de entrar no psql, acesse o banco de dados 'dbempresas' com o comando:

    ```sql
    \c dbempresas;
    ```

3. Após conectar ao banco dbempresas, verifique a tabela 'company' com o seguinte comando:

    ```sql
    select * from company;
    ```

## Contribuições

Contribuições são bem-vindas. Sinta-se à vontade para reportar problemas, abrir pull requests e colaborar para melhorar esta aplicação.



# Melhorias Futuras

Aqui estão algumas melhorias que podem ser consideradas para aprimorar a aplicação alfa no futuro:

1. **Máscara para o Campo CNPJ**: Adicionar uma máscara ao campo CNPJ para aceitar apenas números e melhorar a usabilidade.

2. **Validação do CNPJ**: Implementar uma validação no campo CNPJ para garantir que os dados sejam válidos antes de enviar uma requisição para a API brasileira correspondente.

3. **Testes Unitários e de Integração**: Desenvolver testes unitários e de integração abrangentes para os métodos da aplicação Spring Boot. Isso ajudará a garantir a estabilidade e a confiabilidade do software.

4. **Ferramentas de Monitoramento**: Implementar ferramentas de monitoramento, como Prometheus e Grafana, para acompanhar o desempenho e a saúde da aplicação em tempo real.

Estas são apenas algumas sugestões para melhorar a aplicação no futuro. Contribuições são bem-vindas para implementar essas e outras melhorias.

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

---
