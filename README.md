# Attonatus

## Descrição

Prova técnica para a vaga de desenvolvedor Java na Attornatus.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- H2 Database
- Lombok
- AWS Elastic Beanstalk
- Maven

## Observações

O banco de dados utilizado é o H2, que é um banco de dados em memória. Portanto, ao executar a aplicação, o banco de
  dados será criado mas optei por não popular. Mas valido ressaltar que não tratei todos os exceptions e caso der algum
  erro de dados entre no

http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/h2-console/

e utilize das propriedades do application.yml para logar.

## Endpoints:

**Criar Pessoa:**

- POST [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas]

json

```json
{
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "yyyy-MM-dd"
}
```

Resposta: 201 Created

json

```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "yyyy-MM-dd"
}
```

**Atualizar Pessoa:**

-
PUT [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/{idPessoa}](http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/%7BidPessoa%7D)

Request Body:

json

 ```json
{
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "yyyy-MM-dd"
}
```

Resposta: 200 OK

json

```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "yyyy-MM-dd"
}
```

**Consultar Pessoa:**

-
GET [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/{id}](http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/%7Bid%7D)

Resposta: 200 OK

json

```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "yyyy-MM-dd"
}
```

**Consultar Todas as Pessoas:**

-
GET [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas](http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas)

Query params:

* page: número da página (opcional, padrão: 0)
* size: número de elementos por página (opcional, padrão: 20)

Resposta: 200 OK

json

```json
  [
  {
    "id": 1,
    "nome": "string",
    "cpf": "string",
    "dataNascimento": "yyyy-MM-dd"
  },
  {
    "id": 2,
    "nome": "string",
    "cpf": "string",
    "dataNascimento": "yyyy-MM-dd"
  }
]
```

**Criar Endereço:**

- POST [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/{pessoaId}/enderecos]

json

```json
{
  "logradouro": "Rua dos devs",
  "cep": "01010101",
  "numero": "01",
  "cidade": "buglândia"
}
```

Resposta: 201 Created

json

```json
{
  "id": 1,
  "logradouro": "Rua dos devs",
  "cep": "01010101",
  "numero": "01",
  "cidade": "buglândia"
}
```

**Consultar Endereço:**

- GET [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/{pessoaId}/enderecos/{enderecoId}]

Resposta: 200 OK

json

```json
[
  {
    "id": 1,
    "logradouro": "Rua dos devs",
    "cep": "01010101",
    "numero": "01",
    "cidade": "buglândia"
  },
  {
    "id": 2,
    "logradouro": "Rua dos devs",
    "cep": "01010101",
    "numero": "01",
    "cidade": "buglândia"
  }
]
```

**Settar Endereço como Principal:**

-
PUT [http://attornatusapp-env.eba-hvarprcm.us-east-1.elasticbeanstalk.com/pessoas/{pessoaId}/enderecos/{enderecoId}/principal]

Resposta: 200 OK

json

```json
{
  "id": 1,
  "logradouro": "Rua dos devs",
  "cep": "01010101",
  "numero": "01",
  "cidade": "buglândia",
  "principal": true
}
```