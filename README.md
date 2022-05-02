# Projeto SigaBem para Calculo de Frete

## Requests

http://localhost:8080/sigabem/calcular-frete/ - **POST**

### Body

```
{
    "peso":0,
    "cepOrigem":"0000000",
    "cepDestino":"0000000",
    "nomeDestinatario":"Nome Sobrenome"
}
```

## Banco de Dados

- MySQL
- JPA

### Conexão

No caminho *src/main/resources/application.properties* estará as informações referentes à conexão com o Banco.

Username: root

Password: root

O JPA tratará de criar a Base de Dados e as tabelas automaticamente, porém se preferir, pode fazer na mão utilizando as querys abaixo:

```
CREATE DATABASE sigabem;

CREATE TABLE frete (
  id bigint NOT NULL AUTO_INCREMENT,
  cep_destino varchar(255) DEFAULT NULL,
  cep_origem varchar(255) DEFAULT NULL,
  data_consulta datetime(6) DEFAULT NULL,
  data_prevista_entrega date DEFAULT NULL,
  nome_destinatario varchar(255) DEFAULT NULL,
  peso float NOT NULL,
  vl_total_frete double NOT NULL,
  PRIMARY KEY (id)
);
```

Caso haja problemas com a conexão, é provável que a opção Public Key Retrieval esteja habilitada no MySQL, portando é recomendável desativar para a aplicação Spring conectar ao banco.
